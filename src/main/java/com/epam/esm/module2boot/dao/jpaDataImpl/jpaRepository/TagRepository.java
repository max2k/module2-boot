package com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository;

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
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("SELECT t FROM GiftCertificate gc JOIN gc.tags t WHERE gc.id = :id")
    List<Tag> findTagsByGiftCertificateId(@Param("id") int id);

    @Query(value = """
                    select  tag.*  from tag join cert_tag on tag.id=cert_tag.tag_id\s
                       join ordertable on ordertable.cert_id = cert_tag.cert_id
                    where user_id=:userID
                    group by tag.name
                    order by count(*) desc
                    limit 1
            """,
            nativeQuery = true)
    Tag findMostUsedTagForUserID(@Param("userID") int userID);
}
