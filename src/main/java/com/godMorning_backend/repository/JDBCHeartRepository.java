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
        String sql2 = "update Heart set h_number = h_number - 1 where post_no = ?";
        jdbcTemplate.update(sql1, id, post_no);

        jdbcTemplate.update(sql2, post_no);
        return "좋아요 취소";
    }

    public void insertHeart(Heart heart) {

        String sql1 = "INSERT INTO Heart(h_number, id, post_no) VALUES (0,?,?)";
        String sql2 = "update Heart set h_number = h_number + 1 where post_no = ?";
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

    public List<Heart> heartRank(Long post_no) {

        String sql1 = "select count (post_no) from Heart where post_no=?";
        return jdbcTemplate.query(sql1, HeartRowMapper(), post_no);

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

    /*
    public void heartIncrement(Heart heart) {
        String sql= "update Heart set h_number = h_number + 1 where post_no = ? and id=?";
        Object[] Params = {heart.getH_number(), heart.getPost_no(), heart.getId()};
        jdbcTemplate.update(sql,Params);

    }

        String sql1 = "select distinct post_no, rank() over (order by h_number) as ranking from Heart";
        //String sql2 = "select MAX(h_number) where post_no=? from Heart";
        List<Heart> rank = jdbcTemplate.query(sql1, HeartRowMapper(), post_no);
        //List<Heart> max = jdbcTemplate.query(sql2, HeartRowMapper(), post_no);

        return rank;
    */


