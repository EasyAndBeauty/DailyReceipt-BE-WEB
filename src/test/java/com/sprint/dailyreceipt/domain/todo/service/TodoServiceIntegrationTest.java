package com.sprint.dailyreceipt.domain.todo.service;

import com.sprint.dailyreceipt.support.AbstractIntegrationTest;
import com.sprint.dailyreceipt.web.model.TodoCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TodoService Integration Test")
public class TodoServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("save() : Todo 항목이 정상일 경우, 정상적으로 Todo를 저장할 수 있다")
    void testSave() throws Exception {
        //given
        TodoCreateRequest request = new TodoCreateRequest("TDD 공부", "250", true);

        //when
        long savedId = todoService.save(request);

        //then
        assertThat(savedId).isEqualTo(1L);
    }
}
