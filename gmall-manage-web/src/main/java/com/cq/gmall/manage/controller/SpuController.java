package com.cq.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.PmsBaseSaleAttr;
import com.cq.gmall.bean.PmsProductInfo;
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
    public List<PmsBaseSaleAttr> saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        return null;
    }
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile) {
        //将图片或者音频上传到分布式的文件存储系统

        //将图片的存储路径返回给页面
        String s = "http://192.168.67.163/group1/M00/00/00/wKhDo1qmZmKAEi7GAAAoCdAcm0Q483.jpg";
        return s;
    }

}
