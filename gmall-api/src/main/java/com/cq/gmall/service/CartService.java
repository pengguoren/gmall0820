package com.cq.gmall.service;

import com.cq.gmall.bean.OmsCartItem;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/13 15:43
 */
public interface CartService {
    OmsCartItem getCartItemBySkuId(String skuId);

    void updateCart(OmsCartItem omsCartItem);

    void addCart(OmsCartItem omsCartItem);

    void flushCartCache(String memberId);

    List<OmsCartItem> getCartList(String memberId);

    void checkCart(OmsCartItem omsCartItem);
}
