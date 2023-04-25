package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.UserDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.UserRepository;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
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
}
