package com.sprint.dailyreceipt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.dailyreceipt.global.exception.BusinessException;
import com.sprint.dailyreceipt.global.exception.ExceptionResponseInfo;
import com.sprint.dailyreceipt.global.exception.jwt.ExpiredJwtTokenException;
import com.sprint.dailyreceipt.global.exception.jwt.InvalidJwtTokenException;
import com.sprint.dailyreceipt.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String jwt = JwtUtil.resolveToken(httpRequest);

        try {
            jwtService.validateToken(jwt);
        } catch (InvalidJwtTokenException | ExpiredJwtTokenException exception) {
            sendErrorMessage((HttpServletResponse) response, exception);
        }

        chain.doFilter(request, response);
    }

    private void sendErrorMessage(HttpServletResponse response, BusinessException e) throws IOException {
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(e.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ExceptionResponseInfo.from(e)));

        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

}
