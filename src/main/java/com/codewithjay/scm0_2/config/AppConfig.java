package com.codewithjay.scm0_2.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    public String cloudName;

    @Value("${cloudinary.api.key}")
    public String apiKey;

    @Value("${cloudinary.api.secret}")
    public String apiSecret;

    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",cloudName,
                        "api_key",apiKey,
                        "api_secret",apiSecret
                )
        );
    }

}
