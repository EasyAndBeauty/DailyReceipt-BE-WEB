package com.sprint.dailyreceipt.domain.todo.repository;

import com.sprint.dailyreceipt.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
}
