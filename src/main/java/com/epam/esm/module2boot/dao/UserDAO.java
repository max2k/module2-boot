package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;

public interface UserDAO {
    User createUser(User user) throws DataBaseConstrainException;

    User getUser(Integer id);
}
