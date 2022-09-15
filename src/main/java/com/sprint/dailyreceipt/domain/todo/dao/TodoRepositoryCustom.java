package com.sprint.dailyreceipt.domain.todo.dao;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;

import java.util.List;

public interface TodoRepositoryCustom {


    List<Todo> findTodoBySocialId(String accountSocialId);
}
