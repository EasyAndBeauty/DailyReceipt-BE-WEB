package com.sprint.dailyreceipt.domain.todo.entity;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.common.BaseEntity;
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
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private String timer;

    private boolean isDone;

    private LocalDate date;

    @Builder
    public Todo(Long id, Account account, String task, String timer, boolean isDone, LocalDate date, ZonedDateTime createdAt,
                ZonedDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.account = account;
        this.task = task;
        this.timer = timer;
        this.isDone = isDone;
        this.date = date;
    }

    public Todo update(Todo updatedTodo) {
        this.task = updatedTodo.getTask();
        this.timer = updatedTodo.getTimer();
        this.isDone = updatedTodo.isDone();
        setUpdatedAt(updatedTodo.getUpdatedAt());

        return this;
    }
}
