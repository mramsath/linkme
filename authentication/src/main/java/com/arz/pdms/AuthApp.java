package com.arz.pdms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"com.arz.pdms.authenticate.service"})
public class AuthApp
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        SpringApplication.run(AuthApp.class, args);
    }


}
