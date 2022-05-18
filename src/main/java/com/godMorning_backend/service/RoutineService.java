package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;

import java.util.List;

public interface RoutineService {
    int saveRoutine(Routine routine);

    Routine findByIdDate(Long id, String create_date);
}
