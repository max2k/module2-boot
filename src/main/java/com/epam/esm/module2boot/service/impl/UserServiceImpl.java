package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.UserDAO;
import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Role;
import com.epam.esm.module2boot.model.Status;
import com.epam.esm.module2boot.model.User;
import com.epam.esm.module2boot.service.RoleService;
import com.epam.esm.module2boot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws DataBaseConstrainException {
        User user = modelMapper.map(userDTO, User.class);
        try {
            user.setId(User.NO_ID); // to avoid id from client and prevent field from updating

            user.setRoles(Set.of(roleService.findRoleByName("USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.ACTIVE);

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

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User cannot be found with this email" + email));
    }

    @Override
    public Page<UserDTO> getUserDTOList(Pageable pageable) {
        return userDAO.getUserList(pageable).map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDetails getUserDetailsByUser(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .accountLocked(user.getStatus() != Status.ACTIVE)
                .accountExpired(user.getStatus() != Status.ACTIVE)

                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = getUserByEmail(username);
            return getUserDetailsByUser(user);

        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("User not found with username: " + username, e);
        }

    }
}
