package com.cq.gmall.service;

import com.cq.gmall.bean.PmsSkuInfo;

/**
 * @author 彭国仁
 * @data 2019/9/3 17:27
 */
public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);
}