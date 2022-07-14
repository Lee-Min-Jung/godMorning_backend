package com.godMorning_backend.config;


import com.godMorning_backend.domain.Routine;

import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.repository.RoutineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class springConfig {

    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    public springConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Bean
    public RoutineRepository questionRepository() {
        return new JDBCRoutineRepository(dataSource);
    }

    @Bean
    Routine routine() {
        return new Routine();
    }







}
