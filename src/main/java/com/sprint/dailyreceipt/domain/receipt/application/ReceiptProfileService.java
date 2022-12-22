package com.sprint.dailyreceipt.domain.receipt.application;

import com.sprint.dailyreceipt.domain.account.entity.Account;
import com.sprint.dailyreceipt.domain.receipt.api.model.ReceiptInfoResponse;
import com.sprint.dailyreceipt.domain.receipt.dao.ReceiptDao;
import com.sprint.dailyreceipt.domain.receipt.entity.Receipt;
import com.sprint.dailyreceipt.domain.todo.application.TodoProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptProfileService {

    private final ReceiptDao receiptDao;
    private final TodoProfileService todoProfileService;

    public List<ReceiptInfoResponse> findReceiptList(Account account) {
        return receiptDao.findReceiptByAccountId(account.getId())
                         .stream()
                         .filter(Receipt::isPinned)
                         .map(receipt -> ReceiptInfoResponse.of(todoProfileService.findTodoListByTodoIds(receipt.getTodoIds()),
                                                                receipt.getFamousSaying(),
                                                                receipt.getName(),
                                                                receipt.getId()))
                         .collect(Collectors.toList());
    }
}
