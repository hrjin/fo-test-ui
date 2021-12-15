/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 15.
 *
 ****************************************************/
package kyobobook.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : SecurityConfig.java
 * @Date        : 2021. 9. 15.
 * @author      : smlee1@kyobobook.com
 * @description : spring security 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler; 

    public SecurityConfig(JwtProvider jwtProvider
            , JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
            , JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtProvider = jwtProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            // CSRF 설정 Disable
            .csrf().disable()
            // 인증 및 접근 권한 오류 처리 클래스 등록
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            // 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정함.
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 로그인, 회원가입은 토큰이 없는 상태에서 들어옴. /auth 로 시작.
            .and()
            // HttpServletRequest 를 시큐리티 처리에 이용함을 의미
            .authorizeRequests()
            // 경로.권한
                .antMatchers("/onk").hasAuthority("USER")
                .antMatchers("/myroom/**").hasAuthority("USER")
                .anyRequest().permitAll()
            // Filter 등록
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider)
                    , UsernamePasswordAuthenticationFilter.class);
    }
}
