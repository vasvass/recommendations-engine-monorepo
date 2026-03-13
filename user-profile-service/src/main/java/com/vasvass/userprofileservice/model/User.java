package com.vasvass.userprofileservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String tenantId;
    private Map<String, Object> preferences;

    // Constructors
    public User() {}

    public User(String username, String email, String tenantId, Map<String, Object> preferences) {
        this.username = username;
        this.email = email;
        this.tenantId = tenantId;
        this.preferences = preferences;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Map<String, Object> getPreferences() { return preferences; }
    public void setPreferences(Map<String, Object> preferences) { this.preferences = preferences; }
}
