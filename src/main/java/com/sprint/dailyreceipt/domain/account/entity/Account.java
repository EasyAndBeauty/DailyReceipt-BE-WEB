package com.sprint.dailyreceipt.domain.account.entity;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  private String nickname;

  @OneToMany(mappedBy = "account")
  private List<Todo> todos = new ArrayList<>();

  @Builder
  public Account(Long id, String nickname) {
    this.id = id;
    this.nickname = nickname;
  }

  public static Account of() {
    String temporaryNickname = UUID.randomUUID().toString().substring(0, 8);

    return Account.builder()
        .nickname(temporaryNickname)
        .build();
  }

  public void addTodo(Todo todo) {
    todos.add(todo);
  }

  public boolean isNotSame(Account account) {
    return !this.id.equals(account.getId());
  }

  public void update(Account account) {
    this.nickname = account.getNickname();
  }
}
