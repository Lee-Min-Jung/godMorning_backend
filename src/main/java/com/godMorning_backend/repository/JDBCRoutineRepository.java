package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
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

    @Override
    public void saveRoutine(Routine routine) {
        String sql = "INSERT INTO Routine(user, title, timezone, toDo1, toDo2,toDo3,toDo4,toDo5,toDo6,toDo7,toDo8,toDo9,toDo10) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] Params = {routine.getUserId(), routine.getTitle(), routine.getTimezone(), routine.getToDo1(), routine.getToDo2(), routine.getToDo3(),routine.getToDo4(),routine.getToDo5(),routine.getToDo6(),routine.getToDo7(),routine.getToDo8(),routine.getToDo9(),routine.getToDo10()};
        jdbcTemplate.update(sql,Params);
    }

    @Override
    public List<Routine> findById(String userId) {
        return null;
    }

    private RowMapper<Routine> RoutineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setUserId((rs.getString("user")));
            routine.setTitle((rs.getString("title")));
            routine.setTimezone((rs.getString("timezone")));
            routine.setToDo1((rs.getString("toDo1")));
            routine.setToDo1((rs.getString("toDo2")));
            routine.setToDo1((rs.getString("toDo3")));
            routine.setToDo1((rs.getString("toDo4")));
            routine.setToDo1((rs.getString("toDo5")));
            routine.setToDo1((rs.getString("toDo6")));
            routine.setToDo1((rs.getString("toDo7")));
            routine.setToDo1((rs.getString("toDo8")));
            routine.setToDo1((rs.getString("toDo9")));
            routine.setToDo1((rs.getString("toDo10")));


            return routine;
        };
    }
}
