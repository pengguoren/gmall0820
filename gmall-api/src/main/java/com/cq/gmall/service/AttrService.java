package com.cq.gmall.service;

import com.cq.gmall.bean.PmsBaseAttrInfo;
import com.cq.gmall.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 7:46
 */
public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
