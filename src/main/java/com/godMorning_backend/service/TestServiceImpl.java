package com.godMorning_backend.service;

import com.godMorning_backend.domain.Test;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.repository.JDBCTestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestServiceImpl implements TestService{
    private final JDBCTestRepository jdbcTestRepository;

    public TestServiceImpl(JDBCTestRepository jdbcTestRepository) {
        this.jdbcTestRepository = jdbcTestRepository;
    }
    @Override
    public List<Test> findAll(String id) {

        return jdbcTestRepository.findAll(id);
    }
}
