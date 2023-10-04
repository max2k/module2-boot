package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.Role;


public interface RoleDAO {
    Role findRoleByName(String name);
}
