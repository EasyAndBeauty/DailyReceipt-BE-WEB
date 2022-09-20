package com.sprint.dailyreceipt.domain.todo.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sprint.dailyreceipt.domain.account.entity.QAccount.account;
import static com.sprint.dailyreceipt.domain.todo.entity.QTodo.*;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Todo> findTodosByAccountId(long accountId) {
        return queryFactory.selectFrom(todo)
                           .innerJoin(todo.account, account).fetchJoin()
                           .fetch();
    }
}
