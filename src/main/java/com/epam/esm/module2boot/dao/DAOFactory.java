package com.epam.esm.module2boot.dao;

public interface DAOFactory {
    GiftCertDAO getGiftCertDAO();
    TagDAO getTagDAO();
}
