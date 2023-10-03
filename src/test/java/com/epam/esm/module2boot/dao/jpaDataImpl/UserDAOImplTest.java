package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.UserDAO;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Status;
import com.epam.esm.module2boot.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void createUser() throws DataBaseConstrainException {
        User user = new User();
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setEmail("email");
        user.setPassword("test password");
        user.setStatus(Status.ACTIVE);

        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);

        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }
}