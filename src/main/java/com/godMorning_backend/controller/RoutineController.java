package com.godMorning_backend.controller;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.repository.JDBCRoutineRepository;
import com.godMorning_backend.service.RoutineServiceImpl;
import com.godMorning_backend.service.ToDoServiceImpl;
import com.mysql.cj.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoutineController {
    private JdbcTemplate jdbcTemplate;
    private RoutineServiceImpl routineServiceImpl;
    private ToDoServiceImpl toDoServiceImpl;

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
    //신규 루틴 조회
    @RequestMapping(value="newRoutine/list")
    public List<Routine> newRoutineList(){
        return routineServiceImpl.newRoutineList();
    }

    //신규 루틴 상세 조회
    @RequestMapping(value="newRoutine/list/{post_no}")
    public Routine newRoutineDetail(@PathVariable("post_no") Long post_no, HttpServletRequest request, Model model){

        return routineServiceImpl.newRoutineDetail(post_no);
    }

    //시간대 루틴 조회
    @RequestMapping(value="timezone/list/{startTime}")
    public List<Routine> timezoneList(@PathVariable("startTime") int startTime){
        return routineServiceImpl.startTimeList(startTime);
    }

    //시간대 루틴 상세조회
    @RequestMapping(value="timezone/list/{startTime}/{post_no}")
    public Routine timezoneDetail(@PathVariable("startTime") int startTime, @PathVariable("post_no") Long post_no){
        return routineServiceImpl.startTimeDetail(startTime, post_no);
    }

    //최신순 루틴 모두 보기

    //루틴 저장
    //create자리에 루틴 post_no가 오면 좋을 것 같음
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping(value = "routine/create",   produces = "application/json; charset=UTF-8")
    public Routine save(@RequestBody Routine routine) {

        //루틴
        Long count = jdbcRoutineRepository.controller_getPostNo();

        routine.setPost_no(++count);
        routine.setId(routine.getId());
        routine.setTitle(routine.getTitle());
        routine.setCreate_date(routine.getCreate_date());
        routine.setStartTime(routine.getStartTime());
        routine.setEndTime(routine.getEndTime());


        //투두
        int len = routine.getTodo_list().size();

        List<ToDo> set_todo_list = new ArrayList<>();


        String content = "";

        for(int i=0;i<len;i++){
            ToDo setToDo = new ToDo();

            content = routine.getTodo_list().get(i).getContent();

            setToDo.setPost_no(count);
            setToDo.setContent(content);

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
