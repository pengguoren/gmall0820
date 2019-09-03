package com.cq.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.PmsSkuInfo;
import com.cq.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 彭国仁
 * @data 2019/9/3 17:25
 */

@Controller
@CrossOrigin
public class SkuController {

    @Reference
    SkuService skuService;

    /**
     * 保存sku元数据
     * @param pmsSkuInfo
     * @return
     */
    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        // 处理默认图片
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if(StringUtils.isBlank(skuDefaultImg)){
            pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
            pmsSkuInfo.getSkuImageList().get(0).setIsDefault("1");
        }
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }

}
