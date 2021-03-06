package com.cq.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cq.gmall.bean.PmsProductSaleAttr;
import com.cq.gmall.bean.PmsSkuInfo;
import com.cq.gmall.bean.PmsSkuSaleAttrValue;
import com.cq.gmall.service.SkuService;
import com.cq.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 彭国仁
 * @data 2019/9/4 8:36
 */
@Controller
public class ItemController {

    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId ,ModelMap map) {
       PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);
        //sku对象
        map.put("skuInfo",pmsSkuInfo);
        //销售属性列表
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        //生成sku的销售属性hash表
        Map<String, String> skuSaleAttrHashMap = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String v = skuInfo.getId();
            String k = "";
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
                k += pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";
            }
            skuSaleAttrHashMap.put(k,v);
        }
        //将sku的销售属性hash表放到页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHashMap);
        map.put("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);
        return "item";
    }
    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            list.add("循环"+i);
        }
        modelMap.put("hello","hello thymeleaf");
        modelMap.put("list", list);
        modelMap.put("check", 1);
        return "index";
    }
}
