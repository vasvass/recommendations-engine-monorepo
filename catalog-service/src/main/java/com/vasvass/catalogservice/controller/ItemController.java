package com.vasvass.catalogservice.controller;

import com.vasvass.catalogservice.model.Item;
import com.vasvass.catalogservice.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllItems(
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false) String category) {
        return itemService.findAll(tenantId, category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        return itemService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable String id,
            @RequestBody Item itemDetails) {
        return ResponseEntity.ok(itemService.updateItem(id, itemDetails));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }
}
