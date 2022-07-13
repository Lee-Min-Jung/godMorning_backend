package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Heart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JDBCHeartRepository {
    private JdbcTemplate jdbcTemplate;
    private JDBCHeartRepository jdbcHeartRepository;

    public JDBCHeartRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public String deleteHeart(Long id, Long post_no) {
        String sql = "delete from Heart where id = ? and post_no = ?";
        jdbcTemplate.update(sql,id,post_no);
        return "좋아요 취소";
    }

    public void insertHeart(Heart heart) {
        String sql = "INSERT INTO Heart VALUES (1,?,?)";
        Object[] Params = {heart.getId(), heart.getPost_no()};
        jdbcTemplate.update(sql, Params);
    }

    public void heartIncrement(Heart heart) {
        String sql= "update Heart set h_number = h_number + 1 where id = ? and post_no = ?";
        Object[] Params = {heart.getH_number()};
        jdbcTemplate.update(sql,Params);
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
    public Optional<Heart> findByUserIdAndPost_no(Long id, Long post_no){
        String sql = "select * from Scrap where id = ? and post_no = ?";
        List<Heart> result = jdbcTemplate.query(sql, HeartRowMapper(), id, post_no);
        return result.stream().findAny();
    }

}
