package com.arz.linkme.profiles.db.repo;


import com.arz.linkme.profiles.db.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
