package com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository;

import com.epam.esm.module2boot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
