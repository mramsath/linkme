package com.arz.linkme.authentication.repos;

import com.arz.linkme.authentication.model.db.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String roleName);
}
