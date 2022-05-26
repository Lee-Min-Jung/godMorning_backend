package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Test;
import com.godMorning_backend.domain.ToDo;

import java.util.List;

public interface RoutineRepository {
    // 루틴 저장
    void saveRoutine(Routine routine);

    // 회원아이디와 날짜로 조회
    Routine findByIdDate(Long id, String create_date);

    // 루틴 삭제
    String deleteRoutine(int post_no);



}
