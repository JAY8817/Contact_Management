package com.codewithjay.scm0_2.config;

import java.io.IOException;


import com.codewithjay.scm0_2.Repo.UserRepo;
import com.codewithjay.scm0_2.entities.Providers;
import com.codewithjay.scm0_2.entities.User;
import com.codewithjay.scm0_2.helper.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.util.List;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private UserRepo userRepo;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {



        // identify the provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

        System.out.println(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

//        print the all element
        oauthUser.getAttributes().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });


        User user = new User();
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailverified(true);


        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            // google
            // google attributes

            user.setEmail(oauthUser.getAttribute("email"));
            user.setProfilepic(oauthUser.getAttribute("picture"));
            user.setUsername(oauthUser.getAttribute("name"));
            user.setProviderUserId(oauthUser.getName());
//            user.setPassword(oauthUser.getAttribute("password"));
            user.setPassword(oauthUser.getName() + "@2024");
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            // github

            user.setEmail(oauthUser.getAttribute("email"));
            user.setPassword(oauthUser.getName() + "@2024");
            user.setProfilepic(oauthUser.getAttribute("avatar_url"));
            user.setUsername(oauthUser.getAttribute("login"));
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github");

        }

        else {

            System.out.println("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);

        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved email id is :" + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }

}