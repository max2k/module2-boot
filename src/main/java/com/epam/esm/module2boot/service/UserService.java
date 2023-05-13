package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO) throws DataBaseConstrainException;

    UserDTO getUserDTO(int id) throws NotFoundException;

    User getUser(int userId) throws NotFoundException;

    User getUserByEmail(String email) throws NotFoundException;

    Page<UserDTO> getUserDTOList(Pageable pageable);

    UserDetails getUserDetailsByUser(User user);
}
