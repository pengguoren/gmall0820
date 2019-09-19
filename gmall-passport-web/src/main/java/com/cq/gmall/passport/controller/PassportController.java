package com.cq.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cq.gmall.bean.UmsMember;
import com.cq.gmall.service.UserService;
import com.cq.gmall.util.HttpclientUtil;
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
     * 第三方的登录
     */
    @RequestMapping("vlogin")
    public String vlogin(String code, HttpServletRequest request) {
        //通过授权码获得access_token
        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", "1838656841");
        paramMap.put("client_secret", "6d148a069ffd6a2defb29f8f83f631d3");
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("redirect_uri", "http://passport.gmall.com:8085/vlogin");

        paramMap.put("code", code);
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

        Map<String, Object> access_map = JSON.parseObject(access_token_json, Map.class);
        String access_token = (String) access_map.get("access_token");
        String uid = (String) access_map.get("uid");
        //通过access_token获取第三方的用户信息
        String s4 = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
        String user_json = HttpclientUtil.doGet(s4);
        Map<String, Object> user_map = JSON.parseObject(user_json, Map.class);
        //把用户信息存入数据库，用户类型设置为微博用户
        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType("2");
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setSourceUid((String) user_map.get("idstr"));
        umsMember.setCity((String) user_map.get("location"));
        umsMember.setNickname((String) user_map.get("screen_name"));
        String g = "0";
        String gender = (String) user_map.get("gender");
        if (gender.equals("m")) {
            g = "1";
        }
        umsMember.setGender(g);

        UmsMember umsCheck = new UmsMember();
        umsCheck.setSourceUid(umsMember.getSourceUid());
        // 检查该用户(社交用户)以前是否登陆过系统
        UmsMember umsMemberCheck = userService.checkOauthUser(umsCheck);

        if (umsMemberCheck == null) {
            umsMember = userService.addOauthUser(umsMember);
        } else {
            umsMember = umsMemberCheck;
        }
        //生成jwt的token，并重定向到首页，携带token
        String token = null;
        // rpc的主键返回策略失效
        String memberId = umsMember.getId();
        String nickname = umsMember.getNickname();
        Map<String, Object> userMap = new HashMap<>();
        // 是保存数据库后主键返回策略生成的id
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
        return "redirect:http://search.gmall.com:8083/index?token=" + token;
    }

    /**
     * 验证
     */
    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIp) {
        HashMap<String, String> map = new HashMap<>();
        //验证token
        Map<String, Object> decode = JwtUtil.decode(token, "2019gmall0105", currentIp);
        if (decode != null) {
            map.put("memberId", (String) decode.get("memberId"));
            map.put("nickname", (String) decode.get("nickname"));
            map.put("status", "success");
        } else {
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
