package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Test;

import java.util.List;

public interface TestRepository {
    List<Test> findAll(String id);
}
