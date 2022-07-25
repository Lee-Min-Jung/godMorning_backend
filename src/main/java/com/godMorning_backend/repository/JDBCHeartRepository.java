package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Heart;
import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.ToDo;
import com.godMorning_backend.domain.user.User;
import com.godMorning_backend.dto.HeartCount;
import com.godMorning_backend.dto.HeartRank;
import com.mysql.cj.result.Row;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCHeartRepository {

    private JdbcTemplate jdbcTemplate;
    private JDBCHeartRepository jdbcHeartRepository;

    public JDBCHeartRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String deleteHeart(Long id, Long post_no) {
        String sql1 = "delete from Heart where id = ? and post_no = ?";
        String sql2 = "update Heart set h_number = h_number - 1 where post_no = ?";
        jdbcTemplate.update(sql1, id, post_no);

        jdbcTemplate.update(sql2, post_no);
        return "좋아요 취소";
    }

    public void insertHeart(Heart heart) {

        //heart 테이블에 heart insert하고 h_number 설정
        String sql1 = "INSERT INTO Heart(h_number, id, post_no) VALUES (0,?,?)";
        String sql2 = "update Heart set h_number = h_number + 1 where post_no = ?";


        //String sql2= "update Heart set h_number = h_number + 1 where post_no = ?";
        Object[] Params1 = {heart.getId(), heart.getPost_no()};
        Object[] Params2 = {heart.getPost_no()};


        jdbcTemplate.update(sql1, Params1);
        jdbcTemplate.update(sql2, Params2);


        //Heart에서 최대 h_number찾아서 Routine 테이블 heartCount에 넣어주기
       //최대 h_number은 리스트 중 맨 처음 결과라는 것을 활용
        String sql3 = "select * from Heart where post_no = ? ";
        List<Heart> heartList = jdbcTemplate.query(sql3, HeartRowMapper(), heart.getPost_no());

        Object[] Params3 = {heart.getPost_no()};
        String sql4 = "update Routine set heartCount = heartCount + 1 where post_no = ?";
        jdbcTemplate.update(sql4, Params3);

    }


    public List<Routine> heartRank() {
        String sql1 = "select post_no, count(post_no) from Heart group by post_no order by count(post_no) desc;";
        List<HeartRank> HeartResult = jdbcTemplate.query(sql1, HeartRankRowMapper());
        //위에 있는 sql을 통해 뽑은 post_no을 순서대로 리스트에 담기
        List<Long> postRank = new ArrayList<Long>();
        for(int i = 0; i< HeartResult.size(); i++){
            postRank.add(HeartResult.get(i).getPost_no());
        }
        //위에서 뽑은 post_no을 하나씩 sql2에 넘겨서 결과 뽑아내기
        List<Routine> result = new ArrayList<Routine>();
        String sql2 = "select * from Routine where post_no = ?";
        for(int i = 0; i<postRank.size(); i++){
            List<Routine> result2 = jdbcTemplate.query(sql2, RoutineRowMapper(), postRank.get(i));
            result.add(result2.get(0));
        }
        return result;

    }
    //heart rank 상세보기
    public Routine heartRankDetail(Long post_no){
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
    //hearCount mapper
    private RowMapper<HeartCount> heartCountRowMapper(){
        return (rs, rowNum) -> {
            HeartCount heartCount = new HeartCount();
            heartCount.setHeartCount((rs.getInt("heartCount")));
            return heartCount;
        };
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
    //toDo mapper
    private RowMapper<ToDo> ToDoRowMapper() {
        return (rs, rowNum) -> {
            ToDo todo = new ToDo();
            todo.setPost_no((rs.getLong("post_no")));
            todo.setContent((rs.getString("content")));

            return todo;
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
    private RowMapper<HeartRank> HeartRankRowMapper() {
        return (rs, rowNum) -> {
            HeartRank heartRank = new HeartRank();
            heartRank.setPost_no((rs.getLong("post_no")));
            return heartRank;
        };
    }
    private RowMapper<Heart> HeartRowMapper() {
        return (rs, rowNum) -> {
            Heart heart = new Heart();
            heart.setH_number((rs.getLong("h_number")));
            heart.setPost_no((rs.getLong("post_no")));
            heart.setId((rs.getLong("id")));

            return heart;
        };
    }

    public Optional<Heart> findByUserIdAndPost_no(Long id, Long post_no) {
        String sql = "select * from Heart where id = ? and post_no = ?";
        List<Heart> result = jdbcTemplate.query(sql, HeartRowMapper(), id, post_no);
        return result.stream().findAny();
    }
}



