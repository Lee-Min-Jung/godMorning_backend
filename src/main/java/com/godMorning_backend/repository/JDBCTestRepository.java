package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class JDBCTestRepository implements TestRepository{
    private final JdbcTemplate jdbcTemplate;


    public JDBCTestRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }
    @Override
    public List<Test> findAll(String id) {
        String sql = "select * from test where id = ? ";
        List<Test> result = jdbcTemplate.query(sql, testRowMapper(), id);
        return result;
    }

    private RowMapper<Test> testRowMapper() {
        return (rs, rowNum) -> {
            Test test = new Test();
            test.setId(rs.getInt("id"));
            test.setName(rs.getString("name"));

            return test;
        };
    }
}
