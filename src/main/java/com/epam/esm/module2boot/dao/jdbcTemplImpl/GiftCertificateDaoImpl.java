package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
@Profile("jdbcTemplate")
public class GiftCertificateDaoImpl implements GiftCertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDAO tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    private static SqlParameterSource createParametersMap(GiftCertificate giftCertificate) {
        return new MapSqlParameterSource()
                .addValue("name", giftCertificate.getName())
                .addValue("description", giftCertificate.getDescription())
                .addValue("duration", giftCertificate.getDuration())
                .addValue("price", giftCertificate.getPrice())
                .addValue("create_date",
                        new Timestamp(giftCertificate.getCreateDate().getTime()))
                .addValue("last_update_date",
                        new Timestamp(giftCertificate.getLastUpdateDate().getTime())
                );
    }

    private static String getWhereStr(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return "";
        Set<String> likeFields = Set.of("gift_certificate.name", "description");

        return "WHERE " + params.keySet().stream()
                .map(o -> String.format(
                        likeFields.contains(o) ?
                                "%1$s like :%1$s" : "%1$s = :%1$s"
                        , o
                ))
                .collect(Collectors.joining(" AND "));
    }

    private static String getSortingSubStr(List<String> sortingFieldsList) {
        String sortString = "";
        if (sortingFieldsList != null && sortingFieldsList.size() > 0) {
            sortString = "ORDER BY " + String.join(", ", sortingFieldsList);
        }
        return sortString;
    }

    @Override
    @Transactional
    public GiftCertificate createGiftCert(GiftCertificate giftCertificate) {


        SqlParameterSource parameters = createParametersMap(giftCertificate);

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                """
                        INSERT INTO gift_certificate (name,description, price, duration, create_date, last_update_date)
                        VALUES (:name,:description,:price,:duration,:create_date,:last_update_date)
                        """, parameters, holder);
        int id = Objects.requireNonNull(holder.getKey()).intValue();

        addLinks2Tags(giftCertificate.getTags(), id);

        return giftCertificate.toBuilder()
                .id(id)
                .build();
    }

    private void addLinks2Tags(Set<Tag> tagSet, final int id) {
        if (tagSet != null && tagSet.size() > 0) {
            tagSet.forEach(
                    tag -> jdbcTemplate.update(
                            "INSERT INTO cert_tag (cert_id,tag_id) VALUES (?,?)"
                            , id
                            , tag.getId())
            );
        }
    }

    @Override
    public boolean deleteGiftCert(int id) {
        int result = jdbcTemplate.update("DELETE FROM gift_certificate where id=?", id);
        jdbcTemplate.update("DELETE FROM cert_tag WHERE cert_id=?", id);
        return result == 1;
    }

    @Override
    public boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate) {
        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty())
            throw new IllegalArgumentException("Fields map null or empty!");

        String fields = fieldsToUpdate
                .keySet()
                .stream()
                .map(s -> String.format("%1$s=:%1$s", s))
                .collect(Collectors.joining(", "));

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValues(fieldsToUpdate)
                .addValue("id", id);

        return namedParameterJdbcTemplate.update
                ("UPDATE gift_certificate SET " + fields + " WHERE id=:id", sqlParameterSource)
                == 1;
    }

    @Override
    public GiftCertificate getGiftCert(int id) throws NotFoundException {
        try {

            GiftCertificate giftCertificate =
                    jdbcTemplate.queryForObject("select * from gift_certificate where id=?",
                            new CertificateRowMapper(), id);
            if (giftCertificate != null)
                giftCertificate.setTags(tagDAO.getTagsForCertID(giftCertificate.getId()));
            return giftCertificate;

        } catch (EmptyResultDataAccessException inner) {
            throw new NotFoundException("Gift certificate with id " + id + " not found", inner);
        }


    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, List<String> sortingMap) {

        String sortSubStr = getSortingSubStr(sortingMap);
        String whereStr = getWhereStr(params);

        SqlParameterSource parameterSource = new MapSqlParameterSource().addValues(params);

        List<GiftCertificate> giftCertificates = namedParameterJdbcTemplate.query(
                """
                          SELECT DISTINCT gift_certificate.* FROM gift_certificate
                            LEFT OUTER JOIN  cert_tag ON cert_tag.cert_id=gift_certificate.id
                            LEFT OUTER JOIN tag ON cert_tag.tag_id=tag.id
                        """ + whereStr + " " + sortSubStr, parameterSource, new CertificateRowMapper());

        giftCertificates.forEach(giftCertificate ->
                giftCertificate.setTags(tagDAO.getTagsForCertID(giftCertificate.getId()))
        );

        System.out.println("queryStr:" + whereStr);
        System.out.println("orderStr:" + sortSubStr);

        return giftCertificates;
    }
}
