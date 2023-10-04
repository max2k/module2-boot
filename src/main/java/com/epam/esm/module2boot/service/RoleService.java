package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.model.Role;

public interface RoleService {
    Role findRoleByName(String name);

}
