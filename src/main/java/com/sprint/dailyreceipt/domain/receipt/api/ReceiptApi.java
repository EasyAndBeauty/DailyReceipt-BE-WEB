package com.sprint.dailyreceipt.domain.receipt.api;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptInfoResponse;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptRegisterRequest;
import com.sprint.dailyreceipt.domain.receipt.application.ReceiptProfileService;
import com.sprint.dailyreceipt.domain.receipt.application.ReceiptRegisterService;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import com.sprint.dailyreceipt.global.annotation.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReceiptApi {

    private final ReceiptRegisterService receiptRegisterService;

    private final ReceiptProfileService receiptProfileService;


    @PostMapping("/v1/receipt")
    public ResponseEntity<Long> registerReceipt(@Login Account account, @RequestBody ReceiptRegisterRequest registerRequest) {
        return new ResponseEntity<>(receiptRegisterService.register(account, registerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/v1/receipt")
    public List<ReceiptInfoResponse> searchReceipt(@Login Account account) {
        return receiptProfileService.findReceiptList(account);
    }
}
