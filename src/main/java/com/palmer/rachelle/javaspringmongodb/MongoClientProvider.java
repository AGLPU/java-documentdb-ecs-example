package com.palmer.rachelle.javaspringmongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class MongoClientProvider {

    private static final Logger log = LoggerFactory.getLogger(MongoClientProvider.class);

    public static MongoClient createSslClient() throws Exception {

       // String base64 = "PjNIcllwcG0+Uk9bMTlkZFdiUls1WExwM01wLQ==";
       // String decodedPassword = new String(Base64.getDecoder().decode(base64));
        var mongoUri=System.getenv("MONGO_URI");
        var password=System.getenv("MONGO_PASSWORD");
        var dbNme=System.getenv("MONGO_DBNAME");

        log.info("mongoUri:{}, password:{}, dbNme:{}",mongoUri,password,dbNme);
        MongoCredential credential = MongoCredential.createCredential(
                "aglpu",
                dbNme,
                password.toCharArray()
        );

        // Build the MongoClientSettings
        ConnectionString connString = new ConnectionString(
                System.getenv(mongoUri)
         );
        log.info("connString:{}",connString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
            .applyConnectionString(connString)
            .applyToSslSettings(ssl -> ssl.enabled(true).invalidHostNameAllowed(true))
            .build();

        return MongoClients.create(settings);
    }
}
