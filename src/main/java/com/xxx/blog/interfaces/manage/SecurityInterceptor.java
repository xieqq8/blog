package com.xxx.blog.interfaces.manage;

import com.alibaba.fastjson.JSON;
import com.xxx.toolbox.model.RestResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xiexx on 2017/1/8.
 */
public class SecurityInterceptor implements HandlerInterceptor{

    private List<String> allowRemoteHosts;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String remoterequest.getRemoteHost();
        String remote = request.getRemoteAddr();
        if(remote.equals("0:0:0:0:0:0:0:1") || CollectionUtils.isEmpty(allowRemoteHosts) || allowRemoteHosts.contains(remote)){
             return true;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSON(RestResponse.fail("需要配置ip白名单才能访问!!!")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void setAllowRemoteHosts(List<String> allowRemoteHosts) {
        this.allowRemoteHosts = allowRemoteHosts;
    }
}
