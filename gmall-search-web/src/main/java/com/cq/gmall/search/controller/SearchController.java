package com.cq.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.bean.PmsSearchParam;
import com.cq.gmall.bean.PmsSearchSkuInfo;
import com.cq.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/9 21:13
 */
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam,ModelMap modelMap) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfolist = searchService.list(pmsSearchParam);
        modelMap.put("skuLsInfoList",pmsSearchSkuInfolist);
        return "list";
    }
    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
