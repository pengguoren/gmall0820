package com.cq.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cq.gmall.annotations.LoginRequired;
import com.cq.gmall.bean.OmsCartItem;
import com.cq.gmall.bean.PmsSkuInfo;
import com.cq.gmall.service.CartService;
import com.cq.gmall.service.SkuService;
import com.cq.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/11 17:20
 */
@Controller
public class CartController {
    @Reference
    SkuService skuService;
    @Reference
    CartService cartService;

    @RequestMapping("checkCart")
    @LoginRequired(loginSuccess = false)
    public String checkCart(HttpServletRequest request,HttpServletResponse response, String isChecked,String skuId, ModelMap modelMap) {
        String memberId = (String)request.getAttribute("memberId");
        String nickname = (String)request.getAttribute("nickname");

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        if (StringUtils.isNotBlank(memberId)){
            //调用服务，修改状态
            OmsCartItem omsCartItem = new OmsCartItem();
            omsCartItem.setIsChecked(isChecked);
            omsCartItem.setMemberId(memberId);
            omsCartItem.setProductSkuId(skuId);
            cartService.checkCart(omsCartItem);
            //将最新的数据从缓存中查出，渲染给内嵌页
            omsCartItems = cartService.getCartList(memberId);
        }else{
            //用户未登录,缓存中也没有数据，从cookie中获取数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
            if (omsCartItems != null) {
                for (OmsCartItem omsCartItem : omsCartItems) {
                    if (omsCartItem.getProductSkuId().equals(skuId)) {
                        omsCartItem.setIsChecked(isChecked);
                    }
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookie",JSON.toJSONString(omsCartItems),60*60*24,true);
        }
        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
        }
        modelMap.put("cartList",omsCartItems);
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        modelMap.put("totalAmount", totalAmount);
        return "cartListInner";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIsChecked().equals("1")) {
                totalAmount = totalAmount.add(omsCartItem.getTotalPrice());
            }
        }
        return totalAmount;
    }

    @RequestMapping("cartList")
    @LoginRequired(loginSuccess = false)
    public String cartList( HttpServletRequest request, ModelMap modelMap) {

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String memberId = (String)request.getAttribute("memberId");
        String nickname = (String)request.getAttribute("nickname");
        if(StringUtils.isNotBlank(memberId)){
            //用户一登陆
            //购物车列表从缓存中取出
            omsCartItems =  cartService.getCartList(memberId);
        }else{
            //用户未登录，从cookie中获取数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
        }
        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
        }
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        modelMap.put("totalAmount", totalAmount);
        modelMap.put("cartList",omsCartItems);
        return "cartList";

    }

    @RequestMapping("addToCart")
    @LoginRequired(loginSuccess = false)
    public String addToCart(HttpServletRequest request, HttpServletResponse response,String skuId,int quantity) {
        //根据skuId查询商品详细信息
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);
        //根据商品信息封装购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setQuantity(new BigDecimal(quantity));
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("1111111111111111111");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductSkuId(skuInfo.getId());
        omsCartItem.setProductAttr("");
        omsCartItem.setMemberNickname("66");
        omsCartItem.setIsChecked("1");
        //判断用户是否登陆
        String memberId = (String)request.getAttribute("memberId");
        String nickname = (String)request.getAttribute("nickname");
        if (StringUtils.isBlank(memberId)) {
            //用户未登录
            List<OmsCartItem> omsCartItems = new ArrayList<>();
            omsCartItem.setIsChecked("1");
            String cookieValue = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //cookie是否为空
            if (StringUtils.isBlank(cookieValue)) {
                //cookie为空
                omsCartItems.add(omsCartItem);
            }else {
                //cookie不为空
                omsCartItems = JSON.parseArray(cookieValue, OmsCartItem.class);
                boolean flag = if_cart_exist(omsCartItems, omsCartItem);
                //cookie中是否存在提交的相同的商品
                if (flag) {
                    for (OmsCartItem cartItem : omsCartItems) {
                        //cookie中是否已经存在加入购物车的商品
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                        }
                    }
                }else {
                    omsCartItems.add(omsCartItem);
                }

            }
            CookieUtil.setCookie(request,response,"cartListCookie",JSON.toJSONString(omsCartItems),60*60*72,true);
        }else {
            //用户已登录
            OmsCartItem CartItemFromDb = cartService.getCartItemBySkuId(skuId);
            if (CartItemFromDb != null) {
                //如果购物车中存在该商品
                CartItemFromDb.setQuantity(CartItemFromDb.getQuantity().add(omsCartItem.getQuantity()));
                cartService.updateCart(CartItemFromDb);
            } else {
                omsCartItem.setMemberId(memberId);
                cartService.addCart(omsCartItem);
            }

            //同步缓存
            cartService.flushCartCache(memberId);
        }

        return "redirect:/success.html";
    }

    /**
     *  cookie中是否存在提交的相同的商品
     */
    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        boolean flag = false;
        for (OmsCartItem cartItem : omsCartItems) {
            if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                flag = true;
            }
        }
        return flag;
    }
}
