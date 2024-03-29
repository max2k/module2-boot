package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.UserDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.UserRepository;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpa-data")
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) throws DataBaseConstrainException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DataBaseConstrainException("User with this attributes cannot be created", e);
        }

    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
