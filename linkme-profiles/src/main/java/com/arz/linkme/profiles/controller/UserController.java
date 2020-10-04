package com.arz.linkme.profiles.controller;



import com.arz.linkme.profiles.db.dao.User;
import com.arz.linkme.profiles.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Stream;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
//@Api(tags = {"Lab Order"}, description = "Post Generated Lab Orders", consumes = "application/json"
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> create(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ServerSentEvent<Collection<User>>> notifyUserCreation(){

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> tick == 0 ? userService.getCreatedUsers(): userService.getCreatedUsers())
                .map(users -> ServerSentEvent.<Collection<User>> builder()
                        .event("stock-changed")
                        .data(users)
                        .build());
    }



   /* @PostMapping("/register")
    @ApiOperation(value = "Register", notes = "Register a new UserController")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If the new user is created successfully",
                    response = RegistrationResponse.class),
            @ApiResponse(code = 400, message = "If request parameters invalid"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
    })
    public ResponseEntity<RegistrationResponse> createUser(@Valid @RequestBody com.arz.linkme.profiles.db.dao.User registrationRequest) {
        logger.info("UserController registration request received");
        System.out.println("UserController: " + registrationRequest.getLastName() +"," + registrationRequest.getPassword());
        //String response= testOrderGenerator.generateTestOrder(labOrderRequest);

        String response= userService.createUser(registrationRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Content-Type", "application/json");

        if(response.equals("Success")){
            return new ResponseEntity<>( new RegistrationResponse(0, "Success"), httpHeaders, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new RegistrationResponse(1, "Failure"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
*/
}
