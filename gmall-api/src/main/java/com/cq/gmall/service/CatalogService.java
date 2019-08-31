package com.cq.gmall.service;

import com.cq.gmall.bean.PmsBaseCatalog1;
import com.cq.gmall.bean.PmsBaseCatalog2;
import com.cq.gmall.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/31 18:44
 */
public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
