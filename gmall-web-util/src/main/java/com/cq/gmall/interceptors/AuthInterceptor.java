package com.cq.gmall.interceptors;

import com.alibaba.fastjson.JSON;
import com.cq.gmall.annotations.LoginRequired;
import com.cq.gmall.util.CookieUtil;
import com.cq.gmall.util.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 彭国仁
 * @data 2019/9/14 14:20
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截代码
        //判断被拦截的请求的访问的方法的注解（是否是需要拦截的）
        HandlerMethod hm = (HandlerMethod) handler;
        LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
        //是否拦截
        if (methodAnnotation == null) {
            return true;
        }
        String token = "";
        String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }

        boolean loginSuccess = methodAnnotation.loginSuccess();
        //调用认证中心进行验证
        String success = "fair";
        Map<String, String> successMap = new HashMap<>();
        if (StringUtils.isNotBlank(token)) {
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip)) {
                //从request中获取ip.
                ip = request.getRemoteAddr();
                if (StringUtils.isBlank(ip)) {
                    ip = "127.0.0.1";
                }
            }
            String successJson = HttpclientUtil.doGet("http://passport.gmall.com:8085/verify?token=" + token+"&currentIp="+ip);
            successMap = JSON.parseObject(successJson, Map.class);
            success = successMap.get("status");
        }
        //判断该请求是否必须登陆成功才能访问
        if (loginSuccess) {
            //用户必须登录
            if (!success.equals("success")) {
                //重定向到passport登录
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://passport.gmall.com:8085/index?ReturnUrl=" + requestURL);
                return false;
            }
            //验证通过，覆盖cookie中的token
            if (StringUtils.isNotBlank(token)) {
                CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
            }
            //需要将token携带的用户信息写入
            request.setAttribute("memberId", successMap.get("memberId"));
            request.setAttribute("nickname", successMap.get("nickname"));


        } else {
            //用户可以不登录，但必须验证
            if (success.equals("success")) {
                //需要将token携带的用户信息写入
                //验证通过，覆盖cookie中的token
                request.setAttribute("memberId", successMap.get("memberId"));
                request.setAttribute("nickname", successMap.get("nickname"));
                //验证通过，覆盖cookie中的token
                if (StringUtils.isNotBlank(token)) {
                    CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                }
            }
        }

        return true;
    }
}