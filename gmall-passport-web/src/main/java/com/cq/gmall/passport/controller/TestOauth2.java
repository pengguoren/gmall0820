package com.cq.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.cq.gmall.util.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TestOauth2 {

    public static String getCode(){

        // 1 获得授权码
        //App Key：1838656841
        // http://passport.gmall.com:8085/vlogin
        String s1 = HttpclientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=1838656841&response_type=code&redirect_uri=http://passport.gmall.com:8085/vlogin");

        System.out.println(s1);

        // 在第一步和第二部返回回调地址之间,有一个用户操作授权的过程

        // 2 返回授权码到回调地址

        return null;
    }

    public static String getAccess_token(){
        // 3 换取access_token
        //App Secret：6d148a069ffd6a2defb29f8f83f631d3
        //?client_id=187638711&client_secret=a79777bba04ac70d973ee002d27ed58c&grant_type=authorization_code&redirect_uri=http://passport.gmall.com:8085/vlogin&code=CODE";
        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("client_id","1838656841");
        paramMap.put("client_secret","6d148a069ffd6a2defb29f8f83f631d3");
        paramMap.put("grant_type","authorization_code");
        paramMap.put("redirect_uri","http://passport.gmall.com:8085/vlogin");

        paramMap.put("code","3a6c4695043e50418fda4ce520044715");
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

       Map<String,String> access_map = JSON.parseObject(access_token_json,Map.class);

       System.out.println(access_map.get("access_token"));
       System.out.println(access_map.get("uid"));

        return access_map.get("access_token");
    }

    public static Map<String,String> getUser_info(String access_token){

        // 4 用access_token查询用户信息
        String s4 = "https://api.weibo.com/2/users/show.json?access_token="+access_token+"&uid=6809985023";
        String user_json = HttpclientUtil.doGet(s4);
        Map<String,String> user_map = JSON.parseObject(user_json,Map.class);

        System.out.println(user_map.get("1"));

        return user_map;
    }


    public static void main(String[] args) {
        String access_token = getAccess_token();
        if (StringUtils.isNotBlank(access_token)) {
            getUser_info(access_token);
        }

    }
}
