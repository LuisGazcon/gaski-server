package com.gazcon.gaski.services;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service()
public class FirebaseService {

    @Value("${firebase.databaseUrl}")
    private String databaseUrl;

    @Value("${firebase.serviceAccount}")
    private String serviceAccount;

    @Value("${firebase.projectId}")
    private String projectId;

    private GoogleCredentials credentials;

    private InputStream getStream() {
        return new ByteArrayInputStream(this.serviceAccount.getBytes(StandardCharsets.UTF_8));
    }
    
    @PostConstruct
    public void initialize() {
        try {
            this.credentials = GoogleCredentials.fromStream(this.getStream());
        } catch(IOException exception) {

        }

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(this.credentials)
            .setDatabaseUrl(this.databaseUrl)
            .setProjectId(this.projectId)
            .build();
            FirebaseApp.initializeApp(options);
        } catch(Exception exception) {

        }
    }
}