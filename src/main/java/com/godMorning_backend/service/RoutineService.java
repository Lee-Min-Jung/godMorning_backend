package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;

import java.util.List;

public interface RoutineService {

    //루틴 저장
    int saveRoutine(Routine routine);

    //아이디랑 날짜로 루틴 찾기
    Routine findByIdDate(Long id, String create_date);

    //루틴 삭제
    String deleteRoutine(int post_no);

    //신규 루틴 조회
    List<Routine> newRoutineList();

    //신규 루틴 상세 조회
    Routine newRoutineDetail(Long post_no);

    //시간대 루틴 조회
    List<Routine> startTimeList(String startTime);

    //루틴 수정

}
