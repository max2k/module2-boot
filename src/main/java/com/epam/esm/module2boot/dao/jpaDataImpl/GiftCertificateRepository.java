package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Profile("jpa-data")
@Transactional
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

}
