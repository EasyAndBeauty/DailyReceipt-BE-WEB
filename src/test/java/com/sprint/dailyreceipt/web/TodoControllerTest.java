package com.sprint.dailyreceipt.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import com.sprint.dailyreceipt.domain.todo.service.TodoService;
import com.sprint.dailyreceipt.web.todo.TodoController;
import com.sprint.dailyreceipt.web.todo.model.TodoCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TodoController Unit Test")
class TodoControllerTest {

    private MockMvc mockMvc;

    private TodoController todoController;

    private TodoService todoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        todoService = mock(TodoService.class);
        todoController = new TodoController(todoService);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
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
        TodoCreateRequest updateRequest = new TodoCreateRequest("ATDD 공부", "270", false);

        String requestData = objectMapper.registerModule(new JavaTimeModule())
                                         .writeValueAsString(updateRequest);

        //when
        when(todoService.update(any(), anyLong())).thenReturn(updateRequest);

        //then
        mockMvc.perform(put("/api/v1/todo/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestData))
               .andExpect(status().isOk())
               .andDo(print());
    }
}