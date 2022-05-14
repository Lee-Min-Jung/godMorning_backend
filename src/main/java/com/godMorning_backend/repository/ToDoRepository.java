package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;

public interface ToDoRepository {
    void saveToDo(ToDo todo);
}
