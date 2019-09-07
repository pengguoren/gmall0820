package com.cq.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.cq.gmall.bean.PmsSkuAttrValue;
import com.cq.gmall.bean.PmsSkuImage;
import com.cq.gmall.bean.PmsSkuInfo;
import com.cq.gmall.bean.PmsSkuSaleAttrValue;
import com.cq.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.cq.gmall.manage.mapper.PmsSkuImageMapper;
import com.cq.gmall.manage.mapper.PmsSkuInfoMapper;
import com.cq.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.cq.gmall.service.SkuService;
import com.cq.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

/**
 * @author 彭国仁
 * @data 2019/9/3 17:28
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuInfoId = pmsSkuInfo.getId();
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuInfo.getSkuAttrValueList();
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        List<PmsSkuImage> pmsSkuImages = pmsSkuInfo.getSkuImageList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValues) {
            pmsSkuAttrValue.setSkuId(skuInfoId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
            pmsSkuSaleAttrValue.setSkuId(skuInfoId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        for (PmsSkuImage pmsSkuImage : pmsSkuImages) {
            pmsSkuImage.setSkuId(skuInfoId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }


    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        //sku商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        //sku图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }


    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        //sku商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        //链接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        if(StringUtils.isNotBlank(skuJson)){
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else{
            //如果缓存中没有，查询mysql
            //设置分布式锁
            String token = UUID.randomUUID().toString();
            String ok = jedis.set("sku:" + skuId + " :info", token, "nx", "px", 10*1000);
            if(StringUtils.isNotBlank(ok)&&ok.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                if (pmsSkuInfo!=null) {
                    //mysql查询结果存入redis
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                }else{
                    //数据中不存在该sku
                    //为了防止缓存穿透，null或者""设值给redis
                    jedis.setex("sku:" + skuId + ":info",60*3, JSON.toJSONString(""));
                }

                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    //用token确定删除的是自己的sku的锁
                    jedis.del("sku:" + skuId + ":lock");
                    System.out.println("成功释放锁");
                }

            }else{
                //设置失败，自旋（该线程睡眠几秒后，重新访问本方法）
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId);
            }


        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
    }
}
