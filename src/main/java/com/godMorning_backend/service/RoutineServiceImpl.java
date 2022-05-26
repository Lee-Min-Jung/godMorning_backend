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

    //아이디랑 날짜로 루틴 찾기
    @Override
    public Routine findByIdDate(Long id, String create_date){
        return jdbcRoutineRepository.findByIdDate(id, create_date);

    }

    //루틴 저장
    @Override
    public int saveRoutine(Routine routine) {
        jdbcRoutineRepository.saveRoutine(routine);

        return 1;
    }

    //루틴 삭제
    @Override
    public String deleteRoutine(int post_no) {
        return jdbcRoutineRepository.deleteRoutine(post_no);
    }
}
