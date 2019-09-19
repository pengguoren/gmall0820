package com.cq.gmall.service;

import com.cq.gmall.bean.PmsSkuInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/3 17:27
 */
public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    List<PmsSkuInfo> getAllPmsSkuInfo();

    /**
     * 检验订单商品的价格和数据库是否一致
     * @param productSkuId
     * @param price
     * @return
     */
    boolean checkPrice(String productSkuId, BigDecimal price);
}
