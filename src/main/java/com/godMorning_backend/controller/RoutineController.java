package com.godMorning_backend.controller;

import com.godMorning_backend.config.auth.dto.SessionUser;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.service.RoutineServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class RoutineController {
    private JdbcTemplate jdbcTemplate;
    private RoutineServiceImpl routineServiceImpl;
    private Routine routine;
    private JDBCRoutineRepository jdbcRoutineRepository;
    private HttpSession httpSession;

    public RoutineController(JdbcTemplate jdbcTemplate, RoutineServiceImpl routineServiceImpl, JDBCRoutineRepository jdbcRoutineRepository, Routine routine,HttpSession httpSession ) {
        this.jdbcTemplate = jdbcTemplate;
        this.routineServiceImpl = routineServiceImpl;
        this.jdbcRoutineRepository = jdbcRoutineRepository;
        this.routine = routine;
        this.httpSession = httpSession;
    }



    @PostMapping(value = "routine/create",   produces = "application/json; charset=UTF-8")
    public Routine save(@RequestBody Routine routine) {
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");


        Long id = 0L;
        String title = "";
        String timezone = "";
        String create_time = "";


        id = user.getId();
        title = routine.getTitle();
        timezone = routine.getTimezone();
        create_time = routine.getCreate_time();

       routine.setId(id);
       routine.setTitle(title);
       routine.setTimezone(timezone);
       routine.setCreate_time(create_time);

        routineServiceImpl.saveRoutine(routine);

        return routine;
    }
}
