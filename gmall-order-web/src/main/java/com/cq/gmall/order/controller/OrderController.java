package com.cq.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cq.gmall.annotations.LoginRequired;
import com.cq.gmall.bean.OmsCartItem;
import com.cq.gmall.bean.OmsOrder;
import com.cq.gmall.bean.OmsOrderItem;
import com.cq.gmall.bean.UmsMemberReceiveAddress;
import com.cq.gmall.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/9/18 9:37
 */
@Controller
public class OrderController {

    @Reference
    CartService cartService;

    @Reference
    UmsMemberReceiveAddressService umsMemberReceiveAddressService;

    @Reference
    OrderService orderService;

    @Reference
    UserService userService;

    @Reference
    SkuService skuService;


    @RequestMapping("submitOrder")
    @LoginRequired(loginSuccess = true)
    public ModelAndView submitOrder(String receiveAddressId,BigDecimal totalAmount,String tradeCode,HttpServletRequest request, HttpServletResponse response, ModelMap ModelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");

        //检查交易码
        String success = orderService.checkTradeCode(memberId,tradeCode);
        if (success.equals("success")) {
            //根据用户id获得要购买的商品列表（购物车），和总价格
            List<OmsOrderItem> omsOrderItems = new ArrayList<>();
            // 订单对象
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setAutoConfirmDay(7);
            omsOrder.setCreateTime(new Date());
            omsOrder.setDiscountAmount(null);
            //omsOrder.setFreightAmount(); 运费，支付后，在生成物流信息时
            omsOrder.setMemberId(memberId);
            omsOrder.setMemberUsername(nickname);
            omsOrder.setNote("快点发货");
            String outTradeNo = "gmall";
            // 将毫秒时间戳拼接到外部订单号
            outTradeNo = outTradeNo + System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
            // 将时间字符串拼接到外部订单号
            outTradeNo = outTradeNo + sdf.format(new Date());
            //外部订单号
            omsOrder.setOrderSn(outTradeNo);
            omsOrder.setPayAmount(totalAmount);
            omsOrder.setOrderType(1);
            UmsMemberReceiveAddress umsMemberReceiveAddress = userService.getReceiveAddressById(receiveAddressId);
            omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
            omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
            omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());
            // 当前日期加一天，一天后配送
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,1);
            Date time = c.getTime();
            omsOrder.setReceiveTime(time);
            omsOrder.setSourceType(0);
            omsOrder.setStatus("0");
            omsOrder.setOrderType(0);
            omsOrder.setTotalAmount(totalAmount);

            // 根据用户id获得要购买的商品列表(购物车)，和总价格
            List<OmsCartItem> omsCartItems = cartService.getCartList(memberId);

            for (OmsCartItem omsCartItem : omsCartItems) {
                if (omsCartItem.getIsChecked().equals("1")) {
                    // 获得订单详情列表
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    // 检价
                    boolean b = skuService.checkPrice(omsCartItem.getProductSkuId(),omsCartItem.getPrice());
                    if (b == false) {
                        ModelAndView mv = new ModelAndView("tradeFail");
                        return mv;
                    }
                    // 验库存,远程调用库存系统
                    omsOrderItem.setProductPic(omsCartItem.getProductPic());
                    omsOrderItem.setProductName(omsCartItem.getProductName());
                    // 外部订单号，用来和其他系统进行交互，防止重复
                    omsOrderItem.setOrderSn(outTradeNo);
                    omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
                    omsOrderItem.setProductPrice(omsCartItem.getPrice());
                    omsOrderItem.setRealAmount(omsCartItem.getTotalPrice());
                    omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                    omsOrderItem.setProductSkuCode("111111111111");
                    omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                    omsOrderItem.setProductId(omsCartItem.getProductId());
                    // 在仓库中的skuId
                    omsOrderItem.setProductSn("仓库对应的商品编号");

                    omsOrderItems.add(omsOrderItem);
                }
            }
            omsOrder.setOmsOrderItems(omsOrderItems);

            // 将订单和订单详情写入数据库,删除购物车的对应商品
            orderService.saveOrder(omsOrder);


            // 重定向到支付系统
            ModelAndView mv = new ModelAndView("redirect:http://payment.gmall.com:8087/index");
            mv.addObject("outTradeNo",outTradeNo);
            mv.addObject("totalAmount",totalAmount);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("tradeFail");
            return mv;
        }
    }

    /**
     * 去结算
     * @param request
     * @param response
     * @param ModelMap
     * @return
     */
    @RequestMapping("toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap ModelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        //根据用户id查询用户收货地址
        List<UmsMemberReceiveAddress> userAddressList = umsMemberReceiveAddressService.getReceiveAddressByMemberId(memberId);
        //将购物车集合转化为页面计算清单集合
        List<OmsOrderItem> omsOrderItems = new ArrayList<>();
        List<OmsCartItem> omsCartItems = cartService.getCartList(memberId);
        getOmsCarts(omsOrderItems, omsCartItems);
        BigDecimal totalAmount = getTotalAmount(omsCartItems);
        ModelMap.put("omsOrderItems", omsOrderItems);
        ModelMap.put("userAddressList", userAddressList);
        ModelMap.put("totalAmount", totalAmount);

        //生成交易码，在提交订单时做交易码的校验
        String tradeCode = orderService.getTradeCode(memberId);
        ModelMap.put("tradeCode", tradeCode);

        return "trade";
    }

    /**
     * 获得购物车列表并设置小计金额
     * @param omsOrderItems
     * @param omsCartItems
     */
    private void getOmsCarts(List<OmsOrderItem> omsOrderItems, List<OmsCartItem> omsCartItems) {
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIsChecked().equals("1")) {
                OmsOrderItem omsOrderItem = new OmsOrderItem();
                omsOrderItem.setProductName(omsCartItem.getProductName());
                omsOrderItem.setProductPic(omsCartItem.getProductPic());
                omsOrderItems.add(omsOrderItem);
            }
        }
        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
        }
    }

    /**
     * 获得订单总价，之前需要给每件商品费用小计设值
     * @param omsCartItems
     * @return
     */
    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIsChecked().equals("1")&&omsCartItem.getTotalPrice() != null) {
                totalAmount = totalAmount.add(omsCartItem.getTotalPrice());
            }
        }
        return totalAmount;
    }

}
