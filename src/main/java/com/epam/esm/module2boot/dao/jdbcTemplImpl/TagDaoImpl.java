package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class TagDaoImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag createTag(String name) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", name);

        jdbcTemplate.update("INSERT INTO tags (name) VALUES (:name)"
                ,parameters
                ,holder);

        Tag res=new Tag();
        res.setName(name);
        res.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return res;
    }

    @Override
    public Tag getTagById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tags WHERE id=?",new TagRowMapper(),id);
    }

    @Override
    public void deleteTag(int id) {
        jdbcTemplate.update("DELETE from tags WHERE id=?",id);
    }
}
