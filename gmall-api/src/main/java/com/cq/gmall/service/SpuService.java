package com.cq.gmall.service;

import com.cq.gmall.bean.PmsProductInfo;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 19:19
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);
}
