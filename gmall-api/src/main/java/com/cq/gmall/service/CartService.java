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

    /**
     * 根据用户id获得要购买的商品列表(购物车)，和总价格
     * @param memberId
     * @return
     */
    List<OmsCartItem> getCartList(String memberId);

    void checkCart(OmsCartItem omsCartItem);

    /**
     * 根据skuid删除购物车数据并刷新缓存
     * @param productSkuId
     */
    void removeCartData(String productSkuId);
}
