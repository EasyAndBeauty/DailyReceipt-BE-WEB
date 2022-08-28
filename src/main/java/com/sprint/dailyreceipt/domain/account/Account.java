package com.sprint.dailyreceipt.domain.account;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String email;

    @Column(name = "unique_id_by_social")
    private String uniqueIdBySocial;

    @OneToMany(mappedBy = "account")
    private List<Todo> todos = new ArrayList<>();

    public Account(Long id) {
        this.id = id;
    }

    private Account(String email, String uniqueIdBySocial) {
        this.email = email;
        this.uniqueIdBySocial = uniqueIdBySocial;
    }

    public static Account of(String email, String uniqueIdBySocial) {
        return new Account(email, uniqueIdBySocial);
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
    }
}
