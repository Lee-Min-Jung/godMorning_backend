package com.godMorning_backend.controller;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.service.RoutineServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RoutineController {
    private JdbcTemplate jdbcTemplate;
    private RoutineServiceImpl routineServiceImpl;
    private Routine routine;
    private JDBCRoutineRepository jdbcRoutineRepository;


    public RoutineController(JdbcTemplate jdbcTemplate, RoutineServiceImpl routineServiceImpl, JDBCRoutineRepository jdbcRoutineRepository, Routine routine ) {
        this.jdbcTemplate = jdbcTemplate;
        this.routineServiceImpl = routineServiceImpl;
        this.jdbcRoutineRepository = jdbcRoutineRepository;
        this.routine = routine;
    }



    @PostMapping(value = "routine/create",   produces = "application/json; charset=UTF-8")
    public Routine save(@RequestBody Routine routine, HttpServletResponse response) {

        String userId = "";
        String title = "";
        String timezone = "";
        String toDo1 = "";
        String toDo2 = "";
        String toDo3 = "";
        String toDo4 = "";
        String toDo5 = "";
        String toDo6 = "";
        String toDo7 = "";
        String toDo8 = "";
        String toDo9 = "";
        String toDo10 = "";

        userId = routine.getUserId();
        title = routine.getTitle();
        timezone = routine.getTimezone();
        toDo1 = routine.getToDo1();
        toDo2 = routine.getToDo1();
        toDo3 = routine.getToDo1();
        toDo4 = routine.getToDo1();
        toDo5 = routine.getToDo1();
        toDo6 = routine.getToDo1();
        toDo7 = routine.getToDo1();
        toDo8 = routine.getToDo1();
        toDo9 = routine.getToDo1();
        toDo10 = routine.getToDo1();

        routine.setUserId(userId);
        routine.setTitle(title);
        routine.setTimezone(timezone);
        routine.setToDo1(toDo1);
        routine.setToDo1(toDo2);
        routine.setToDo1(toDo3);
        routine.setToDo1(toDo4);
        routine.setToDo1(toDo5);
        routine.setToDo1(toDo6);
        routine.setToDo1(toDo7);
        routine.setToDo1(toDo8);
        routine.setToDo1(toDo9);
        routine.setToDo1(toDo10);

        routineServiceImpl.saveRoutine(routine);

        return routine;
    }
}
