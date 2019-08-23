package com.cq.gmall.user.controller;

import com.cq.gmall.bean.UmsMember;
import com.cq.gmall.bean.UmsMemberReceiveAddress;
import com.cq.gmall.service.UmsMemberReceiveAddressService;
import com.cq.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/8/21 18:09
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UmsMemberReceiveAddressService umsMemberReceiveAddressService;

    @RequestMapping("index")
    @ResponseBody
    public String test() {
        return "hello user";
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        return userService.getAllUser();
    }

    @RequestMapping("getUmsMemberReceiveAddress")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        return umsMemberReceiveAddressService.getReceiveAddressByMemberId(memberId);
    }
}
