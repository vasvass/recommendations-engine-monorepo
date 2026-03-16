package com.vasvass.recommendationservice.controller;

import com.vasvass.recommendationservice.model.RecommendedItem;
import com.vasvass.recommendationservice.model.UserInteraction;
import com.vasvass.recommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    /**
     * Get recommendations for a user
     * GET /api/recommendations/{userId}?tenantId={tenantId}&algorithm={algorithm}&limit={limit}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendedItem>> getRecommendations(
            @PathVariable String userId,
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "content-based") String algorithm,
            @RequestParam(defaultValue = "10") int limit) {
        
        try {
            List<RecommendedItem> recommendations = recommendationService
                .getRecommendations(userId, tenantId, algorithm, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Record user interaction
     * POST /api/recommendations/interactions
     */
    @PostMapping("/interactions")
    public ResponseEntity<String> recordInteraction(@RequestBody InteractionRequest request) {
        try {
            recommendationService.recordInteraction(
                request.getUserId(), 
                request.getTenantId(), 
                request.getItemId(), 
                request.getInteractionType()
            );
            
            return ResponseEntity.ok("Interaction recorded successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to record interaction");
        }
    }
    
    /**
     * Get user's interaction history
     * GET /api/recommendations/{userId}/interactions?tenantId={tenantId}
     */
    @GetMapping("/{userId}/interactions")
    public ResponseEntity<List<UserInteraction>> getUserInteractions(
            @PathVariable String userId,
            @RequestParam String tenantId) {
        
        try {
            List<UserInteraction> interactions = recommendationService
                .getUserInteractions(userId, tenantId);
            
            return ResponseEntity.ok(interactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/recommendations/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "recommendation-service",
            "timestamp", String.valueOf(System.currentTimeMillis())
        ));
    }
    
    /**
     * Clean up expired recommendations
     * POST /api/recommendations/cleanup
     */
    @PostMapping("/cleanup")
    public ResponseEntity<String> cleanupExpiredRecommendations() {
        try {
            recommendationService.cleanupExpiredRecommendations();
            return ResponseEntity.ok("Cleanup completed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Cleanup failed");
        }
    }
    
    // Inner class for interaction request
    public static class InteractionRequest {
        private String userId;
        private String tenantId;
        private String itemId;
        private UserInteraction.InteractionType interactionType;
        
        // Constructors
        public InteractionRequest() {}
        
        public InteractionRequest(String userId, String tenantId, String itemId, 
                                UserInteraction.InteractionType interactionType) {
            this.userId = userId;
            this.tenantId = tenantId;
            this.itemId = itemId;
            this.interactionType = interactionType;
        }
        
        // Getters and Setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getTenantId() { return tenantId; }
        public void setTenantId(String tenantId) { this.tenantId = tenantId; }
        
        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        
        public UserInteraction.InteractionType getInteractionType() { return interactionType; }
        public void setInteractionType(UserInteraction.InteractionType interactionType) { 
            this.interactionType = interactionType; 
        }
    }
}
