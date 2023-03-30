package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@JdbcTest
@ExtendWith(SpringExtension.class)
class GigCertDAOImplTest {

    private JdbcTemplate jdbcTemplate;
    private GiftCertDAO giftCertDAO;
    private TagDAO tagDAO;

    @BeforeEach
    void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .addDefaultScripts()
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        tagDAO = new TagDaoImpl(jdbcTemplate);
        giftCertDAO = new GigCertDAOImpl(jdbcTemplate,tagDAO);
    }

    @Test
    void createGiftCert() throws ParseException {
        GiftCertificate giftCertificate=new GiftCertificate();

        String daoTestName = "DAO test name1";

        giftCertificate.setName(daoTestName);
        giftCertificate.setDescription("DAO test description");
        giftCertificate.setPrice(new BigDecimal("11.2"));
        giftCertificate.setDuration(200);
        giftCertificate.setCreateDate( parseISO8601("2020-10-01T16:40:11")  );
        giftCertificate.setLastUpdateDate(parseISO8601("2020-10-01T16:40:11"));

        Set<Tag> tags=getTags("DAO test tag",3); // new tags
        tags.add(tagDAO.getTagById(1));  // existing tag from database

        giftCertificate.setTags(tags);
        giftCertDAO.createGiftCert(giftCertificate);

        GiftCertificate giftCertificate2=jdbcTemplate.queryForObject("select * from gift_certificate where name =?"
                ,new CertRowMapper()
                ,daoTestName
        );
        assertNotNull(giftCertificate2);
        assertEqualsCerts(giftCertificate, giftCertificate2);
        // get tag list from database
        List<Tag> tagList=jdbcTemplate.query(
                """
                        select * from 
                        cert_tag inner join tag on cert_tag.tag_id=tag.id 
                        where cert_tag.cert_id=?
                        """,new TagRowMapper(),giftCertificate2.getId()
        );
        assertEquals(tags.size(),tagList.size());

        // check that recieved tag list have same tag names as input tag set
        Set<String> namesSet = tags.stream().map(Tag::getName).collect(Collectors.toSet());
        assertTrue( tagList.stream().allMatch(tag -> namesSet.contains(tag.getName())));
    }

    private static void assertEqualsCerts(GiftCertificate giftCertificate, GiftCertificate giftCertificate2) {
        assertEquals(giftCertificate.getName(), giftCertificate2.getName());
        assertEquals(giftCertificate.getDescription(), giftCertificate2.getDescription());
        assertTrue(giftCertificate.getPrice().compareTo(giftCertificate2.getPrice())==0);
        assertEquals(giftCertificate.getDuration(), giftCertificate2.getDuration());
        assertEquals(giftCertificate.getCreateDate(), giftCertificate2.getCreateDate());
        assertEquals(giftCertificate.getLastUpdateDate(), giftCertificate2.getLastUpdateDate());
    }

    @Test
    void deleteGiftCert() {
        giftCertDAO.deleteGiftCert(1);
        assertThrows(EmptyResultDataAccessException.class, () ->
            jdbcTemplate.queryForObject("SELECT * FROM gift_certificate where id=?", new CertRowMapper(),1)
        );
    }

    @Test
    void updateGiftCert() throws ParseException {

        Map<String, Object> paramToUpdate=new HashMap<>();
        paramToUpdate.put("name","New name");
        paramToUpdate.put("description","New description");
        paramToUpdate.put("create_date", new Timestamp(
                parseISO8601("2020-10-10T10:10:10").getTime())
        );

        giftCertDAO.updateGiftCert(1, paramToUpdate);

        GiftCertificate giftCertificate=giftCertDAO.getGiftCert(1);

        assertEquals("New name",giftCertificate.getName() );
        assertEquals("New description",giftCertificate.getDescription() );
        assertEquals( parseISO8601("2020-10-10T10:10:10"),giftCertificate.getCreateDate());
    }

    @Test
    void getGiftCert() {
        GiftCertificate giftCertificate=
                giftCertDAO.getGiftCert(1);
        assertNotNull(giftCertificate);
        assertEquals("name1",giftCertificate.getName());
    }

    @Test
    void getAllByParam() {
    }

    Set<Tag> getTags(String name,int count){
        Set<Tag> res=new HashSet<>();
        for(int i=0;i<count;i++){
            Tag t1=new Tag();
            t1.setName(name+i);
            res.add(t1);
        }
        return res;
    }

    public static Date parseISO8601(String iso8601string) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.parse(iso8601string);
    }
}