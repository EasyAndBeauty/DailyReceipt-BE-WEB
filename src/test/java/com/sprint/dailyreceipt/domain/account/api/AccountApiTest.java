package com.sprint.dailyreceipt.domain.account.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.dailyreceipt.domain.account.api.model.AccountInfoResponse;
import com.sprint.dailyreceipt.domain.account.api.model.AccountUpdateRequest;
import com.sprint.dailyreceipt.domain.account.application.AccountProfileService;
import com.sprint.dailyreceipt.domain.account.application.AccountUpdateService;
import com.sprint.dailyreceipt.domain.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AccountApi Unit Test")
@ExtendWith(RestDocumentationExtension.class)
class AccountApiTest {

    private MockMvc mockMvc;

    private AccountProfileService accountProfileService;

    private AccountUpdateService accountUpdateService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init(RestDocumentationContextProvider restDocumentation) {

        accountProfileService = mock(AccountProfileService.class);
        accountUpdateService = mock(AccountUpdateService.class);

        objectMapper = new ObjectMapper();

        AccountApi accountApi = new AccountApi(accountProfileService, accountUpdateService);

        mockMvc = MockMvcBuilders.standaloneSetup(accountApi)
                                 .apply(documentationConfiguration(restDocumentation)
                                                .operationPreprocessors()
                                                .withResponseDefaults(prettyPrint())
                                                .withRequestDefaults(prettyPrint()))
                                 .build();
    }

    @Test
    @DisplayName("searchAccount() : 기존 사용자일 경우, 정상적으로 Account 정보 조회 요청을 보낼 수 있다.")
    void testSearchAccount() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("user1")
                                 .build();

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse(account);

        //when
        when(accountProfileService.findAccountInfo(any()))
                .thenReturn(accountInfoResponse);

        //then
        mockMvc.perform(get("/api/v1/user")
                                .header(HttpHeaders.AUTHORIZATION, "Token"))
               .andExpect(status().isOk())
               .andDo(document("get-account",
                               requestHeaders(
                                       headerWithName(HttpHeaders.AUTHORIZATION).description("JWT")
                               )));
    }

    @Test
    @DisplayName("updateAccount() : 사용자는 정상적으로 사용자 정보 수정 요청을 보낼 수 있다")
    void testUpdateAccount() throws Exception {
        //given
        Account account = Account.builder()
                                 .nickname("user2")
                                 .build();

        AccountUpdateRequest accountUpdateRequest = new AccountUpdateRequest("user2");

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse(account);

        String requestData = objectMapper.writeValueAsString(accountUpdateRequest);

        //when
        when(accountUpdateService.update(any(), any()))
                .thenReturn(accountInfoResponse);

        //then
        mockMvc.perform(put("/api/v1/user")
                                .header(HttpHeaders.AUTHORIZATION, "Token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
               .andExpect(status().isOk())
               .andDo(document("put-account",
                               requestFields(
                                       attributes(key("title").value("api/v1/user")),
                                       fieldWithPath("nickname").description("변경할 nickname")
                               ),
                               requestHeaders(
                                       headerWithName(HttpHeaders.AUTHORIZATION).description("JWT")
                               )));
    }
}