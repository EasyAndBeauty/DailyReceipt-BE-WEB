package com.sprint.dailyreceipt.domain.receipt.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.domain.receipt.application.ReceiptModifyService;
import com.sprint.dailyreceipt.domain.receipt.application.ReceiptProfileService;
import com.sprint.dailyreceipt.domain.receipt.application.ReceiptRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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

@DisplayName("ReceiptApi Unit Test")
@ExtendWith(RestDocumentationExtension.class)
class ReceiptApiTest {

    private MockMvc mockMvc;

    private ReceiptRegisterService receiptRegisterService;

    private ReceiptProfileService receiptProfileService;

    private ReceiptModifyService receiptModifyService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init(RestDocumentationContextProvider restDocumentation) {
        receiptRegisterService = mock(ReceiptRegisterService.class);
        receiptProfileService = mock(ReceiptProfileService.class);
        receiptModifyService = mock(ReceiptModifyService.class);

        objectMapper = new ObjectMapper();

        ReceiptApi receiptApi = new ReceiptApi(receiptRegisterService, receiptProfileService, receiptModifyService);

        mockMvc = MockMvcBuilders.standaloneSetup(receiptApi)
                                 .apply(documentationConfiguration(restDocumentation)
                                                .operationPreprocessors()
                                                .withResponseDefaults(prettyPrint())
                                                .withRequestDefaults(prettyPrint()))
                                 .build();
    }

    @Test
    @DisplayName("makeRecepit() : Receipt ????????? ????????? ??????, ??????????????? Receipt ?????? ????????? ?????? ??? ??????")
    void testMakeReceipt() throws Exception {
        //given
        ReceiptRegisterRequest request = ReceiptRegisterRequest.builder()
                                                               .todoIds(List.of(1L, 2L, 3L))
                                                               .pinned(true)
                                                               .famousSaying("say")
                                                               .name("name1")
                                                               .build();

        //when
        String requestData = objectMapper.writeValueAsString(request);

        //then
        when(receiptRegisterService.register(any(), any()))
                .thenReturn(1L);

        mockMvc.perform(post("/api/v1/receipt/pinned")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
               .andExpect(status().isCreated())
               .andDo(document("post-receipt",
                               requestFields(
                                       attributes(key("title").value("/api/v1/receipt/pinned")),
                                       fieldWithPath("todoIds").description("???????????? ????????? todo Id???"),
                                       fieldWithPath("pinned").description("??? ??????"),
                                       fieldWithPath("famousSaying").description("??????"),
                                       fieldWithPath("name").description("???????????? ????????? ?????? ??????")
                               )));
    }

    @Test
    @DisplayName("searchReceipt() : ???????????? ??????????????? Receipt ?????? ????????? ?????? ??? ??????")
    void testSearchReceipt() throws Exception {
        //then
        mockMvc.perform(get("/api/v1/receipt/pinned")
                                .header(HttpHeaders.AUTHORIZATION, "Token"))
               .andExpect(status().isOk())
               .andDo(document("get-receipt",
                               requestParameters(
                                       attributes(key("title").value("/api/v1/receipt/pinned"))
                               ),
                               requestHeaders(
                                       headerWithName(HttpHeaders.AUTHORIZATION).description("JWT")
                               )));
    }

    @Test
    @DisplayName("modifyReceipt() : ???????????? ??????????????? Receipt ?????? ????????? ?????? ??? ??????.")
    void testModifyReceipt() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/receipt/{receipt-id}", 1L)
                                                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(document("put-receipt",
                               pathParameters(
                                       parameterWithName("receipt-id").description("receipt ?????? ???")
                               )));
    }
}