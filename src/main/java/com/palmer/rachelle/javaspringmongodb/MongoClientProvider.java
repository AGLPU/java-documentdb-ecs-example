package com.palmer.rachelle.javaspringmongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Base64;

public class MongoClientProvider {

    public static MongoClient createSslClient() throws Exception {
        // Load your CA certificate into a Java truststore (JKS)
        String trustStorePath = "C:/Learning/Projects/Usersaman.goel1docdb-truststore.jks";
        char[] trustStorePassword = "changeit".toCharArray();

        KeyStore ts = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream(trustStorePath)) {
            ts.load(fis, trustStorePassword);
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);

        String base64 = "PjNIcllwcG0+Uk9bMTlkZFdiUls1WExwM01wLQ==";
        String decodedPassword = new String(Base64.getDecoder().decode(base64));

        // Create an SSLContext using this trust store
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        MongoCredential credential = MongoCredential.createCredential(
                "aglpu",
                "BookStore",
                decodedPassword.toCharArray()
        );

        // Build the MongoClientSettings
        ConnectionString connString = new ConnectionString(
            "mongodb://localhost:27017/?tls=true&retryWrites=false"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
            .applyConnectionString(connString)
            .applyToSslSettings(ssl -> ssl.enabled(true).context(sslContext).invalidHostNameAllowed(true))
            .build();

        return MongoClients.create(settings);
    }
}
