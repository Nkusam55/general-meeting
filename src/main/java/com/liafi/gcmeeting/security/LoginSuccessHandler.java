package com.liafi.gcmeeting.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
         
        String redirectURL = request.getContextPath();
         
        System.out.println(authentication.getName() +"******"+redirectURL);
         
        response.sendRedirect(redirectURL+"/gcmeeting/usersList?username="+authentication.getName());
         
    }
 
}
