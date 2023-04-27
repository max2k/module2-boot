package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDAO {
    User createUser(User user) throws DataBaseConstrainException;

    User getUser(Integer id);

    Page<User> getUserList(Pageable pageable);
}
