package com.cq.gmall.manage.mapper;

import com.cq.gmall.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/1 7:50
 */
public interface PmsBaseAttrInfoMapper  extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIdStr") String valueIdStr);
}
