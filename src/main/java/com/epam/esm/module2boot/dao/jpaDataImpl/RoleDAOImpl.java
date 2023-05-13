package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.RoleDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.RoleRepository;
import com.epam.esm.module2boot.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final RoleRepository roleRepository;

    public RoleDAOImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new RuntimeException("No role found with name:" + name));
    }
}
