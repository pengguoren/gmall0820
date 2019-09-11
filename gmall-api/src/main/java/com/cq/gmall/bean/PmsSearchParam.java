package com.cq.gmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/10 7:46
 */
public class PmsSearchParam implements Serializable {

    String keyword;

    String catalog3Id;
    String [] valueId;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }
}
