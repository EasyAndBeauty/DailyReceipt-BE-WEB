package com.sprint.dailyreceipt.domain.todo.repository;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepositoryCustom {


    List<Todo> findTodoBySocialId(String accountSocialId);
}
