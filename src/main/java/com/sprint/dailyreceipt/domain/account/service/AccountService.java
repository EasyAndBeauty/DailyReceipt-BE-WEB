package com.sprint.dailyreceipt.domain.account.service;

import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.account.repository.AccountRepository;
import com.sprint.dailyreceipt.web.account.model.AccountDetailInfo;
import com.sprint.dailyreceipt.web.oauth.kakao.model.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
                                        Account account = Account.of(kakaoUserInfo.getEmail(),
                                                                     kakaoUserInfo.getId(),
                                                                     kakaoUserInfo.getNickname());

                                        accountRepository.save(account);
                                    }
        );
    }

    public AccountDetailInfo getUserDetailInfo(String socialId) {
        Account savedAccount = accountRepository.findByUniqueIdBySocial(socialId)
                                                .orElseThrow(EntityNotFoundException::new);

        return new AccountDetailInfo(savedAccount);
    }
}
