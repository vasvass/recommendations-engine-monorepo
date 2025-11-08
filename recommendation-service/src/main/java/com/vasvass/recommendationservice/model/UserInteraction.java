package com.vasvass.recommendationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "user_interactions")
public class UserInteraction {
    
    @Id
    private String id;
    
    @Field("user_id")
    private String userId;
    
    @Field("tenant_id")
    private String tenantId;
    
    @Field("item_id")
    private String itemId;
    
    @Field("interaction_type")
    private InteractionType interactionType;
    
    @Field("timestamp")
    private LocalDateTime timestamp;
    
    @Field("session_id")
    private String sessionId;
    
    @Field("metadata")
    private String metadata; // JSON string for additional data
    
    public enum InteractionType {
        VIEW, CLICK, LIKE, DISLIKE, PURCHASE, SHARE, BOOKMARK, RATE
    }
    
    // Constructors
    public UserInteraction() {}
    
    public UserInteraction(String userId, String tenantId, String itemId, 
                          InteractionType interactionType) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.itemId = itemId;
        this.interactionType = interactionType;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    public InteractionType getInteractionType() {
        return interactionType;
    }
    
    public void setInteractionType(InteractionType interactionType) {
        this.interactionType = interactionType;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
