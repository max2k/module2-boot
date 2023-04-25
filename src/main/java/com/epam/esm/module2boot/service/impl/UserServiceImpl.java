package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.UserDAO;
import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;
import com.epam.esm.module2boot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws DataBaseConstrainException {
        User user = modelMapper.map(userDTO, User.class);
        try {
            User createdUser = userDAO.createUser(user);
            return modelMapper.map(createdUser, UserDTO.class);
        } catch (Exception e) {
            throw new DataBaseConstrainException("User with this attributes cannot be created", e);
        }

    }

    @Override
    public UserDTO getUserDTO(int id) throws NotFoundException {
        return modelMapper.map(getUser(id), UserDTO.class);
    }

    @Override
    public User getUser(int userId) throws NotFoundException {
        User user = userDAO.getUser(userId);
        if (user == null) throw new NotFoundException("User cannot be found with this id" + userId);
        return user;
    }
}
