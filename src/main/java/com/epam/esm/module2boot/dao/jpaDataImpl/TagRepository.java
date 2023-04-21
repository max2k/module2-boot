package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.model.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Profile("jpa-data")
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("SELECT t FROM GiftCertificate gc JOIN gc.tags t WHERE gc.id = :id")
    List<Tag> findTagsByGiftCertificateId(@Param("id") int id);
}
