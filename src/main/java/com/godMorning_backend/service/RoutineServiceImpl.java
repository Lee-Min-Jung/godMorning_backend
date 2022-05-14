package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.repository.JDBCToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineServiceImpl implements RoutineService{
    private final JDBCRoutineRepository jdbcRoutineRepository;


    public RoutineServiceImpl(JDBCRoutineRepository jdbcRoutineRepository) {
        this.jdbcRoutineRepository = jdbcRoutineRepository;
    }

    @Override
    public int saveRoutine(Routine routine) {
        jdbcRoutineRepository.saveRoutine(routine);

        return 1;
    }

    @Override
    public List<Routine> findById(String userId) {
        return null;
    }
}
