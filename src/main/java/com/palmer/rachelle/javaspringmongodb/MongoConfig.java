package com.palmer.rachelle.javaspringmongodb;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() throws Exception {
        return MongoClientProvider.createSslClient();
    }
}
