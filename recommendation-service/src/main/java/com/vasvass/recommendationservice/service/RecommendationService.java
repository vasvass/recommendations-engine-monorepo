package com.vasvass.recommendationservice.service;

import com.vasvass.recommendationservice.model.Recommendation;
import com.vasvass.recommendationservice.model.RecommendedItem;
import com.vasvass.recommendationservice.model.UserInteraction;
import com.vasvass.recommendationservice.repository.RecommendationRepository;
import com.vasvass.recommendationservice.repository.UserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {
    
    @Autowired
    private RecommendationRepository recommendationRepository;
    
    @Autowired
    private UserInteractionRepository userInteractionRepository;
    
    @Autowired
    private AlgorithmService algorithmService;
    
    @Autowired
    private ExternalServiceClient externalServiceClient;
    
    /**
     * Get recommendations for a user
     */
    @Cacheable(value = "recommendations", key = "#userId + '_' + #tenantId + '_' + #algorithmType")
    public List<RecommendedItem> getRecommendations(String userId, String tenantId, String algorithmType, int limit) {
        // Check for existing active recommendations first
        List<Recommendation> existingRecommendations = recommendationRepository
            .findActiveRecommendationsByUser(userId, tenantId, LocalDateTime.now());
        
        if (!existingRecommendations.isEmpty()) {
            // Return cached recommendations
            return existingRecommendations.get(0).getRecommendedItems();
        }
        
        // Generate new recommendations
        List<RecommendedItem> newRecommendations = generateRecommendations(userId, tenantId, algorithmType, limit);
        
        // Save recommendations to database
        Recommendation recommendation = new Recommendation(userId, tenantId, algorithmType, newRecommendations);
        recommendation.setConfidenceScore(calculateConfidenceScore(newRecommendations));
        recommendationRepository.save(recommendation);
        
        return newRecommendations;
    }
    
    /**
     * Generate recommendations using the specified algorithm
     */
    private List<RecommendedItem> generateRecommendations(String userId, String tenantId, String algorithmType, int limit) {
        // Get user interactions for context
        List<UserInteraction> userInteractions = userInteractionRepository
            .findRecentInteractionsByUser(userId, tenantId, LocalDateTime.now().minusDays(30));
        
        // Get user profile data from external service
        // This would be a call to user-profile-service
        
        // Apply recommendation algorithm
        return algorithmService.generateRecommendations(userId, tenantId, algorithmType, userInteractions, limit);
    }
    
    /**
     * Record user interaction for future recommendations
     */
    public void recordInteraction(String userId, String tenantId, String itemId, 
                                UserInteraction.InteractionType interactionType) {
        UserInteraction interaction = new UserInteraction(userId, tenantId, itemId, interactionType);
        userInteractionRepository.save(interaction);
        
        // Invalidate cache for this user
        // This would trigger cache eviction
    }
    
    /**
     * Get user's interaction history
     */
    public List<UserInteraction> getUserInteractions(String userId, String tenantId) {
        return userInteractionRepository.findByUserIdAndTenantId(userId, tenantId);
    }
    
    /**
     * Calculate confidence score for recommendations
     */
    private Double calculateConfidenceScore(List<RecommendedItem> recommendations) {
        if (recommendations.isEmpty()) {
            return 0.0;
        }
        
        double totalScore = recommendations.stream()
            .mapToDouble(RecommendedItem::getScore)
            .sum();
        
        return totalScore / recommendations.size();
    }
    
    /**
     * Clean up expired recommendations
     */
    public void cleanupExpiredRecommendations() {
        recommendationRepository.deleteExpiredRecommendations(LocalDateTime.now());
    }
}
