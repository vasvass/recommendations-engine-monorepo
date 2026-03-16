package com.vasvass.recommendationservice.controller;

import com.vasvass.recommendationservice.model.Recommendation;
import com.vasvass.recommendationservice.model.UserInteraction;
import com.vasvass.recommendationservice.repository.RecommendationRepository;
import com.vasvass.recommendationservice.repository.UserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private RecommendationRepository recommendationRepository;
    
    @Autowired
    private UserInteractionRepository userInteractionRepository;
    
    /**
     * Get all recommendations for a tenant
     * GET /api/admin/recommendations?tenantId={tenantId}
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<Recommendation>> getAllRecommendations(
            @RequestParam String tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            // This is a simplified implementation
            // In a real scenario, you'd implement pagination
            List<Recommendation> recommendations = recommendationRepository
                .findByUserIdAndTenantId("", tenantId); // This would need proper implementation
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get interaction statistics
     * GET /api/admin/statistics?tenantId={tenantId}
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(@RequestParam String tenantId) {
        try {
            // Get basic statistics
            long totalInteractions = userInteractionRepository.count();
            long totalRecommendations = recommendationRepository.count();
            
            Map<String, Object> stats = Map.of(
                "totalInteractions", totalInteractions,
                "totalRecommendations", totalRecommendations,
                "tenantId", tenantId,
                "timestamp", LocalDateTime.now().toString()
            );
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Delete recommendations for a user
     * DELETE /api/admin/recommendations/{userId}?tenantId={tenantId}
     */
    @DeleteMapping("/recommendations/{userId}")
    public ResponseEntity<String> deleteUserRecommendations(
            @PathVariable String userId,
            @RequestParam String tenantId) {
        
        try {
            List<Recommendation> userRecommendations = recommendationRepository
                .findByUserIdAndTenantId(userId, tenantId);
            
            recommendationRepository.deleteAll(userRecommendations);
            
            return ResponseEntity.ok("Recommendations deleted for user: " + userId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to delete recommendations");
        }
    }
    
    /**
     * Get system health with detailed information
     * GET /api/admin/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        try {
            long recommendationCount = recommendationRepository.count();
            long interactionCount = userInteractionRepository.count();
            
            Map<String, Object> health = Map.of(
                "status", "UP",
                "service", "recommendation-service",
                "recommendationCount", recommendationCount,
                "interactionCount", interactionCount,
                "timestamp", LocalDateTime.now().toString(),
                "uptime", System.currentTimeMillis()
            );
            
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "DOWN",
                "error", e.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
        }
    }
}
