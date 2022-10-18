package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.exception.AccountMisMatchException;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.domain.todo.dao.TodoRepository;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoRemoveService {

    private final TodoDao todoDao;

    private final TodoRepository todoRepository;

    //TODO : TodoDao , TodoRepository 를 모두 의존하는게 맞는 것일까? TodoRepository 로만 조회, 삭제 둘 다 가능
    public void deleteTodo(Account account, long todoId) {

        Todo savedTodo = todoDao.findTodoById(todoId);

        if (account.isNotSame(savedTodo.getAccount())) {
            throw new AccountMisMatchException();
        }

        todoRepository.delete(savedTodo);
    }
}
