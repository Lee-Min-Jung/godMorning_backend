package com.godMorning_backend.service;

import com.godMorning_backend.repository.JDBCWiseRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class WiseServiceImpl implements WiseService{
    private final JDBCWiseRepository jdbcWiseRepository;


    public WiseServiceImpl(JDBCWiseRepository jdbcWiseRepository) {
        this.jdbcWiseRepository = jdbcWiseRepository;
    }

    //랜덤 명언 조회
    @Override
    public String findByNo() {
        Random random = new Random();
        int random_no = random.nextInt(50) +1;
        return jdbcWiseRepository.findByNo(random_no);
    }
}
