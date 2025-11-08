package com.vasvass.recommendationservice.repository;

import com.vasvass.recommendationservice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    
    // Find recommendations for a specific user
    List<Recommendation> findByUserIdAndTenantId(String userId, String tenantId);
    
    // Find active (non-expired) recommendations for a user
    @Query("{ 'userId': ?0, 'tenantId': ?1, 'expiresAt': { $gt: ?2 } }")
    List<Recommendation> findActiveRecommendationsByUser(String userId, String tenantId, LocalDateTime now);
    
    // Find recommendations by algorithm type
    List<Recommendation> findByUserIdAndTenantIdAndAlgorithmType(String userId, String tenantId, String algorithmType);
    
    // Find recommendations created after a specific time
    List<Recommendation> findByUserIdAndTenantIdAndCreatedAtAfter(String userId, String tenantId, LocalDateTime createdAt);
    
    // Delete expired recommendations
    @Query("{ 'expiresAt': { $lt: ?0 } }")
    void deleteExpiredRecommendations(LocalDateTime now);
    
    // Find recommendations with confidence score above threshold
    @Query("{ 'userId': ?0, 'tenantId': ?1, 'confidenceScore': { $gte: ?2 } }")
    List<Recommendation> findRecommendationsByConfidenceScore(String userId, String tenantId, Double minScore);
}
