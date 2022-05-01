package com.arz.linkme.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@SpringBootConfiguration
@EnableKafka
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@ComponentScan(basePackages = {"com.arz.pdms.authenticate.service"})
class AuthApp
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        SpringApplication.run(AuthApp.class, args);
    }


}
