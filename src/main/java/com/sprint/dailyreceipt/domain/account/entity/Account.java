package com.sprint.dailyreceipt.domain.account.entity;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.token.entity.Token;
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    @OneToMany(mappedBy = "account")
    private List<Todo> todos = new ArrayList<>();

    public Account(Long id) {
        this.id = id;
    }

    @Builder
    public Account(String nickname, Token token) {
        this.nickname = nickname;
        this.token = token;
    }

    public static Account of(Token token) {
        String temporaryNickname = UUID.randomUUID().toString().substring(0, 8);

        return Account.builder()
                      .token(token)
                      .nickname(temporaryNickname)
                      .build();
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    public void addToken(Token token) {
        this.token = token;
    }
}
