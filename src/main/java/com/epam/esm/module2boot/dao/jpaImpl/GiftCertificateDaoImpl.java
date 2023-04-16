package com.epam.esm.module2boot.dao.jpaImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.GiftCertificate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Profile("jpa")
public class GiftCertificateDaoImpl implements GiftCertificateDAO {

    final EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate createGiftCert(GiftCertificate giftCertificate) {

        entityManager.persist(giftCertificate);
        entityManager.flush();
        return entityManager.find(GiftCertificate.class, giftCertificate.getId());

    }

    @Override
    public boolean deleteGiftCert(int id) throws NotFoundException {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate == null) throw new NotFoundException("GiftCertificate not found with id:" + id);
        entityManager.remove(giftCertificate);
        entityManager.flush();
        return true;
    }

    @Override
    public boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate) throws BadRequestException{
        if (fieldsToUpdate == null || fieldsToUpdate.isEmpty() ) throw new BadRequestException("No fields to update");
        Map<String,Object> fieldSet=HQLHelper.changeSQlNamesToHQL(fieldsToUpdate);
        String setStr=HQLHelper.getSetStr(fieldSet);

        Query query= entityManager.createQuery("UPDATE GiftCertificate gc SET "+setStr+
                                                " WHERE gc.id=:id");
        query.setParameter("id",id);
        for (Map.Entry<String, Object> field : fieldSet.entrySet()) {
            query.setParameter(field.getKey().replace(".","_"),field.getValue());
        }

        return query.executeUpdate()==1;
    }



    @Override
    public GiftCertificate getGiftCert(int id) throws NotFoundException {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate == null)
            throw new NotFoundException("Gift certificate not found with id:" + id);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, List<String> sorting) {
        String whereStr = HQLHelper.getWhereStr(params);
        String sortStr = HQLHelper.getSortingSubStr(sorting);

        System.out.println("\nqueryStr:" + whereStr);
        System.out.println("sortStr:" + sortStr + "\n");

        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                """
                              SELECT gc FROM GiftCertificate gc
                              OUTER JOIN gc.tags t
                        """ + whereStr + sortStr
                , GiftCertificate.class);

        if (params!=null && params.size()>0) params.forEach((s, o) ->
                query.setParameter(HQLHelper.translateParameter(s), o));


        return query.getResultList();
    }


}
