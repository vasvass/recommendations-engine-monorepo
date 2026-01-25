package com.vasvass.recommendationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class ExternalServiceClient {
    
    private final WebClient webClient;
    
    @Value("${user-profile-service.url}")
    private String userProfileServiceUrl;
    
    @Value("${product-catalog-service.url}")
    private String productCatalogServiceUrl;
    
    public ExternalServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    /**
     * Get user profile data from user-profile-service
     */
    public Mono<UserProfile> getUserProfile(String userId, String tenantId) {
        return webClient.get()
            .uri(userProfileServiceUrl + "/{userId}?tenantId={tenantId}", userId, tenantId)
            .retrieve()
            .bodyToMono(UserProfile.class)
            .onErrorReturn(new UserProfile()); // Return empty profile on error
    }
    
    /**
     * Get product information from product-catalog-service
     */
    public Mono<ProductInfo> getProductInfo(String itemId, String tenantId) {
        return webClient.get()
            .uri(productCatalogServiceUrl + "/{itemId}?tenantId={tenantId}", itemId, tenantId)
            .retrieve()
            .bodyToMono(ProductInfo.class)
            .onErrorReturn(new ProductInfo()); // Return empty product on error
    }
    
    /**
     * Get multiple products information
     */
    public Mono<List<ProductInfo>> getProductsInfo(String[] itemIds, String tenantId) {
        return webClient.get()
            .uri(productCatalogServiceUrl + "/batch?ids={ids}&tenantId={tenantId}", 
                 String.join(",", itemIds), tenantId)
            .retrieve()
            .bodyToFlux(ProductInfo.class)
            .collectList();
    }
    
    // Inner classes for data transfer
    public static class UserProfile {
        private String userId;
        private String username;
        private String email;
        private String tenantId;
        private String[] preferences;
        private String[] interests;
        
        // Constructors, getters, setters
        public UserProfile() {}
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getTenantId() { return tenantId; }
        public void setTenantId(String tenantId) { this.tenantId = tenantId; }
        
        public String[] getPreferences() { return preferences; }
        public void setPreferences(String[] preferences) { this.preferences = preferences; }
        
        public String[] getInterests() { return interests; }
        public void setInterests(String[] interests) { this.interests = interests; }
    }
    
    public static class ProductInfo {
        private String itemId;
        private String title;
        private String description;
        private String category;
        private String[] tags;
        private Double price;
        private String[] features;
        
        // Constructors, getters, setters
        public ProductInfo() {}
        
        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String[] getTags() { return tags; }
        public void setTags(String[] tags) { this.tags = tags; }
        
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        
        public String[] getFeatures() { return features; }
        public void setFeatures(String[] features) { this.features = features; }
    }
}
