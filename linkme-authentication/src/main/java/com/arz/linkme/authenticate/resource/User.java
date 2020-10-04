package com.arz.linkme.authenticate.resource;


import com.arz.linkme.authenticate.service.JwtTokenUtil;
import com.arz.linkme.authenticate.model.JwtRequest;
import com.arz.linkme.authenticate.model.JwtResponse;
import com.arz.linkme.authenticate.model.UserDetailRequest;
import com.arz.linkme.authenticate.model.UserDetailResponse;
//import com.arz.pdms.authenticate.service.JwtUserDetailsService;
import com.arz.linkme.authenticate.service.JwtUserDetailsService;
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
public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

//    @Autowired
//    UserRegistrationService userRegistrationService;

    @PostMapping("/auth/status")
    //@ApiOperation(value = "Place a Lab Order", notes = "Send generated lab orders from a LIS")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "If the test orders successfully stored to middleware database",
//                    response = LabOrderResponse.class),
//            @ApiResponse(code = 400, message = "If request parameters invalid"),
//            @ApiResponse(code = 401, message = "If request is not authorized"),
//            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
//    })
    public ResponseEntity<UserDetailResponse> loginStatus(@Valid @RequestBody JwtRequest jwtRequest) {
        logger.info("Auth Status request received");
        System.out.println("User: " + jwtRequest.getUsername() +"," + jwtRequest.getPassword());
        //String response= testOrderGenerator.generateTestOrder(labOrderRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
       //httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>( new UserDetailResponse(0, "Success"), httpHeaders, HttpStatus.OK);

    }


    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
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



    @PostMapping("/authorize")
    //@ApiOperation(value = "Place a Lab Order", notes = "Send generated lab orders from a LIS")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "If the test orders successfully stored to middleware database",
//                    response = LabOrderResponse.class),
//            @ApiResponse(code = 400, message = "If request parameters invalid"),
//            @ApiResponse(code = 401, message = "If request is not authorized"),
//            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
//    })
    public ResponseEntity<UserDetailResponse> createUser(@Valid @RequestBody UserDetailRequest userDetailRequest) {
        logger.info("User registration request received");
        System.out.println("User: " + userDetailRequest.getLastName() +"," + userDetailRequest.getPassword());
        //String response= testOrderGenerator.generateTestOrder(labOrderRequest);

       // String response=userRegistrationService.registerUser(userDetailRequest);
      String response="Success";

        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Content-Type", "application/json");

        if(response.equals("Success")){
            return new ResponseEntity<>( new UserDetailResponse(0, "Success"), httpHeaders, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new UserDetailResponse(1, "Failure"),httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
