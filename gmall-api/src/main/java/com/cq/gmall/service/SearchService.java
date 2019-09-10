package com.cq.gmall.service;

import com.cq.gmall.bean.PmsSearchParam;
import com.cq.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/10 7:53
 */
public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
