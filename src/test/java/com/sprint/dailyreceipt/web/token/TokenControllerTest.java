package com.sprint.dailyreceipt.web.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.dailyreceipt.domain.account.Account;
import com.sprint.dailyreceipt.domain.token.entity.Token;
import com.sprint.dailyreceipt.domain.token.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TokenController Unit Test")
class TokenControllerTest {

    private MockMvc mockMvc;

    private TokenController tokenController;

    private TokenService tokenService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        tokenService = mock(TokenService.class);
        tokenController = new TokenController(tokenService);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
    }

    @Test
    @DisplayName("reIssuance() : account 는 재발급 요청을 할 수 있다")
    void testReIssue() throws Exception {
        //given
        Token token = Token.builder()
                             .refreshToken(
                                     "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyZWNlaXB0Iiwic3ViIjoidW5pcXVlMSIsImF1ZCI6IlJFRlJFU0giLCJleHAiOjE2OTM5MjE1ODcsImlhdCI6MTY2MjM4NTU4NywiYXV0aCI6IlVTRVIifQ.qy27pR38E2zgHTLxxXy-zwfp4HpAtWFTbvB_0QhJxpEDbb-eBV1uNHXqalYeizCl6MLAFiJS2tlmxZVkJrOAyw")
                             .uniqueIdBySocial("unique1")
                             .build();

        Account account = Account.builder()
                                 .nickname("account")
                                 .token(token)
                                 .build();

        String requestData = objectMapper.writeValueAsString(account);

        //when
        when(tokenService.reIssue(any())).thenReturn(any());

        //then
        mockMvc.perform(post("/api/v1/tokens/re-issuance")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestData))
               .andExpect(status().isOk())
               .andDo(print());
    }
}