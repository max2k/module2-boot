package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GigCertDAOImpl implements GiftCertDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagDAO tagDAO;

    @Autowired
    public GigCertDAOImpl(JdbcTemplate jdbcTemplate, TagDAO tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
    }

    @Override
    public void createGiftCert(GiftCertificate giftCertificate) {
        Set<Tag> tagSet = new HashSet<>();
        for (Tag tag1 : giftCertificate.getTags()) {
            tagSet.add(tag1.isNoId() ?
                    tagDAO.createTag(tag1.getName())
                    : tag1);
        }

        SqlParameterSource parameters = createParametesMap(giftCertificate);

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                """
                    INSERT INTO gift_certificates (name,description, price, duration, create_date, last_update_date)
                    VALUES (:name,:description,:price,:duration,:create_date,:last_update_date)
                    """,parameters,holder);

        addLinks2Tags(tagSet, Objects.requireNonNull(holder.getKey()).intValue());
    }

    private void addLinks2Tags(Set<Tag> tagSet,final int id) {
        tagSet.stream().forEach(
                tag -> jdbcTemplate.update(
                        "INSERT INTO cert_tags (cert_id,tag_id) VALUES (?,?)"
                        ,id
                        ,tag.getId())
        );
    }

    private static SqlParameterSource createParametesMap(GiftCertificate giftCertificate) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue(":name", giftCertificate.getName())
                    .addValue(":description", giftCertificate.getDescription())
                    .addValue(":price", giftCertificate.getPrice())
                    .addValue(":create_date", giftCertificate.getCreateDate())
                    .addValue(":last_update_date", giftCertificate.getLastUpDateTime());
        return parameters;
    }

    @Override
    public void deleteGiftCert(int id) {
        jdbcTemplate.update("DELETE FROM gift_certificates where id=?",id);
        jdbcTemplate.update("DELETE FROM cert_tags WHERE cert_id=?",id);
    }

    @Override
    public void updateGiftCert(int id, Map<String,Objects> fieldsToUpdate) {
        String fields=fieldsToUpdate
                .keySet()
                .stream()
                .map(s -> String.format("%s=:%s",s))
                .collect(Collectors.joining(", "));

        SqlParameterSource sqlParameterSource=new MapSqlParameterSource()
                .addValues(fieldsToUpdate)
                .addValue("id",id);

        jdbcTemplate.update("UPDATE gift_certificates SET " + fields +" WHERE id=:id",sqlParameterSource);
    }

    @Override
    public void getGiftCert(int id) {
        jdbcTemplate.queryForObject("select * from gift_certificates where id=?",new CertRowMapper(),id);
    }
}
