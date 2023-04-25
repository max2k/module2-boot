package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;

public interface UserService {
    UserDTO createUser(UserDTO userDTO) throws DataBaseConstrainException;

    UserDTO getUserDTO(int id) throws NotFoundException;

    User getUser(int userId);
}
