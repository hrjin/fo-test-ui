/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 9. 15.  First Draft.
 *
 ****************************************************/
package kyobobook.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.amazonaws.http.HttpResponse;
import kyobobook.common.Constants;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtAuthenticationFilter.java
 * @Date        : 2021. 9. 15.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtProvider jwtProvider;
    
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        try {
            String jwt = jwtProvider.resolveToken(request, Constants.COOKIE_ACCESS_TOKEN);
            String refreshJwt = jwtProvider.resolveToken(request, Constants.COOKIE_REFRESH_TOKEN);
            
            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt, request)) {
                // true.. ?????? ?????? ??????!
                logger.info("true? false? ..." + jwtProvider.validateToken(jwt, request));
             
                // ?????? ????????? ??????
                if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt, request)) {
                    Authentication authentication = jwtProvider.getAuthentication(jwt);
                    // SecurityContext ??? Authentication ??? ??????
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
