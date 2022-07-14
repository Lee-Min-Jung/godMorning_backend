package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;

import java.util.List;

public interface RoutineRepository {
    // 루틴 저장
    void saveRoutine(Routine routine);

    // 회원아이디와 날짜로 조회
    Routine findByIdDate(Long id, String create_date);

    // 루틴 삭제
    String deleteRoutine(int post_no);

    //루틴 수정
    void updateRoutine(Routine routine);

    //신규 루틴 조회
    List<Routine>  newRoutineList();

    //신규 루틴 상세 조회
    Routine newRoutineDetail(Long post_no);

    //시간대별 루틴 조회
    List<Routine> startTimeList(int startTime);

    //시간대별 루틴 상세조회
    Routine startTimeDetail(int startTime, Long post_no);

    //나의 루틴 전체 조회
    List<Routine> myRoutine(Long id);

    //나의 루틴 상세 보기
    Routine myRoutineDetail(Long id, Long post_no);

}
