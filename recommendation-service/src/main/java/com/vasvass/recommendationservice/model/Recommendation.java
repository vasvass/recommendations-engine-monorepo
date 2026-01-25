package com.vasvass.recommendationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendations")
public class Recommendation {
    
    @Id
    private String id;
    
    @Field("user_id")
    private String userId;
    
    @Field("tenant_id")
    private String tenantId;
    
    @Field("algorithm_type")
    private String algorithmType;
    
    @Field("recommended_items")
    private List<RecommendedItem> recommendedItems;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("expires_at")
    private LocalDateTime expiresAt;
    
    @Field("confidence_score")
    private Double confidenceScore;
    
    // Constructors
    public Recommendation() {}
    
    public Recommendation(String userId, String tenantId, String algorithmType, 
                         List<RecommendedItem> recommendedItems) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.algorithmType = algorithmType;
        this.recommendedItems = recommendedItems;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(1); // Default 1 hour expiry
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
    
    public String getAlgorithmType() {
        return algorithmType;
    }
    
    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }
    
    public List<RecommendedItem> getRecommendedItems() {
        return recommendedItems;
    }
    
    public void setRecommendedItems(List<RecommendedItem> recommendedItems) {
        this.recommendedItems = recommendedItems;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public Double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}
