package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Scrap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class JDBCScrapRepository {

    private final JdbcTemplate jdbcTemplate;
    public JDBCScrapRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveScrap(Scrap scrap) {
        String sql = "INSERT INTO Scrap(id, post_no) VALUES (?,?)";
        Object[] Params = {scrap.getId(), scrap.getPost_no()};
        jdbcTemplate.update(sql, Params);
    }

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

    private RowMapper<Routine> routineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setPost_no(rs.getLong("post_no"));
            routine.setTitle(rs.getString("title"));
            routine.setId(rs.getLong("id"));
            routine.setCreate_date(rs.getString("create_date"));

            return routine;
        };
    }
}
