package com.vasvass.recommendationservice.repository;

import com.vasvass.recommendationservice.model.UserInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserInteractionRepository extends MongoRepository<UserInteraction, String> {
    
    // Find interactions for a specific user
    List<UserInteraction> findByUserIdAndTenantId(String userId, String tenantId);
    
    // Find interactions by type
    List<UserInteraction> findByUserIdAndTenantIdAndInteractionType(String userId, String tenantId, 
                                                                   UserInteraction.InteractionType interactionType);
    
    // Find interactions within a time range
    List<UserInteraction> findByUserIdAndTenantIdAndTimestampBetween(String userId, String tenantId, 
                                                                    LocalDateTime startTime, LocalDateTime endTime);
    
    // Find recent interactions for a user
    @Query("{ 'userId': ?0, 'tenantId': ?1, 'timestamp': { $gte: ?2 } }")
    List<UserInteraction> findRecentInteractionsByUser(String userId, String tenantId, LocalDateTime since);
    
    // Count interactions by type for a user
    long countByUserIdAndTenantIdAndInteractionType(String userId, String tenantId, 
                                                   UserInteraction.InteractionType interactionType);
    
    // Find interactions for items that a user has interacted with
    @Query("{ 'userId': ?0, 'tenantId': ?1, 'itemId': { $in: ?2 } }")
    List<UserInteraction> findByUserIdAndTenantIdAndItemIdIn(String userId, String tenantId, List<String> itemIds);
    
    // Find users who interacted with a specific item
    @Query("{ 'itemId': ?0, 'tenantId': ?1 }")
    List<UserInteraction> findUsersByItemIdAndTenantId(String itemId, String tenantId);
}
