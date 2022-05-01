package com.arz.linkme.authentication.controller;


import com.arz.linkme.authentication.model.LoginRequest;
import com.arz.linkme.authentication.model.LoginResponse;
import com.arz.linkme.authentication.model.RegistrationRequest;
import com.arz.linkme.authentication.model.UserDetailResponse;
import com.arz.linkme.authentication.model.db.User;
import com.arz.linkme.authentication.service.JwtTokenUtil;
import com.arz.linkme.authentication.service.TopicProducer;
import com.arz.linkme.authentication.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
//@Api(tags = {"Lab Order"}, description = "Post Generated Lab Orders", consumes = "application/json"
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    TopicProducer topicProducer;

    ObjectMapper Obj = new ObjectMapper();

//    @Autowired
//    private JwtUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @PostMapping("/auth/status")
    //@ApiOperation(value = "Place a Lab Order", notes = "Send generated lab orders from a LIS")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "If the test orders successfully stored to middleware database",
//                    response = LabOrderResponse.class),
//            @ApiResponse(code = 400, message = "If request parameters invalid"),
//            @ApiResponse(code = 401, message = "If request is not authorized"),
//            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
//    })
    public ResponseEntity<UserDetailResponse> loginStatus(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Auth Status request received");
        System.out.println("User: " + loginRequest.getUsername() +"," + loginRequest.getPassword());
        //String response= testOrderGenerator.generateTestOrder(labOrderRequest);


        HttpHeaders httpHeaders = new HttpHeaders();
       //httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>( new UserDetailResponse(0, "Success"), httpHeaders, HttpStatus.OK);

    }


    @RequestMapping(path = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



    @PostMapping("/sign-up")
    public ResponseEntity<UserDetailResponse> createUser(@Valid @RequestBody RegistrationRequest regDetails) {
        logger.info("User registration request received");
        System.out.println("User: " + regDetails.getLastName() +"," + regDetails.getPassword());
        //String response= testOrderGenerator.generateTestOrder(labOrderRequest);

       // String response=userRegistrationService.registerUser(user);

//        User user = userService.registerNewUser(regDetails);
        try {
            topicProducer.send( Obj.writeValueAsString(regDetails));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Content-Type", "application/json");
        return new ResponseEntity<>( new UserDetailResponse(0, "Success"), httpHeaders, HttpStatus.OK);
//        if(user!=null){
//            return new ResponseEntity<>( new UserDetailResponse(0, "Success"), httpHeaders, HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>(new UserDetailResponse(1, "Failure"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }

}
