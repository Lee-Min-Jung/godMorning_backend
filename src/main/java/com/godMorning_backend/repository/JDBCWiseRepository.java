package com.godMorning_backend.repository;

import com.godMorning_backend.domain.Routine;
import com.godMorning_backend.domain.Wise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class JDBCWiseRepository implements WiseRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCWiseRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public String findByNo(int id) {
        String sql1 = "select * from Wise where wise_no = ?";
        List<Wise> result1 = jdbcTemplate.query(sql1, WiseRowMapper(), id);
        return result1.get(0).getWise_saying();
    }


    private RowMapper<Wise> WiseRowMapper() {
        return (rs, rowNum) -> {
            Wise wise = new Wise();
            wise.setWise_no((rs.getLong("wise_no")));
            wise.setWise_saying((rs.getString("wise_saying")));
            return wise;
        };
    }
}
