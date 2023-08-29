package com.sprint.dailyreceipt.global.annotation;

import static com.sprint.dailyreceipt.global.ReceiptConstants.AUTHORIZATION_HEADER;

import com.sprint.dailyreceipt.domain.account.dao.AccountRepository;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginAccountArgumentResolver implements HandlerMethodArgumentResolver {

//    private final TokenRepository tokenRepository;
//    private final JwtParseService jwtParseService;

  private final AccountRepository accountRepository;

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

//        String jwt = JwtUtil.resolveToken(request);
//        String subject = jwtParseService.getSubject(jwt);

    final long memberId = Long.parseLong(request.getHeader(AUTHORIZATION_HEADER));

    return accountRepository.findById(memberId)
        .orElseThrow(AccountNotFoundException::new);
  }
}
