package com.arz.linkme.profiles.service;

import com.arz.linkme.profiles.db.dao.User;
import com.arz.linkme.profiles.db.repo.IGenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arz.linkme.profiles.db.repo.UserRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    private List<User> createdUsers = new ArrayList<User>();
    public static Map<Long, User> userDatabase = new ConcurrentHashMap<>();

    public Mono<String> createUser(User user){
        this.createdUsers.add(user);
        try {
            userRepository.save(user);
        }
        catch (Exception e){
            logger.error("Problem in registering the new UserController {}", user.getUsername());
            e.printStackTrace();
            return Mono.just("failure");
        }

        logger.info("User details successfully stored");
        return Mono.just("success");
    }


    public Mono<String> NotifyUserCreation(){
        return Mono.just("success");
    }


    public List<User> getCreatedUsers() {
        return createdUsers;
    }
}
