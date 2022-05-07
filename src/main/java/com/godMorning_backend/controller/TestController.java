package com.godMorning_backend.controller;

import com.godMorning_backend.domain.Test;
import com.godMorning_backend.repository.JDBCTestRepository;
import com.godMorning_backend.service.TestServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
public class TestController {
    private JdbcTemplate jdbcTemplate;
    private TestServiceImpl testServiceImpl;
    private Test test;
    private JDBCTestRepository jdbcTestRepository;


    public TestController(JdbcTemplate jdbcTemplate, TestServiceImpl testServiceImpl, JDBCTestRepository jdbcTestRepository, Test test ) {
        this.jdbcTemplate = jdbcTemplate;
        this.testServiceImpl = testServiceImpl;
        this.jdbcTestRepository = jdbcTestRepository;
        this.test = test;
    }

    @RequestMapping(value = "test/list")
    public List<Test> findAll(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        return testServiceImpl.findAll(id);
    }
}
