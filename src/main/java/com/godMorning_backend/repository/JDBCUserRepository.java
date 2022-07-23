package com.godMorning_backend.repository;

import com.godMorning_backend.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JDBCUserRepository {
    private final JdbcTemplate jdbcTemplate;
    public JDBCUserRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setPassword(String pw, Long id){
        String sql = "update user set password = ? where id = ?";
        jdbcTemplate.update(sql, pw, id);

    }

    //user mapper
//    private RowMapper<User> UserRowMapper() {
//        return (rs, rowNum) -> {
//            User user = new User();
//            user.setId((rs.getLong("id")));
//            user.setUsername((rs.getString("username")));
//            user.setEmail((rs.getString("email")));
//
//
//            return user;
//        };
//    }
}
