package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JDBCRoutineRepository implements RoutineRepository{
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


        String sql2 = "select * from ToDo where post_no = 2";
        List<ToDo> result2 = jdbcTemplate.query(sql2, ToDoRowMapper());
        result.get(0).setTodo_list(result2);
        return result.get(0);
    }

    //루틴 저장
    @Override
    public void saveRoutine(Routine routine) {
        String sql = "INSERT INTO Routine(id, title, create_date, startTime, endTime) VALUES (?,?,?,?,?)";
        Object[] Params = {routine.getId(), routine.getTitle(), routine.getCreate_date(), routine.getStartTime(), routine.getEndTime()};
        jdbcTemplate.update(sql,Params);

        int len = routine.getTodo_list().size();
        for(int i=0;i<len;i++){
            String sql2 = "INSERT INTO ToDo(post_no, content, check_do) VALUES (?,?,?)";
            Object[] Params2 = {routine.getTodo_list().get(i).getPost_no(), routine.getTodo_list().get(i).getContent(), routine.getTodo_list().get(i).getCheck_do() };
            jdbcTemplate.update(sql2, Params2);
        }



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

    private RowMapper<Routine> RoutineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setId((rs.getLong("id")));
            routine.setTitle((rs.getString("title")));
            routine.setCreate_date((rs.getString("create_date")));
            routine.setStartTime((rs.getString("startTime")));
            routine.setEndTime((rs.getString("endTime")));
            return routine;
        };
    }

    private RowMapper<ToDo> ToDoRowMapper() {
        return (rs, rowNum) -> {
            ToDo todo = new ToDo();
            todo.setPost_no((rs.getInt("post_no")));
            todo.setContent((rs.getString("content")));
            todo.setCheck_do((rs.getInt("check_do")));
            return todo;
        };
    }


}
