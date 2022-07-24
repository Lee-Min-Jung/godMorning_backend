package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;
import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class JDBCScrapRepository implements ScrapRepository{

    private final JdbcTemplate jdbcTemplate;
    public JDBCScrapRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveScrap(Scrap scrap) {
        String sql = "INSERT INTO Scrap(id, post_no) VALUES (?,?)";
        Object[] Params = {scrap.getId(), scrap.getPost_no()};
        jdbcTemplate.update(sql, Params);
    }

    //id별 스크랩 전체 조회
    public List<Routine> findById(Long id) {

        String sql = "select * from Routine where post_no in (select post_no from Scrap where Scrap.id = ?)";
        List<Routine> result = jdbcTemplate.query(sql, routineRowMapper(), id);
        return result;
    }

    public Optional<Scrap> findByUserIdAndPost_no(Long post_no,Long id){
        //String sql = "select * from Scrap where id = ? and post_no = ?";
        String sql = "select * from Scrap where post_no = ? and id = ?";

        List<Scrap> result = jdbcTemplate.query(sql, scrapRowMapper(),  post_no,id);
        return result.stream().findAny();
    }

    //아이디별 스크랩 전체 조회 후 상세 조회
    @Override
    public Routine findByIdAndPost_no(Long id, Long post_no) {
        //루틴 검색
        String sql = "select * from Routine where id = ? and post_no = ?";
        List<Routine> routineResult = jdbcTemplate.query(sql, routineRowMapper(), id, post_no );

        //id로 이름 뽑기
        String sql01 = "select * from user where id = ?";
        List<User> nameResult1 = jdbcTemplate.query(sql01, UserRowMapper(), id );
        String nameResult2 = nameResult1.get(0).getUsername();

        //post_no에 맞는 투두뽑기
        String sql2 = "select * from ToDo where post_no = ?";
        List<ToDo> todoResult = jdbcTemplate.query(sql2, ToDoRowMapper(), post_no);

        //검색된 루틴에 이름 설정
        routineResult.get(0).setNickname(nameResult2);

        //검색된 루틴에 투두 설정
        routineResult.get(0).setTodo_list(todoResult);

        return routineResult.get(0);
    }

    //스크랩 삭제
    public String deleteScrap(Long id, Long post_no){
        String sql = "delete from Scrap where id = ? and post_no = ?";
        jdbcTemplate.update(sql,id,post_no);
        return "스크랩 취소";
    }
    //mapper
    private RowMapper<Scrap> scrapRowMapper() {
        return (rs, rowNum) -> {
            Scrap scrap = new Scrap();
            scrap.setPost_no(rs.getLong("post_no"));
            scrap.setId(rs.getLong("id"));
            return scrap;
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
    //routine mapper
    private RowMapper<Routine> routineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setPost_no(rs.getLong("post_no"));
            routine.setTitle(rs.getString("title"));
            routine.setId(rs.getLong("id"));
            routine.setCreate_date(rs.getString("create_date"));
            routine.setStartTime((rs.getString("startTime")));
            routine.setEndTime((rs.getString("endTime")));

            return routine;
        };
    }

    //투두 mapper
    private RowMapper<ToDo> ToDoRowMapper() {
        return (rs, rowNum) -> {
            ToDo todo = new ToDo();
            todo.setPost_no((rs.getLong("post_no")));
            todo.setContent((rs.getString("content")));

            return todo;
        };
    }
}
