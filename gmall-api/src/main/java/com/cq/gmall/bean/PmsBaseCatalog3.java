package com.cq.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 彭国仁
 * @data 2019/8/31 18:38
 */
public class PmsBaseCatalog3 implements Serializable {
    @Id
    private String id;
    private String name;
    private String catalog2_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog2_id() {
        return catalog2_id;
    }

    public void setCatalog2_id(String catalog2_id) {
        this.catalog2_id = catalog2_id;
    }
}
