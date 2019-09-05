package com.cq.gmall.manage.mapper;

import com.cq.gmall.bean.PmsSkuInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/3 17:31
 */
public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);
}
