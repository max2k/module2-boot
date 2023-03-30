package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class TagDaoImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag createTag(String name) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name1", name, Types.VARCHAR);

        namedParameterJdbcTemplate.update("INSERT INTO tag (name) VALUES (:name1);"
                ,parameters
                ,holder);


        Tag res=new Tag();
        res.setName(name);
        res.setId(Objects.requireNonNull(holder.getKey()).intValue());

        return res;
    }

    @Override
    public Tag getTagById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tag WHERE id=?",new TagRowMapper(),id);
    }

    @Override
    public void deleteTag(int id) {
        jdbcTemplate.update("DELETE from tag WHERE id=?",id);
    }

    @Override
    public Set<Tag> getTagsForCertID(int id) {
        try {
            List<Tag> tagList=jdbcTemplate.query(
                    "SELECT tag.* FROM tag JOIN cert_tag on cert_tag.tag_id=tag.id where cert_tag.cert_id=?"
                    ,new TagRowMapper()
                    ,id);
            return new HashSet<>(tagList);
        }catch (EmptyResultDataAccessException noElements){
            return new HashSet<>();
        }

    }
}
