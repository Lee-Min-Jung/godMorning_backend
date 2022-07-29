package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Heart;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.godMorning_backend.domain.User;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class JDBCRoutineRepository implements RoutineRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCRoutineRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //id랑 날짜로 루틴 찾기
    @Override
    public Routine findByIdDate(Long id, String create_date){
        String sql = "select * from Routine where id = ? and create_date = ? ";
        List<Routine> result = jdbcTemplate.query(sql, RoutineRowMapper(), id, create_date);


        String sql2 = "select * from ToDo where post_no = (select post_no from Routine where id = ? and create_date = ?)";
        List<ToDo> result2 = jdbcTemplate.query(sql2, ToDoRowMapper(), id, create_date);
        result.get(0).setTodo_list(result2);
        return result.get(0);
    }

    //루틴 저장
    @Override
    public void saveRoutine(Routine routine) {
        String sql = "INSERT INTO Routine(post_no, id, title, create_date, startTime, endTime) VALUES (?,?,?,?,?,?)";
        Object[] Params = {routine.getPost_no(), routine.getId(), routine.getTitle(), routine.getCreate_date(), routine.getStartTime(), routine.getEndTime()};
        jdbcTemplate.update(sql,Params);

        int len = routine.getTodo_list().size();
        for(int i=0;i<len;i++){
            String sql2 = "INSERT INTO ToDo(post_no, content) VALUES (?,?)";
            Object[] Params2 = {routine.getTodo_list().get(i).getPost_no(), routine.getTodo_list().get(i).getContent() };
            jdbcTemplate.update(sql2, Params2);
        }

    }

    //루틴 수정
    @Override
    public void updateRoutine(Routine routine) {

    }


    //신규 루틴 전체 조회
    @Override
    public List<Routine> newRoutineList() {


        //
        String sql1 = "select * from Routine order by post_no desc";
        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper());
        return result;
    }

    //신규 루틴 상세조회
    @Override
    public Routine newRoutineDetail(Long post_no) {

        //id 뽑기
        String sql0 = "select * from Routine where post_no = ?";
        List<Routine> result0 = jdbcTemplate.query(sql0, RoutineRowMapper(), post_no);
        Long idResult = result0.get(0).getId();


        //뽑은 id로 name 뽑기
        String sql01 = "select * from user where id = ?";
        List<User> result01 = jdbcTemplate.query(sql01, UserRowMapper(), idResult );
        String nameResult = result01.get(0).getNickname();

        //post_no에 맞는 루틴 뽑기
        String sql1 = "select * from Routine where post_no = ?";
        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper(), post_no);
        Routine newRoutineDetail = result.get(0);

        //post_no에 맞는 투두뽑기
        String sql2 = "select * from ToDo where post_no = ?";
        List<ToDo> result2 = jdbcTemplate.query(sql2, ToDoRowMapper(), post_no);

        //routine에 todo랑 name 설정
        newRoutineDetail.setTodo_list(result2);
        newRoutineDetail.setNickname(nameResult);

        return newRoutineDetail;
    }



    //시간대별 루틴
    @Override
    public List<Routine> startTimeList(int startTime) {
        String sql1 = "select * from Routine where startTime = ?";
        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper(), startTime);
        return result;
    }

    //시간대별 루틴 상세조회

    @Override
    public Routine startTimeDetail(int startTime, Long post_no) {
        //id 뽑기
        String sql0 = "select * from Routine where post_no = ?";
        List<Routine> result0 = jdbcTemplate.query(sql0, RoutineRowMapper(), post_no);
        Long idResult = result0.get(0).getId();

        //뽑은 id로 name 뽑기
        String sql01 = "select * from user where id = ?";
        List<User> result01 = jdbcTemplate.query(sql01, UserRowMapper(), idResult );
        String nameResult = result01.get(0).getNickname();

        //post_no에 맞는 루틴 뽑기
        String sql1 = "select * from Routine where post_no = ?";
        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper(), post_no);
        Routine timezoneDetail = result.get(0);

        //post_no에 맞는 투두뽑기
        String sql2 = "select * from ToDo where post_no = ?";
        List<ToDo> result2 = jdbcTemplate.query(sql2, ToDoRowMapper(), post_no);

        //routine에 todo랑 name 설정
        timezoneDetail.setTodo_list(result2);
        timezoneDetail.setNickname(nameResult);
