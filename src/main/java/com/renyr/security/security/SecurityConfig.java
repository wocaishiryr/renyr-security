package com.renyr.security.security;

import com.renyr.security.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    public static final String [] PERMIT_GET_URLS={
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html",
            "/swagger-resources/configuration/security",
            "/Authorization/login",
            "/"};
    static final String [] PERMIT_POST_URLS={
            "/Authorization/login"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry=http.authorizeRequests();

        permitUrl(registry);

        registry.and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //都需要认证
                .authenticated()
                .and()
                //前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                //自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(authAccessDeniedHandler)
                .and()
                //添加JWT过滤器 除/login其它请求都需经过此过滤器
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    private void permitUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        permitGETUrl(registry);
        permitPOSTUrl(registry);
    }

    private void permitGETUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        for (String permitUrl : PERMIT_GET_URLS) {
            registry.antMatchers(HttpMethod.GET,permitUrl).permitAll();
        }
    }

    private void permitPOSTUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        for (String permitUrl : PERMIT_POST_URLS) {
            registry.antMatchers(HttpMethod.POST,permitUrl).permitAll();
        }
    }

}
