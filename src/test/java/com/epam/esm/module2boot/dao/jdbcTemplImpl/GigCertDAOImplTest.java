package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class GigCertDAOImplTest {


    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Autowired
    private TagDAO tagDAO;


    public static Arguments getUnsortedArgs(Map<String, Object> params, List<Integer> expected) {
        return Arguments.of(params, new LinkedList<>(), expected, false);
    }

    public static Stream<Arguments> queryMaps() {
        List<Arguments> args = new LinkedList<>();
        //

        args.add(
                Arguments.of(
                        Map.of("gift_certificate.name", "%name%",
                                "description", "description%"),
                        List.of("create_date desc", "gift_certificate.name asc"),
                        List.of(1, 3, 2, 4, 5, 6),
                        true
                ));

        args.add(
                Arguments.of(
                        Map.of("gift_certificate.name", "%name%",
                                "description", "description%",
                                "tag.name", "tag1"),
                        List.of("create_date desc", "gift_certificate.name asc"),
                        List.of(1, 2),
                        true
                ));

        //empty arguments no ordering
        args.add(getUnsortedArgs(new HashMap<>(), List.of(1, 2, 3, 4, 5, 6)));
        // one by full name

        args.add(getUnsortedArgs(
                Map.of("gift_certificate.name", "name1"),
                List.of(1)
        ));

        args.add(getUnsortedArgs(
                Map.of("gift_certificate.name", "%name%"),
                List.of(1, 2, 3, 4, 5, 6)
        ));

        args.add(getUnsortedArgs(
                Map.of("gift_certificate.name", "%name%",
                        "description", "description2"),
                List.of(2, 4, 5, 6)
        ));

        args.add(getUnsortedArgs(
                Map.of("gift_certificate.name", "%name%",
                        "description", "description%",
                        "tag.name", "tag1"),
                List.of(1, 2)
        ));

        args.add(
                Arguments.of(
                        Map.of("gift_certificate.name", "%name%",
                                "description", "description%",
                                "tag.name", "tag1"),
                        List.of("name desc"),
                        List.of(2, 1),
                        true
                ));

        args.add(
                Arguments.of(
                        Map.of("gift_certificate.name", "%name%",
                                "description", "description%",
                                "tag.name", "tag1"),
                        List.of("create_date"),
                        List.of(2, 1),
                        true
                ));


        return args.stream();
    }
//
//    @BeforeEach
//    void setUp() {
//        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
//                .generateUniqueName(true)
//                .setType(H2)
//                .setScriptEncoding("UTF-8")
//                .addDefaultScripts()
//                .build();
//        jdbcTemplate = new JdbcTemplate(db);
//        tagDAO = new TagDaoImpl(jdbcTemplate);
//        giftCertificateDAO = new GiftCertificateDaoImpl(jdbcTemplate,tagDAO);
//    }

    private static void assertEqualsCerts(GiftCertificate giftCertificate, GiftCertificate giftCertificate2) {
        assertEquals(giftCertificate.getName(), giftCertificate2.getName());
        assertEquals(giftCertificate.getDescription(), giftCertificate2.getDescription());
        assertEquals(0, giftCertificate.getPrice().compareTo(giftCertificate2.getPrice()));
        assertEquals(giftCertificate.getDuration(), giftCertificate2.getDuration());
        assertEquals(giftCertificate.getCreateDate(), giftCertificate2.getCreateDate());
        assertEquals(giftCertificate.getLastUpdateDate(), giftCertificate2.getLastUpdateDate());
    }

    @Test
    void createGiftCert() throws ParseException {
        GiftCertificate giftCertificate = GiftCertificate.builder().build();

        String daoTestName = "DAO test name1";

        giftCertificate.setName(daoTestName);
        giftCertificate.setDescription("DAO test description");
        giftCertificate.setPrice(new BigDecimal("11.2"));
        giftCertificate.setDuration(200);
        giftCertificate.setCreateDate(Util.parseISO8601("2020-10-01T16:40:11"));
        giftCertificate.setLastUpdateDate(Util.parseISO8601("2020-10-01T16:40:11"));

        Set<Tag> tags = new HashSet<>();
        //getTags("DAO test tag",3); // new tags
        tags.add(tagDAO.getTagById(1));  // existing tag from database

        giftCertificate.setTags(tags);

        GiftCertificate certificateReturnedFromCreate = giftCertificateDAO.createGiftCert(giftCertificate);

        GiftCertificate certificateFromDatabase = giftCertificateDAO.getGiftCert(
                certificateReturnedFromCreate.getId()
        );

        assertNotNull(certificateFromDatabase);
        assertEqualsCerts(giftCertificate, certificateFromDatabase);
        // get tag list from database

        Set<Tag> tagList = certificateFromDatabase.getTags();

        assertEquals(tags.size(), tagList.size());

        // check that recieved tag list have same tag names as input tag set
        Set<String> namesSet = tags.stream().map(Tag::getName).collect(Collectors.toSet());
        assertTrue(tagList.stream().allMatch(tag -> namesSet.contains(tag.getName())));
    }

    @Test
    void deleteGiftCert() {
        giftCertificateDAO.deleteGiftCert(1);

        assertThrows(EmptyResultDataAccessException.class, () ->
                giftCertificateDAO.getGiftCert(1));

    }

    @Test
    void updateGiftCert() throws ParseException {

        Map<String, Object> paramToUpdate = new HashMap<>();
        paramToUpdate.put("name", "New name");
        paramToUpdate.put("description", "New description");
        paramToUpdate.put("create_date", new Timestamp(
                Util.parseISO8601("2020-10-10T10:10:10").getTime())
        );

        giftCertificateDAO.updateGiftCert(1, paramToUpdate);

        GiftCertificate giftCertificate = giftCertificateDAO.getGiftCert(1);

        assertEquals("New name", giftCertificate.getName());
        assertEquals("New description", giftCertificate.getDescription());
        assertEquals(Util.parseISO8601("2020-10-10T10:10:10"), giftCertificate.getCreateDate());
    }

    @Test
    void getGiftCert() {
        GiftCertificate giftCertificate =
                giftCertificateDAO.getGiftCert(1);
        assertNotNull(giftCertificate);
        assertEquals("name1", giftCertificate.getName());
    }

    @ParameterizedTest
    @MethodSource("queryMaps")
    void getAllByParam(Map<String, Object> params,
                       List<String> sorting,
                       List<Integer> expected,
                       boolean ordered) {

        List<GiftCertificate> result = giftCertificateDAO.getAllByParam(params, sorting);

        assertEquals(expected.size(), result.size());
        if (ordered) {
            for (int i = 0; i < expected.size(); i++)
                assertEquals(expected.get(i), result.get(i).getId());
        } else {
            assertTrue(
                    result.stream()
                            .allMatch(giftCertificate -> expected.contains(giftCertificate.getId()))
            );
        }
    }


}