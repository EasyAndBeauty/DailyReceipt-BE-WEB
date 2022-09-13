package com.sprint.dailyreceipt.global.annotation;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.domain.token.repository.TokenRepository;
import com.sprint.dailyreceipt.global.exception.jwt.NotHaveTokenException;
import com.sprint.dailyreceipt.global.jwt.JwtService;
import com.sprint.dailyreceipt.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginAccountArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        boolean isAccountType = Account.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && isAccountType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String jwt = JwtUtil.resolveToken(request);
        String subject = jwtService.getSubject(jwt);

        Token token = tokenRepository.findByUniqueIdBySocial(subject)
                                     .orElseThrow(NotHaveTokenException::new);

        return token.getAccount();
    }
}
