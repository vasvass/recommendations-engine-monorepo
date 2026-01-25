package com.vasvass.recommendationservice.model;

public class RecommendedItem {
    
    private String itemId;
    private String itemType; // product, article, media, etc.
    private String title;
    private String description;
    private String category;
    private Double score;
    private String reason; // Why this item was recommended
    
    // Constructors
    public RecommendedItem() {}
    
    public RecommendedItem(String itemId, String itemType, String title, Double score) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.title = title;
        this.score = score;
    }
    
    // Getters and Setters
    public String getItemId() {
        return itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
}
