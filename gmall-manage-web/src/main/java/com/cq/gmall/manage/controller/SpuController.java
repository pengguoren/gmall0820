package com.cq.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.*;
import com.cq.gmall.util.PmsUploadUtil;
import com.cq.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 18:59
 */

@Controller
@CrossOrigin
public class SpuController {
    @Reference
    SpuService spuService;

    /**
     * spu列表查询
     * @param catalog3Id
     * @return
     */
    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    /**
     * 保存spu元数据
     * @param pmsProductInfo
     * @return
     */
    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    /**
     * spu销售属性列表查询
     * @param spuId
     * @return
     */
    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    /**
     * spu图片列表查询
     * @param spuId
     * @return
     */
    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId) {
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }

    /**
     * 媒体文件上传到fastdfs上
     * @param multipartFile
     * @return
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile) {
        //将图片或者音频上传到分布式的文件存储系统
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        //将图片的存储路径返回给页面
        return imgUrl;
    }

}
