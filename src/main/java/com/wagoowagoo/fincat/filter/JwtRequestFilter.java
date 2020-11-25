package com.wagoowagoo.fincat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.common.ErrorResponse;
import com.wagoowagoo.fincat.util.ConstantUtil;
import com.wagoowagoo.fincat.util.JwtUtil;
import com.wagoowagoo.fincat.util.RequestUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
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

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        try {
            String accessToken = RequestUtil.getAccessToken(request);
            String username = JwtUtil.extractUsername(accessToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = accountService.loadUserByUsername(username);
                boolean isValidToken = JwtUtil.validateToken(accessToken, userDetails);

                if (isValidToken) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            response.setContentType(ConstantUtil.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(ErrorCode.JWT_SIGNATURE_ERROR)));
        } catch (MalformedJwtException e) {
            response.setContentType(ConstantUtil.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(ErrorCode.JWT_MALFORMED_ERROR)));
        } catch (ExpiredJwtException e) {
            response.setContentType(ConstantUtil.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(ErrorCode.JWT_EXPIRED_ERROR)));
        }
    }
}
