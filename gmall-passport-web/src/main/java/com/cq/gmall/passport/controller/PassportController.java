package com.cq.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cq.gmall.bean.UmsMember;
import com.cq.gmall.service.UserService;
import com.cq.gmall.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 彭国仁
 * @data 2019/9/14 10:03
 */
@Controller
public class PassportController {

    @Reference
    UserService userService;

    /**
     * 验证
     */
    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token,String currentIp) {
        HashMap<String, String> map = new HashMap<>();
        //验证token
        Map<String, Object> decode = JwtUtil.decode(token, "2019gmall0105", currentIp);
        if (decode != null) {
            map.put("memberId",(String) decode.get("memberId"));
            map.put("nickname",(String)decode.get("nickname"));
            map.put("status", "success");
        }else{
            map.put("status", "fair");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 登录
     */
    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request) {
        String token = "";
        // 调用用户服务验证用户名和密码
        UmsMember umsMemberLogin = userService.login(umsMember);

        if (umsMemberLogin != null) {
            // 登录成功

            // 用jwt制作token
            String memberId = umsMemberLogin.getId();
            String nickname = umsMemberLogin.getNickname();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("memberId", memberId);
            userMap.put("nickname", nickname);

            // 通过nginx转发的客户端ip
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip)) {
                // 从request中获取ip
                ip = request.getRemoteAddr();
                if (StringUtils.isBlank(ip)) {
                    ip = "127.0.0.1";
                }
            }

            // 按照设计的算法对参数进行加密后，生成token
            token = JwtUtil.encode("2019gmall0105", userMap, ip);

            // 将token存入redis一份
            userService.addUserToken(token, memberId);


        }
        return token;
    }
    /**
     * 返回主页
     *
     * @return
     */
    @RequestMapping("index")
    public String index(String ReturnUrl, ModelMap modelMap) {
        if (StringUtils.isNotBlank(ReturnUrl)) {
            modelMap.put("ReturnUrl", ReturnUrl);
        }
        return "index";
    }
}
