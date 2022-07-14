package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Heart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

        String sql2= "update Heart set h_number = h_number - 1 where post_no = ?";
        jdbcTemplate.update(sql1,id,post_no);

        jdbcTemplate.update(sql2,post_no);
        return "좋아요 취소";
    }

    public void insertHeart(Heart heart) {

        String sql1 = "INSERT INTO Heart(h_number, id, post_no) VALUES (0,?,?)";
        String sql2= "update Heart set h_number = h_number + 1 where post_no = ?";
        //String sql2= "update Heart set h_number = h_number + 1 where post_no = ?";
        Object[] Params1 = {heart.getId(), heart.getPost_no()};
        Object[] Params2 = {heart.getPost_no()};
        jdbcTemplate.update(sql1, Params1);
        jdbcTemplate.update(sql2, Params2);
        /*
        if ( heart.getH_number() > 0) {
            jdbcTemplate.update(sql2, Params1);
        }
        else {
            jdbcTemplate.update(sql1, Params1);
        }
        */

    }
/*
    public void heartIncrement(Heart heart) {
        String sql= "update Heart set h_number = h_number + 1 where post_no = ? and id=?";
        Object[] Params = {heart.getH_number(), heart.getPost_no(), heart.getId()};
        jdbcTemplate.update(sql,Params);
    }
    */

    private RowMapper<Heart> HeartRowMapper() {
        return (rs, rowNum) -> {
            Heart heart = new Heart();
            heart.setH_number((rs.getLong("h_number")));
            heart.setPost_no((rs.getLong("post_no")));
            heart.setId((rs.getLong("id")));

            return heart;
        };
    }
    public Optional<Heart> findByUserIdAndPost_no(Long id, Long post_no){
        String sql = "select * from Scrap where id = ? and post_no = ?";
        List<Heart> result = jdbcTemplate.query(sql, HeartRowMapper(), id, post_no);
        return result.stream().findAny();
    }

}