//        String sql1 = "select * from Routine where startTime = ? and post_no = ?";
//        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper(), startTime, post_no);
//        Routine startTimeDetail = result.get(0);
//
//        String sql2 = "select * from ToDo where post_no = ?";
//        List<ToDo> result2 = jdbcTemplate.query(sql2, ToDoRowMapper(), post_no);
//
//
//        startTimeDetail.setTodo_list(result2);
//
//
//        return timezoneDatail;
        return timezoneDetail;
    }



    //나의 루틴 전체 보기

    @Override
    public List<Routine> myRoutine(Long id) {
        String sql1 = "select * from Routine where id = ?";
        List<Routine> result = jdbcTemplate.query(sql1, RoutineRowMapper(), id);
        return result;
    }

    //나의 루틴 상세보기
    @Override
    public Routine myRoutineDetail(Long id, Long post_no){
        //id와 post_no에 맞는 루틴 뽑기
        String sql1 = "select * from Routine where id = ? and post_no = ?";
        List<Routine> routineResult = jdbcTemplate.query(sql1, RoutineRowMapper(), id, post_no);

        //post_no에 맞는 투두 뽑기
        String sql2 = "select * from ToDo where post_no = ?";
        List<ToDo> todoResult = jdbcTemplate.query(sql2, ToDoRowMapper(), post_no);

        //뽑은 루틴에 투두 설정
        routineResult.get(0).setTodo_list(todoResult);

        return routineResult.get(0);

    }



    //루틴 삭제
    @Override
    public String deleteRoutine(int post_no) {
        String sql1 = "delete from ToDo where post_no = ?";
        String sql2 = "delete from Routine where post_no = ?";
        jdbcTemplate.update(sql1, post_no);
        jdbcTemplate.update(sql2, post_no);

        return "루틴이 삭제되었습니다";
    }


    //컨트롤러에서 post_no의 최댓값 찾기 위한 메소드
    public Long controller_getPostNo(){
        try {
            String sql = "SELECT max(post_no) FROM Routine;";
            Long count = jdbcTemplate.queryForObject(sql, Long.class);
            return count; }
        catch (Exception e) { Long count = 1L ; return count;}
    }

    //routine mapper
    private RowMapper<Routine> RoutineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setPost_no((rs.getLong("post_no")));
            routine.setId((rs.getLong("id")));
            routine.setTitle((rs.getString("title")));
            routine.setCreate_date((rs.getString("create_date")));
            routine.setStartTime((rs.getString("startTime")));
            routine.setEndTime((rs.getString("endTime")));
            routine.setHeartCount((rs.getInt("heartCount")));
            routine.setScrapCount((rs.getInt("scrapCount")));
            return routine;
        };
    }

    //user mapper
    private RowMapper<User> UserRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId((rs.getLong("id")));
            user.setUsername((rs.getString("username")));
            user.setNickname((rs.getString("nickname")));


            return user;
        };
    }
    //Heart mapper
    private RowMapper<Heart> HeartRowMapper() {
        return (rs, rowNum) -> {
            Heart heart = new Heart();
            heart.setH_number((rs.getLong("h_number")));
            heart.setPost_no((rs.getLong("post_no")));
            heart.setId((rs.getLong("id")));

            return heart;
        };
    }
    //toDo mapper
    private RowMapper<ToDo> ToDoRowMapper() {
        return (rs, rowNum) -> {
            ToDo todo = new ToDo();
            todo.setPost_no((rs.getLong("post_no")));
            todo.setContent((rs.getString("content")));

            return todo;
        };
    }




}
