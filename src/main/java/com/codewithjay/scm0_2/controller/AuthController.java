package com.codewithjay.scm0_2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("auth")
public class AuthController {


    @GetMapping("verify-email")
   public String verifyEmail(@RequestParam("token") String tokan) {
    return tokan;
   }
}
