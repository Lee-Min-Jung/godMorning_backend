package com.godMorning_backend.service;

import com.godMorning_backend.domain.Routine;

import java.util.List;

public interface RoutineService {
    int saveRoutine(Routine routine);

    List<Routine> findById(String userId);
}
