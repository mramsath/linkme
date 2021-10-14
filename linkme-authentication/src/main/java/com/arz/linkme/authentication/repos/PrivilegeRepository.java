package com.arz.linkme.authentication.repos;

import com.arz.linkme.authentication.model.db.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Privilege findByName(String privilegeName);
}
