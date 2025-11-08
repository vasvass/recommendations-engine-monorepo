package com.vasvass.recommendationservice.service;

import com.vasvass.recommendationservice.model.RecommendedItem;
import com.vasvass.recommendationservice.model.UserInteraction;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlgorithmService {
    
    /**
     * Generate recommendations using different algorithms
     */
    public List<RecommendedItem> generateRecommendations(String userId, String tenantId, 
                                                       String algorithmType, 
                                                       List<UserInteraction> userInteractions, 
                                                       int limit) {
        
        switch (algorithmType.toLowerCase()) {
            case "content-based":
                return generateContentBasedRecommendations(userId, tenantId, userInteractions, limit);
            case "collaborative-filtering":
                return generateCollaborativeFilteringRecommendations(userId, tenantId, userInteractions, limit);
            case "hybrid":
                return generateHybridRecommendations(userId, tenantId, userInteractions, limit);
            default:
                return generateContentBasedRecommendations(userId, tenantId, userInteractions, limit);
        }
    }
    
    /**
     * Content-based filtering algorithm
     * Recommends items similar to what the user has interacted with
     */
    private List<RecommendedItem> generateContentBasedRecommendations(String userId, String tenantId, 
                                                                    List<UserInteraction> userInteractions, 
                                                                    int limit) {
        // Get user's preferred categories based on interactions
        Map<String, Long> categoryPreferences = getUserCategoryPreferences(userInteractions);
        
        // Generate recommendations based on category preferences
        List<RecommendedItem> recommendations = new ArrayList<>();
        
        for (Map.Entry<String, Long> entry : categoryPreferences.entrySet()) {
            String category = entry.getKey();
            Long interactionCount = entry.getValue();
            
            // Create mock recommendations based on categories
            // In a real implementation, this would query a product catalog service
            for (int i = 0; i < Math.min(limit / categoryPreferences.size(), 3); i++) {
                RecommendedItem item = new RecommendedItem();
                item.setItemId("item_" + category + "_" + i);
                item.setItemType("product");
                item.setTitle("Recommended " + category + " Item " + (i + 1));
                item.setCategory(category);
                item.setScore(0.8 + (Math.random() * 0.2)); // Score between 0.8-1.0
                item.setReason("Based on your interest in " + category);
                
                recommendations.add(item);
            }
        }
        
        return recommendations.stream()
            .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    /**
     * Collaborative filtering algorithm
     * Recommends items based on similar users' preferences
     */
    private List<RecommendedItem> generateCollaborativeFilteringRecommendations(String userId, String tenantId, 
                                                                              List<UserInteraction> userInteractions, 
                                                                              int limit) {
        // Find similar users based on interaction patterns
        List<String> similarUsers = findSimilarUsers(userId, tenantId, userInteractions);
        
        // Get items that similar users liked but current user hasn't interacted with
        List<RecommendedItem> recommendations = new ArrayList<>();
        
        // Mock implementation - in reality would query database for similar users' interactions
        for (int i = 0; i < limit; i++) {
            RecommendedItem item = new RecommendedItem();
            item.setItemId("collab_item_" + i);
            item.setItemType("product");
            item.setTitle("Collaborative Recommendation " + (i + 1));
            item.setScore(0.7 + (Math.random() * 0.3)); // Score between 0.7-1.0
            item.setReason("Users with similar preferences also liked this");
            
            recommendations.add(item);
        }
        
        return recommendations.stream()
            .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    /**
     * Hybrid algorithm combining content-based and collaborative filtering
     */
    private List<RecommendedItem> generateHybridRecommendations(String userId, String tenantId, 
                                                             List<UserInteraction> userInteractions, 
                                                             int limit) {
        // Get recommendations from both algorithms
        List<RecommendedItem> contentBased = generateContentBasedRecommendations(userId, tenantId, userInteractions, limit / 2);
        List<RecommendedItem> collaborative = generateCollaborativeFilteringRecommendations(userId, tenantId, userInteractions, limit / 2);
        
        // Combine and re-rank
        List<RecommendedItem> combined = new ArrayList<>();
        combined.addAll(contentBased);
        combined.addAll(collaborative);
        
        // Remove duplicates and re-rank
        Map<String, RecommendedItem> uniqueItems = new HashMap<>();
        for (RecommendedItem item : combined) {
            if (!uniqueItems.containsKey(item.getItemId()) || 
                uniqueItems.get(item.getItemId()).getScore() < item.getScore()) {
                uniqueItems.put(item.getItemId(), item);
            }
        }
        
        return uniqueItems.values().stream()
            .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    /**
     * Analyze user interactions to determine category preferences
     */
    private Map<String, Long> getUserCategoryPreferences(List<UserInteraction> userInteractions) {
        // Mock implementation - in reality would analyze actual item categories
        Map<String, Long> preferences = new HashMap<>();
        preferences.put("electronics", 5L);
        preferences.put("books", 3L);
        preferences.put("clothing", 2L);
        
        return preferences;
    }
    
    /**
     * Find users with similar interaction patterns
     */
    private List<String> findSimilarUsers(String userId, String tenantId, List<UserInteraction> userInteractions) {
        // Mock implementation - in reality would use sophisticated similarity algorithms
        return Arrays.asList("user_123", "user_456", "user_789");
    }
}
