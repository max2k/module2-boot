package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
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
    @Transactional
    public void createGiftCert(GiftCertificate giftCertificate) {
        Set<Tag> tagSet = new HashSet<>();
        if (giftCertificate.getTags()!=null) {
            for (Tag tag1 : giftCertificate.getTags()) {
                tagSet.add(tag1.isNoId() ?
                        tagDAO.createTag(tag1.getName())
                        : tag1);
            }
        }

        SqlParameterSource parameters = createParametesMap(giftCertificate);

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);

        namedParameterJdbcTemplate.update(
                """
                    INSERT INTO gift_certificate (name,description, price, duration, create_date, last_update_date)
                    VALUES (:name,:description,:price,:duration,:create_date,:last_update_date)
                    """,parameters,holder);
        int id =  Objects.requireNonNull(holder.getKey()).intValue();
        addLinks2Tags(tagSet,id);

    }

    private void addLinks2Tags(Set<Tag> tagSet,final int id) {
        tagSet.stream().forEach(
                tag -> jdbcTemplate.update(
                        "INSERT INTO cert_tag (cert_id,tag_id) VALUES (?,?)"
                        ,id
                        ,tag.getId())
        );
    }

    private static SqlParameterSource createParametesMap(GiftCertificate giftCertificate) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("name", giftCertificate.getName())
                    .addValue("description", giftCertificate.getDescription())
                    .addValue("duration",giftCertificate.getDuration())
                    .addValue("price", giftCertificate.getPrice() )
                    .addValue("create_date",
                            new Timestamp( giftCertificate.getCreateDate().getTime() ))
                    .addValue("last_update_date",
                            new Timestamp( giftCertificate.getLastUpdateDate().getTime() )
                    );
        return parameters;
    }

    @Override
    public void deleteGiftCert(int id) {
        jdbcTemplate.update("DELETE FROM gift_certificate where id=?",id);
        jdbcTemplate.update("DELETE FROM cert_tag WHERE cert_id=?",id);
    }

    @Override
    public void updateGiftCert(int id, Map<String,Object> fieldsToUpdate) {
        String fields=fieldsToUpdate
                .keySet()
                .stream()
                .map(s -> String.format("%1$s=:%1$s",s))
                .collect(Collectors.joining(", "));

        SqlParameterSource sqlParameterSource=new MapSqlParameterSource()
                .addValues(fieldsToUpdate)
                .addValue("id",id);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);

        namedParameterJdbcTemplate.update("UPDATE gift_certificate SET " + fields +" WHERE id=:id",sqlParameterSource);
    }

    @Override
    public GiftCertificate getGiftCert(int id) {
        return jdbcTemplate.queryForObject("select * from gift_certificate where id=?",new CertRowMapper(),id);

    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, Map<String, String> sortingMap) {
        String sortSubStr=getSortingSubStr(sortingMap);

        String whereStr=getWhereStr(params);


        return null;
    }

    private static String getWhereStr(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(stringObjectEntry ->
                        String.format(
                                stringObjectEntry.getKey() == "tag.name" ?
                                "%s = :%s" :
                                "%s like :%s"
                        ))
                .collect(Collectors.joining(" AND "));
    }

    private static String getSortingSubStr(Map<String, String> sortingMap) {
        String sortString="";
        if (sortingMap !=null && sortingMap.keySet().size()>0){
            sortString="ORDER BY "+ sortingMap
                    .entrySet()
                    .stream()
                    .map( stringStringEntry ->
                            String.format("%s %s"
                                    ,stringStringEntry.getKey()
                            ,stringStringEntry.getValue())
                    )
                    .collect(Collectors.joining(", 0"));
        }
        return sortString;
    }
}
