package com.godMorning_backend.repository;

import com.godMorning_backend.domain.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JDBCToDoRepository implements ToDoRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCToDoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public void saveToDo(ToDo todo) {
        String sql = "INSERT INTO ToDo(post_no, content, check_do) VALUES(?,?,?)";
        Object[] Params = {todo.getPost_no(), todo.getContent()};
        jdbcTemplate.update(sql,Params);
    }

    private RowMapper<ToDo> ToDoRowMapper() {
        return (rs, rowNum) -> {
            ToDo todo = new ToDo();
            todo.setPost_no((rs.getLong("post_no")));
            todo.setContent((rs.getString("content")));

            return todo;
        };
    }
}
