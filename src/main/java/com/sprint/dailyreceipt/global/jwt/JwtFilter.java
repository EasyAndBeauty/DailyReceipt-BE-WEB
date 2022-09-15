package com.sprint.dailyreceipt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionResponseInfo;
import com.sprint.dailyreceipt.global.jwt.application.JwtParseService;
import com.sprint.dailyreceipt.global.jwt.application.JwtSupport;
import com.sprint.dailyreceipt.global.jwt.exception.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.jwt.exception.InvalidJwtTokenException;
import com.sprint.dailyreceipt.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sprint.dailyreceipt.global.ReceiptConstants.CHARACTER_ENCODING;
import static com.sprint.dailyreceipt.global.ReceiptConstants.CONTENT_TYPE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.ERROR_LOG_MESSAGE;
import static com.sprint.dailyreceipt.global.ReceiptConstants.whiteList;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final ObjectMapper objectMapper;

    private final JwtSupport jwtSupport;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = httpRequest.getRequestURI();

        String jwt = JwtUtil.resolveToken(httpRequest);

        try {
            if(isLoginCheckPath(requestURI)) {
                jwtSupport.validateToken(jwt);
            }
        } catch (InvalidJwtTokenException | ExpiredJwtTokenException exception) {
            sendErrorMessage((HttpServletResponse) response, exception);
        }

        chain.doFilter(request, response);
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

    private void sendErrorMessage(HttpServletResponse response, BusinessException e) throws IOException {
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(e.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ExceptionResponseInfo.from(e)));

        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

}
