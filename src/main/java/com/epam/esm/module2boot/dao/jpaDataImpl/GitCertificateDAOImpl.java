package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver.FieldSetHelper;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Repository
@Profile("jpa-data")
public class GitCertificateDAOImpl implements GiftCertificateDAO {

    private final GiftCertificateRepository giftCertificateRepository;

    public GitCertificateDAOImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public GiftCertificate createGiftCert(GiftCertificate giftCertificate) {
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
    public boolean deleteGiftCert(int id) throws NotFoundException {

        giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gift certificate not found with id:" + id));
        giftCertificateRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gift certificate not found with id:" + id));

        setDBFieldsToGiftCertificateFields(fieldsToUpdate, giftCertificate);

        giftCertificateRepository.save(giftCertificate);

        return true;
    }

    private static void setDBFieldsToGiftCertificateFields(Map<String, Object> fieldsToUpdate, GiftCertificate giftCertificate) {
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            FieldSetHelper.setField(giftCertificate, entry.getKey(), entry.getValue());
        }
    }

    @Override
    public GiftCertificate getGiftCert(int id) throws NotFoundException {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gift certificate not found with id:" + id));

    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, List<String> sorting) {
        final Set<String> likeFields = Set.of("name", "description");

        GiftCertificate giftCertificate = new GiftCertificate();
        setDBFieldsToGiftCertificateFields(params, giftCertificate);

        ExampleMatcher matcher = ExampleMatcher.matching();

        for (Map.Entry<String, Object> entry :
                params.entrySet()) {
            matcher = matcher.withMatcher(entry.getKey(),
                    likeFields.contains(entry.getKey()) ? contains() : exact());
        }

        Example<GiftCertificate> example = Example.of(giftCertificate,matcher);

        return giftCertificateRepository.findAll(example);
    }


}
