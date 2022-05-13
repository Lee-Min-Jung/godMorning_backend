package com.godMorning_backend.controller;

import com.godMorning_backend.config.auth.dto.SessionUser;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.service.RoutineServiceImpl;
import com.godMorning_backend.service.ToDoServiceImpl;
import com.mysql.cj.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class RoutineController {
    private JdbcTemplate jdbcTemplate;
    private RoutineServiceImpl routineServiceImpl;
    private ToDoServiceImpl toDoServiceImpl;
    private SessionUser sessionUser;
    private JDBCRoutineRepository jdbcRoutineRepository;
    private final HttpSession httpSession;

    public RoutineController(JdbcTemplate jdbcTemplate, ToDoServiceImpl toDoServiceImpl, RoutineServiceImpl routineServiceImpl, JDBCRoutineRepository jdbcRoutineRepository,HttpSession httpSession ) {
        this.jdbcTemplate = jdbcTemplate;
        this.routineServiceImpl = routineServiceImpl;
        this.jdbcRoutineRepository = jdbcRoutineRepository;
        this.toDoServiceImpl = toDoServiceImpl;
        this.httpSession = httpSession;
    }

    @PostMapping(value = "todo/create", produces = "application/json; charset_UTF-8")
    public ToDo save(@RequestBody ToDo todo){
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");
        //투두

        int post_no = 0;
        String content = "";
        int check_do = 0;


        post_no = todo.getPost_no();
        content = todo.getContent();
        check_do = todo.getCheck_do();


        todo.setPost_no(post_no);
        todo.setContent(content);
        todo.setCheck_do(0);

        toDoServiceImpl.saveToDo(todo);

        return todo;
    }

    @PostMapping(value = "routine/create",   produces = "application/json; charset=UTF-8")
    public Routine save(@RequestBody Routine routine, Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");

        //루틴
        Long id = 0L;
        String title = "";
        String timezone = "";
        String create_time = "";

        id = 1L;
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
