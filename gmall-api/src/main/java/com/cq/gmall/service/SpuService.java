package com.cq.gmall.service;

import com.cq.gmall.bean.PmsProductImage;
import com.cq.gmall.bean.PmsProductInfo;
import com.cq.gmall.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 19:19
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId);
}
