package com.cq.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.*;
import com.cq.gmall.service.AttrService;
import com.cq.gmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author 彭国仁
 * @data 2019/9/9 21:13
 */
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;


    /**
     * es根据keyword，skuName，平台属性值等搜索商品
     *
     * @param pmsSearchParam
     * @param modelMap
     * @return
     */
    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] valueIds = pmsSearchParam.getValueId();
        List<PmsSearchSkuInfo> pmsSearchSkuInfolist = searchService.list(pmsSearchParam);
        //抽取平台属性值的集合用set集合去重
        Set<String> attrValueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfolist) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                attrValueIdSet.add(valueId);
            }
        }
        //通过属性值id查询平台属性和属性值的列表
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(attrValueIdSet);
        //删除已经选择的平台属性
        //面包屑封装
        List<PmsSearchCrumbs> pmsSearchCrumbsList = new ArrayList<>();
        if (valueIds != null) {
            for (String valueId : valueIds) {
                PmsSearchCrumbs pmsSearchCrumbs = new PmsSearchCrumbs();
                String urlParam1 = getUrlParam(keyword, catalog3Id, valueIds, valueId);
                pmsSearchCrumbs.setUrlParam(urlParam1);
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo PmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = PmsBaseAttrInfo.getAttrValueList();
                    for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                        String baseAttrValueId = pmsBaseAttrValue.getId();

                        if (baseAttrValueId.equals(valueId)) {
                            pmsSearchCrumbs.setAttrName(PmsBaseAttrInfo.getAttrName());
                            pmsSearchCrumbs.setValueName(pmsBaseAttrValue.getValueName());
                            iterator.remove();
                        }
                    }
                }
                pmsSearchCrumbsList.add(pmsSearchCrumbs);
                modelMap.put("attrValueSelectedList", pmsSearchCrumbsList);
            }

        }
        //配置平台属性url路径参数
        String urlParam = getUrlParam(keyword, catalog3Id, valueIds);
        modelMap.put("skuLsInfoList", pmsSearchSkuInfolist);
        modelMap.put("attrList", pmsBaseAttrInfos);
        modelMap.put("keyword", keyword);
        modelMap.put("urlParam", urlParam);
        return "list";
    }


    public String getUrlParam(String keyword, String catalog3Id, String[] valueIds, String... CrumbsValueId) {
        //配置平台属性url路径参数
        String urlParam = "";
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank((urlParam))) {
                urlParam += "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank((urlParam))) {
                urlParam += "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (valueIds != null) {
            for (String valueId : valueIds) {

                if (CrumbsValueId != null && CrumbsValueId.length != 0) {
                    for (String s : CrumbsValueId) {
                        if (!valueId.equals(s)) {
                            urlParam = urlParam + "&valueId=" + valueId;
                        }
                    }
                } else {
                    urlParam = urlParam + "&valueId=" + valueId;
                }

            }
        }
        return urlParam;
    }

    /**
     * 返回商品主页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
