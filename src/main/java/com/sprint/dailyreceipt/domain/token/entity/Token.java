package com.sprint.dailyreceipt.domain.token.entity;

import com.sprint.dailyreceipt.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "token")
    private Account account;

    @Column(nullable = false, unique = true)
    private String uniqueIdBySocial;

    @Column(columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Builder
    public Token(Long id, String uniqueIdBySocial, String refreshToken) {
        this.id = id;
        this.uniqueIdBySocial = uniqueIdBySocial;
        this.refreshToken = refreshToken;
    }

    public void addAccount(Account account) {
        this.account = account;
        account.addToken(this);
    }

    public void exchangeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
