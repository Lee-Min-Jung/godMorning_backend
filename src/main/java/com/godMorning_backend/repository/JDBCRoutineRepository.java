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
        String sql = "INSERT INTO Routine(id, title, timezone, create_time) VALUES (?,?,?,?)";
        Object[] Params = {routine.getId(), routine.getTitle(), routine.getTimezone(), routine.getCreate_time()};
        jdbcTemplate.update(sql,Params);
    }



    private RowMapper<Routine> RoutineRowMapper() {
        return (rs, rowNum) -> {
            Routine routine = new Routine();
            routine.setId((rs.getLong("id")));
            routine.setTitle((rs.getString("title")));
            routine.setTimezone((rs.getString("timezone")));
            routine.setCreate_time((rs.getString("create_time")));
            return routine;
        };
    }
}
