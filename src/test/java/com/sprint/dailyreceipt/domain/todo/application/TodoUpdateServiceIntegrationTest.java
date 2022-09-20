package com.sprint.dailyreceipt.domain.todo.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.account.exception.AccountMisMatchException;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.dao.TodoDao;
import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TodoUpdateService Integration Test")
public class TodoUpdateServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TodoUpdateService todoUpdateService;

    @Autowired
    TodoDao todoDao;

    @Test
    @DisplayName("update() : 수정하려는 사용자와 Todo 를 작성한 사용자가 같지 않으면 AccountMisMatchException 예외를 던집니다.")
    void testUpdateThrowAccountMisMatchException() throws Exception {
        //given
        Account account = testAccount(2L);

        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .date(LocalDate.now())
                                                               .isDone(true)
                                                               .timer("05:07")
                                                               .task("ATDD 공부")
                                                               .build();

        //when & then
        assertThrows(
                AccountMisMatchException.class, () -> todoUpdateService.update(account, 1L, todoCreateRequest)
        );
    }
}
