package com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository;

import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("jpa-data")
@Transactional
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

//    @Query("SELECT gc FROM GiftCertificate gc  " +
//            "LEFT JOIN gc.tags t " +
//            "WHERE (t.name in :tags) and " +
//            "(gc.name LIKE %:name% OR :name is null) " +
//            "and (gc.description LIKE %:desc% OR :desc is null) ")
//    Page<GiftCertificate> findByFiltersWithTags(@Param("name") String name
//            , @Param("desc") String description
//            , @Param("tags") List<String> tagName, Pageable pageable);
//
//    @Query("SELECT gc FROM GiftCertificate gc  " +
//            "WHERE (gc.name LIKE :name OR :name is null) " +
//            "and (gc.description LIKE :desc OR :desc is null) ")
//    Page<GiftCertificate> findByFilters(@Param("name") String name
//            , @Param("desc") String description, Pageable pageable);

//    @Query("SELECT gc FROM GiftCertificate gc  " +
//            "LEFT JOIN gc.tags t " +
//            "WHERE ( t.name in :tags OR :useTags=\"true\") and " +
//            "(gc.name LIKE %:subStr% OR gc.description LIKE %:subStr%) ")
//    Page<GiftCertificate> findByFiltersWithSubStr(@Param("useTags") String useTags, @Param("subStr") String subStr
//            ,@Param("tags") List<String> tagName, Pageable pageable);

//    @Query("SELECT gc FROM GiftCertificate gc  " +
//            "LEFT JOIN gc.tags t " +
//            "WHERE  ( t.name in :tags OR :useTags='true') AND " +
//            "(gc.name LIKE %:subStr% OR gc.description LIKE %:subStr%) ")
//    Page<GiftCertificate> findByFiltersWithSubStr(@Param("subStr") String subStr, @Param("useTags") String useTags,
//            @Param("tags") List<String> tagName, Pageable pageable);

//    @Query("SELECT gc FROM GiftCertificate gc  " +
//            "LEFT JOIN gc.tags t " +
//            "WHERE  ( t.name in :tags OR :useTags='true') AND " +
//            "(:subStr is null OR gc.name LIKE %:subStr% OR gc.description LIKE %:subStr%) " +
//            "group by gc " +
//            "having count(t)=:tagCount")
//    Page<GiftCertificate> findByFiltersWithSubStr(@Param("subStr") String subStr,
//                                                  @Param("useTags") String useTags,
//                                                  @Param("tags") List<String> tagName,
//                                                  @Param("tagCount") Integer tagCount,
//                                                  Pageable pageable);

    @Query("SELECT gc FROM GiftCertificate gc  " +
            "WHERE  ( (select count(t.name) from gc.tags t WHERE t.name IN :tags)=:tagCount " +
            " OR :useTags='true') AND " +
            "(:subStr is null OR gc.name LIKE %:subStr% OR gc.description LIKE %:subStr%)")
    Page<GiftCertificate> findByFiltersWithSubStr(@Param("subStr") String subStr,
                                                  @Param("useTags") String useTags,
                                                  @Param("tags") List<String> tagName,
                                                  @Param("tagCount") Integer tagCount,
                                                  Pageable pageable);

}
