package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag=new Tag();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));

        return tag;
    }
}
