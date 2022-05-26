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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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


//    @PostMapping(value = "todo/create", produces = "application/json; charset_UTF-8")
//    public ToDo save(@RequestBody ToDo todo){
//        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");
//        //투두
//
//        int post_no = 0;
//        String content = "";
//        int check_do = 0;
//
//
//        post_no = todo.getPost_no();
//        content = todo.getContent();
//        check_do = todo.getCheck_do();
//
//
//        todo.setPost_no(post_no);
//        todo.setContent(content);
//        todo.setCheck_do(0);
//
//        toDoServiceImpl.saveToDo(todo);
//
//        return todo;
//    }

    //아이디와 날짜로 루틴 조회
    @RequestMapping(value = "todo/list")
    public Routine findByIdDate(HttpServletRequest request, Model model){
        Long id = Long.parseLong(request.getParameter("id"));
        String create_date = request.getParameter("create_date");
        model.addAttribute("id",id);
        model.addAttribute("create_date",create_date);


        return routineServiceImpl.findByIdDate(id, create_date);

    }

    //루틴 저장
    //create자리에 루틴 post_no가 오면 좋을 것 같음
    @PostMapping(value = "routine/create",   produces = "application/json; charset=UTF-8")
    public Routine save(@RequestBody Routine routine) {
        SessionUser user = (SessionUser) httpSession.getAttribute("google_user");

        //루틴
        Long id = 0L;
        String title = "";
        String create_date = "";
        String startTime = "";
        String endTime = "";

        id = routine.getId();
        title = routine.getTitle();
        create_date = routine.getCreate_date();
        startTime = routine.getStartTime();
        endTime = routine.getEndTime();

        routine.setId(id);
        routine.setTitle(title);
        routine.setCreate_date(create_date);
        routine.setStartTime(startTime);
        routine.setEndTime(endTime);


        //투두
        int len = routine.getTodo_list().size();

        List<ToDo> set_todo_list = new ArrayList<>();

        int post_no = 0;
        String content = "";
        //int check_do = 0;

        for(int i=0;i<len;i++){
            ToDo setToDo = new ToDo();
            post_no = routine.getTodo_list().get(i).getPost_no();
            content = routine.getTodo_list().get(i).getContent();
            //check_do = routine.getTodo_list().get(i).getCheck_do();

            setToDo.setPost_no(post_no);
            setToDo.setContent(content);
            setToDo.setCheck_do(0);

            set_todo_list.add(setToDo);

        }
        routine.setTodo_list(set_todo_list);
        routineServiceImpl.saveRoutine(routine);

        return routine;
    }

    //루틴 삭제
    @RequestMapping(value="routine/delete")
    public String deleteRoutine(HttpServletRequest request, Model model){
        int post_no = Integer.parseInt(request.getParameter("post_no"));
        model.addAttribute("post_no",post_no);
        return routineServiceImpl.deleteRoutine(post_no);

    }



}
