package com.godMorning_backend.service;


import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.repository.JDBCToDoRepository;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService{
    private final JDBCToDoRepository jdbcToDoRepository;

    public ToDoServiceImpl(JDBCToDoRepository jdbcToDoRepository) {
        this.jdbcToDoRepository = jdbcToDoRepository;
    }

    //투두 저장
    @Override
    public int saveToDo(ToDo todo) {
        jdbcToDoRepository.saveToDo(todo);
        return 1;
    }
}
