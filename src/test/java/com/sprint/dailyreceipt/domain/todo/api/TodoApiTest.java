package com.sprint.dailyreceipt.domain.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.application.TodoService;
import com.sprint.dailyreceipt.domain.todo.api.TodoApi;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TodoController Unit Test")
class TodoApiTest {

    private MockMvc mockMvc;

    private TodoApi todoApi;

    private TodoService todoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        todoService = mock(TodoService.class);
        todoApi = new TodoApi(todoService);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(todoApi).build();
    }

    @Test
    @DisplayName("makeTodo() : Todo 항목이 정상일 경우, 정상적으로 Todo 생성 요청을 보낼 수 있다")
    void testMakeTodo() throws Exception {
        //given
        Todo todo = Todo.builder()
                        .task("TDD 공부")
                        .timer("230")
                        .isDone(true)
                        .build();

        String requestData = objectMapper.writeValueAsString(todo);

        //when
        when(todoService.save(any())).thenReturn(anyLong());

        //then
        mockMvc.perform(post("/api/v1/todo")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestData))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    @DisplayName("updateTodo() : 변경할 Todo 항목이 정상일 경우, 정상적으로 Todo 수정 요청을 보낼 수 있다")
    void testUpdateTodo() throws Exception {
        //given
        TodoCreateRequest updateRequest = TodoCreateRequest.builder()
                                                           .task("ATDD 공부")
                                                           .timer("270")
                                                           .isDone(false)
                                                           .date(LocalDate.now())
                                                           .build();

        TodoUpdateResponse updateResponse = new TodoUpdateResponse("ATDD 공부", "270", false);

        String requestData = objectMapper.registerModule(new JavaTimeModule())
                                         .writeValueAsString(updateRequest);

        //when
        when(todoService.update(any(), anyLong())).thenReturn(updateResponse);

        //then
        mockMvc.perform(put("/api/v1/todo/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestData))
               .andExpect(status().isOk())
               .andDo(print());
    }
}