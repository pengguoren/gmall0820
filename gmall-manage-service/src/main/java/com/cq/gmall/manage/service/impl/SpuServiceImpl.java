package com.cq.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cq.gmall.bean.PmsProductInfo;
import com.cq.gmall.manage.mapper.PmsProductInfoMapper;
import com.cq.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 19:21
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return  pmsProductInfoMapper.select(pmsProductInfo);
    }
}
