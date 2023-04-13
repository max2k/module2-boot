package com.epam.esm.module2boot.dao;

public interface DAOFactory {
    GiftCertificateDAO getGiftCertDAO();
    TagDAO getTagDAO();
}
