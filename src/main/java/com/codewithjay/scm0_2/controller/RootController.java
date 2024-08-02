package com.codewithjay.scm0_2.controller;

import com.codewithjay.scm0_2.Service.UserService;
import com.codewithjay.scm0_2.entities.User;
import com.codewithjay.scm0_2.helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class RootController {


    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");

        String email = Helper.getEmailOfLoggedInUser(authentication);
        System.out.println("User logged in: "+  "{ " +email + " }");
        // database se data ko fetch : get user from db :
//        User user = userService.getUserByEmail("jay@gmail.com");
        User user = userService.getUserByEmail(email);

        System.out.println(" user finding..." +user);
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());

        model.addAttribute("user", user);

    }
}
