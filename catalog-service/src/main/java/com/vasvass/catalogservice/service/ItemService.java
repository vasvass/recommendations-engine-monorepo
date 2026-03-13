package com.vasvass.catalogservice.service;

import com.vasvass.catalogservice.model.Item;
import com.vasvass.catalogservice.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll(String tenantId, String category) {
        if (tenantId != null && category != null) {
            return itemRepository.findByTenantIdAndCategory(tenantId, category);
        } else if (tenantId != null) {
            return itemRepository.findByTenantId(tenantId);
        } else if (category != null) {
            return itemRepository.findByCategory(category);
        }
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(String id, Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());
        item.setCategory(itemDetails.getCategory());
        item.setTags(itemDetails.getTags());
        item.setTenantId(itemDetails.getTenantId());
        item.setMetadata(itemDetails.getMetadata());
        return itemRepository.save(item);
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}
