package com.arz.linkme.authentication.model.db;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;


//    @OneToMany(mappedBy = "department")
//    private Set<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
//
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
