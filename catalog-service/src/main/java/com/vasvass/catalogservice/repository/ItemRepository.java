package com.vasvass.catalogservice.repository;

import com.vasvass.catalogservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findByTenantId(String tenantId);

    List<Item> findByCategory(String category);

    List<Item> findByTenantIdAndCategory(String tenantId, String category);
}
