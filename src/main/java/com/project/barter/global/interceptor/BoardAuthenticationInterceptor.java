package com.project.barter.global.interceptor;

import com.project.barter.global.GlobalConst;
import com.project.barter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BoardAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals(HttpMethod.GET.toString()))
            return true;
        HttpSession session = request.getSession(false);
        if(session==null||session.getAttribute(GlobalConst.loginSessionAttributeName)==null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
//        session.getAttribute(loginSessionName).toString();
        return true;
    }

}
