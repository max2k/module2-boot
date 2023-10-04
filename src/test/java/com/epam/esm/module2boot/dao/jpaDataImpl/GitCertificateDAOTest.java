package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class GitCertificateDAOTest {

    @Autowired
    private GiftCertificateDAO gitCertificateDAO;

    public static Stream<Arguments> parametersForGetGiftCert() {
        return Stream.of(
                Arguments.of(null, 10006),
                Arguments.of(Map.of("tags", List.of("tag1", "tag2")), 2),
                Arguments.of(Map.of("substr", "name"), 99)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForGetGiftCert")
    void getAllByParam(Map<String, Object> params, int expectedSize) {

        Page<GiftCertificate> gc = gitCertificateDAO.getAllByParam(params,
                PageRequest.of(0, 100));
        assertEquals(expectedSize, gc.getTotalElements());

    }
}