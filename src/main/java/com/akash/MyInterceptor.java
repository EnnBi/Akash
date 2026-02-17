package com.akash;

import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

class MyInterceptor
implements HandlerInterceptor {
    MyInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        // Allow static resources and dashboard
        if (uri.startsWith("/vendors/") || uri.startsWith("/css/") || uri.startsWith("/js/") ||
            uri.startsWith("/images/") || uri.startsWith("/fonts/") || uri.startsWith("/lib/") ||
            uri.startsWith("/addon/") || uri.contains("/dashboard")) {
            return true;
        }
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri((HttpServletRequest)request).replacePath(null).build().toUriString();
        // if (LocalDate.now().isAfter(LocalDate.of(2022, 4, 1))) {
        //     response.sendRedirect(baseUrl + "/dashboard");
        //     return false;
        // }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
