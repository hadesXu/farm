package com.hades.farm.web.config;

import com.langu.authorization.filter.DecryptFilter;
import com.langu.authorization.interceptor.AuthIntercepter;
import com.hades.farm.web.token.DefaultTokenUserService;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{

    @Bean
    public DefaultTokenUserService tokenUserService() {
        return new DefaultTokenUserService();
    }

    @Bean
    public AuthIntercepter authIntercepter() {
        AuthIntercepter intercepter = new AuthIntercepter();
        intercepter.setTokenParamKey("token");
        intercepter.setUserIdKey("userId");
        intercepter.setTokenUserService(tokenUserService());
        return intercepter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercepter()).addPathPatterns("/**").excludePathPatterns("/txim/callback");
    }

}
