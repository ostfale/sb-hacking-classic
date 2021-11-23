package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class ApiItemController {

    private final ItemRepository itemRepository;

    public ApiItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/items")
    public Iterable<Item> findAll() {
        return this.itemRepository.findAll();
    }

    @GetMapping("/api/items/{id}")
    public Optional<Item> findById(@PathVariable Integer id) {
        return this.itemRepository.findById(id);
    }

    @PostMapping("/api/items")
    ResponseEntity<?> addNewItem(@RequestBody Item item) {
        Item savedItem = this.itemRepository.save(item);
        return ResponseEntity
                .created(URI.create("/api/items/" + savedItem.getId()))
                .body(savedItem);
    }

    @PutMapping("/api/items/{id}")
    public ResponseEntity<?> updateItem(@RequestBody Item item, @PathVariable Integer id) {
        Item newItem = new Item(id, item.getName(), item.getDescription(), item.getPrice());
        this.itemRepository.save(newItem);
        return ResponseEntity.created(URI.create("/api/items/" + id)).build();
    }
}
