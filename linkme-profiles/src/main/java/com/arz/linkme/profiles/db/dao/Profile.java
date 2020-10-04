package com.arz.linkme.profiles.db.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "user_profiles")
public class Profile {

    @Id
    private Long userId;


}
