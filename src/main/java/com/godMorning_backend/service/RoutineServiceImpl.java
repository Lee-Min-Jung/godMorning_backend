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

    //신규 루틴 조회

    @Override
    public List<Routine> newRoutineList() {
        return jdbcRoutineRepository.newRoutineList();
    }

    //신규 루틴 상세 조회


    @Override
    public Routine newRoutineDetail(Long post_no) {
        return jdbcRoutineRepository.newRoutineDetail(post_no);
    }

    //시간대 루틴 조회

    @Override
    public List<Routine> startTimeList(int startTime) {
        return jdbcRoutineRepository.startTimeList(startTime);
    }

    //시간대 루틴 상세조회


    @Override
    public Routine startTimeDetail(int startTime, Long post_no) {
        return jdbcRoutineRepository.startTimeDetail(startTime, post_no);
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
