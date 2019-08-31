package com.cq.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cq.gmall.bean.PmsBaseCatalog1;
import com.cq.gmall.bean.PmsBaseCatalog2;
import com.cq.gmall.bean.PmsBaseCatalog3;
import com.cq.gmall.manage.mapper.PmsBaseeCatalog1Mapper;
import com.cq.gmall.manage.mapper.PmsBaseeCatalog2Mapper;
import com.cq.gmall.manage.mapper.PmsBaseeCatalog3Mapper;
import com.cq.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/31 19:11
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseeCatalog1Mapper pmsBaseeCatalog1Mapper;

    @Autowired
    PmsBaseeCatalog2Mapper pmsBaseeCatalog2Mapper;

    @Autowired
    PmsBaseeCatalog3Mapper pmsBaseeCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseeCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);

        return pmsBaseeCatalog2Mapper.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2_id(catalog2Id);

        return pmsBaseeCatalog3Mapper.select(pmsBaseCatalog3);
    }
}
