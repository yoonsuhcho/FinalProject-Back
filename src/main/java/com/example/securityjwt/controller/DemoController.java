package com.example.securityjwt.controller;

import com.example.securityjwt.controller.response.Response;
import com.example.securityjwt.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DemoController {

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Enumeration headers = request.getHeaderNames();

        while(headers.hasMoreElements()) {
            String headerName = (String)headers.nextElement();
            String value = request.getHeader(headerName);
            log.info("headerName={}: {}", headerName, value);
            System.out.println("headerName:"+headerName+","+value);
        }
//        return "home";
        return "loginForm";
    }


}
