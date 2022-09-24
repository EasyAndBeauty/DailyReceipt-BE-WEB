package com.sprint.dailyreceipt.domain.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoCreateRequest;
import com.sprint.dailyreceipt.domain.todo.api.model.TodoInfoResponse;
import com.sprint.dailyreceipt.domain.todo.application.TodoCreateService;
import com.sprint.dailyreceipt.domain.todo.application.TodoProfileService;
import com.sprint.dailyreceipt.domain.todo.application.TodoRemoveService;
import com.sprint.dailyreceipt.domain.todo.application.TodoUpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TodoApi Unit Test")
@ExtendWith(RestDocumentationExtension.class)
class TodoApiTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private TodoCreateService todoCreateService;

    private TodoProfileService todoProfileService;

    private TodoUpdateService todoUpdateService;

    private TodoRemoveService todoRemoveService;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        todoCreateService = mock(TodoCreateService.class);
        todoProfileService = mock(TodoProfileService.class);
        todoUpdateService = mock(TodoUpdateService.class);
        todoRemoveService = mock(TodoRemoveService.class);

        TodoApi todoApi = new TodoApi(todoCreateService, todoProfileService,
                                      todoUpdateService, todoRemoveService);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(todoApi)
                                 .apply(documentationConfiguration(restDocumentation)
                                                .operationPreprocessors()
                                                .withResponseDefaults(prettyPrint())
                                                .withRequestDefaults(prettyPrint()))
                                 .build();

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
               .andDo(document("post-todo",
                               requestFields(
                                       attributes(key("title").value("/api/v1/todo")),
                                       fieldWithPath("task").description("todo 이름"),
                                       fieldWithPath("timer").description("todo 시간"),
                                       fieldWithPath("isDone").description("todo 완료 여부"),
                                       fieldWithPath("date").description("todo 작성 날짜")
                               )));
    }

    @Test
    @DisplayName("searchTodo() : 사용자는 정상적으로 Todo 조회 요청을 보낼 수 있다.")
    void testSearchTodo() throws Exception {
        //given
        String targetDate = "2022-02-04";

        TodoInfoResponse todo1 = TodoInfoResponse.builder()
                                                 .date("2022-02-04")
                                                 .isDone(true)
                                                 .timer("06:06")
                                                 .task("TDD 공부")
                                                 .build();

        TodoInfoResponse todo2 = TodoInfoResponse.builder()
                                                 .date("2022-02-04")
                                                 .isDone(true)
                                                 .timer("06:08")
                                                 .task("ATDD 공부")
                                                 .build();

        Account account = Account.builder()
                                 .nickname("user1")
                                 .build();

        List<TodoInfoResponse> todoList = List.of(todo1, todo2);

        //when
        when(todoProfileService.findTodoList(account, null))
                .thenReturn(todoList);

        //then

        mockMvc.perform(get("/api/v1/todo")
                                .param("targetDate", targetDate))
               .andExpect(status().isOk())
               .andDo(document("get-todo",
                               requestParameters(
                                       attributes(key("title").value("/api/v1/todo?targetDate")),
                                       parameterWithName("targetDate").description("조회할 Todo 날짜").optional()
                               )));

    }

    @Test
    @DisplayName("updateTodo() : 사용자는 정상적으로 Todo 수정 요청을 보낼 수 있다.")
    void testUpdateTodo() throws Exception {
        //given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                                                            .task("TDD 공부")
                                                            .timer("14:24")
                                                            .isDone(true)
                                                            .date(LocalDate.now())
                                                            .build();

        TodoInfoResponse todoInfoResponse = TodoInfoResponse.builder()
                                                            .task("TDD 공부")
                                                            .timer("14:24")
                                                            .isDone(true)
                                                            .date(LocalDate.now().toString())
                                                            .todoId(1L)
                                                            .build();

        String requestData = objectMapper.writeValueAsString(todoCreateRequest);

        //when
        when(todoUpdateService.update(any(), anyLong(), any()))
                .thenReturn(todoInfoResponse);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/todo/{todo-id}", 1L)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(requestData))
               .andExpect(status().isOk())
               .andDo(document("put-todo",
                               pathParameters(
                                       parameterWithName("todo-id").description("todo 식별 값")
                               ),
                               requestFields(
                                       attributes(key("title").value("/api/v1/todo/{todo-id}")),
                                       fieldWithPath("task").description("todo 이름"),
                                       fieldWithPath("timer").description("todo 시간"),
                                       fieldWithPath("isDone").description("todo 완료 여부"),
                                       fieldWithPath("date").description("todo 작성 날짜")
                               )));
    }

    @Test
    @DisplayName("removeTodo() : 사용자는 정상적으로 Todo 삭제 요청을 보낼 수 있다.")
    void testRemoveTodo() throws Exception {

        //when
        doNothing().when(todoRemoveService).deleteTodo(any(), anyLong());

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/todo/{todo-id}", 1L))
               .andExpect(status().isOk())
               .andDo(document("delete-todo",
                               pathParameters(
                                       parameterWithName("todo-id").description("todo 식별 값")
                               )
               ));
    }
}