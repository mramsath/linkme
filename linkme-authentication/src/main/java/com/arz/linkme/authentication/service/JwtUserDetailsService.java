package com.arz.linkme.authentication.service;

//import com.arz.pdms.authenticate.db.repo.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
public class JwtUserDetailsService implements UserDetailsService {

//    @Autowired
//    private UserRepository userRepository;

   /* @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserController user = userRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        });

        UserDetails userDetails = new UserController(user.getLastName(), user.getPassword(), authorities);

        return userDetails;
    }*/


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("javainuse".equals(username)) {
            return new User("javainuse",
              "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
              new ArrayList<>()     //roles
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
