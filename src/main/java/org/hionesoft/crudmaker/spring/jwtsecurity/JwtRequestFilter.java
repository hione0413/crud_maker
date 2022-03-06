package org.hionesoft.crudmaker.spring.jwtsecurity;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUserDetailsService jwtUserDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(
            Arrays.asList(
                    "/login",
                    "/api/member",
                    "/authenticate",
                    "/makecrud/java/spring/create"
            ));


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        logger.info("[doFilterInternal] jwt 유효성 확인 Start ::: " + request.getServletPath());

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get JWT Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token의 정보가 유효하지 않습니다.");

                // TODO : 특정 페이지로 이동시키기
                // return;
            } catch (ExpiredJwtException e) {
                logger.info("JWT Token has expired");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token 사용기간이 만료되었습니다.");

                // TODO : 특정 페이지로 이동시키기
                // return;
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            // TODO : 특정 페이지로 이동시키기
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token 이 누락되었습니다.");
//            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            JwtUserDetailsDTO userDetails = this.jwtUserDetailService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        logger.info("[doFilterInternal] jwt 유효성 확인 End");
        filterChain.doFilter(request, response);
    }

    /**
     * exclude 시킬 url 지정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

}
