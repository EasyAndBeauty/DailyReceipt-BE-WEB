package com.sprint.dailyreceipt.domain.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.application.TodoCreateService;
import com.sprint.dailyreceipt.domain.todo.application.TodoService;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TodoApi Unit Test")
class TodoApiTest {

    private MockMvc mockMvc;

    private TodoApi todoApi;

    private ObjectMapper objectMapper;

    private TodoCreateService todoCreateService;

    @BeforeEach
    public void init() {
        todoCreateService = mock(TodoCreateService.class);
        todoApi = new TodoApi(todoCreateService);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(todoApi).build();
    }

    @Test
    @DisplayName("makeTodo() : Todo 항목이 정상일 경우, 정상적으로 Todo 생성 요청을 보낼 수 있다.")
    void testMakeTodo() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                               .date(LocalDate.now())
                                                               .isDone(true)
                                                               .timer("05:07")
                                                               .task("TDD 공부")
                                                               .build();

        String requestData = objectMapper.writeValueAsString(todoCreateRequest);

        //when
        when(todoCreateService.create(any(), any()))
                .thenReturn(1L);

        //then
        mockMvc.perform(post("/api/v1/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
               .andExpect(status().isCreated())
               .andDo(print());
    }
}