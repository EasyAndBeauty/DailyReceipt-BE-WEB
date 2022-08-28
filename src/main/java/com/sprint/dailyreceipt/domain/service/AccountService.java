package com.sprint.dailyreceipt.domain.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.repository.AccountRepository;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public void signIn(KakaoUserInfo kakaoUserInfo) {

        Optional<Account> findAccount = accountRepository.findByUniqueIdBySocial(kakaoUserInfo.getId());

        findAccount.ifPresentOrElse(account -> {
                                        log.info("signIn");
                                    }, () -> {
                                        Account account = Account.of(kakaoUserInfo.getEmail(), kakaoUserInfo.getId());

                                        accountRepository.save(account);
                                    }
        );
    }
}
