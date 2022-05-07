package com.godMorning_backend.service;

import com.godMorning_backend.domain.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll(String id);
}
