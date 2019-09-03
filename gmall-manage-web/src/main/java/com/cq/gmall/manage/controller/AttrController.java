package com.cq.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.PmsBaseAttrInfo;
import com.cq.gmall.bean.PmsBaseAttrValue;
import com.cq.gmall.bean.PmsBaseSaleAttr;
import com.cq.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 7:40
 */
@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    /**
     * 获取属性值列表
     * @param attrId
     * @return
     */
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        List<PmsBaseAttrValue>  pmsBaseAttrValueList = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValueList;
    }

    /**
     * 保存或修改属性值
     * @param pmsBaseAttrInfo
     * @return
     */
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        String sucdess = attrService.saveAttrInfo(pmsBaseAttrInfo);
        return sucdess;
    }

    /**
     * 根据分类查询平台属性列表
     * @param catalog3Id
     * @return
     */
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos =  attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    /**
     * 查询销售属性列表
     * @return
     */
    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrList =  attrService.baseSaleAttrList();
        return pmsBaseSaleAttrList;
    }

}
