package com.arz.linkme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.arz.linkme.feeds.repo")
//@EnableDiscoveryClient
class LinkmeFeeds
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        SpringApplication.run(LinkmeFeeds.class, args);
    }


}
