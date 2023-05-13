package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.RoleDAO;
import com.epam.esm.module2boot.model.Role;
import com.epam.esm.module2boot.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDAO.findRoleByName(name);
    }
}
