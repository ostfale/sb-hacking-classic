package de.ostfale.book.sbhackingclassic.service;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemByExampleRepository;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final ItemByExampleRepository itemByExampleRepository;

    public InventoryService(ItemRepository itemRepository, ItemByExampleRepository itemByExampleRepository) {
        this.itemRepository = itemRepository;
        this.itemByExampleRepository = itemByExampleRepository;
    }

    List<Item> getItems() {
        // imagine calling a remote service!
        return Collections.emptyList();
    }


    public Iterable<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");  // ignore price field

        Example<Item> probe = Example.of(item, matcher);
        return itemByExampleRepository.findAll(probe);
    }

    // old and much more chatty implementation of the search above
    public Iterable<Item> search(String partialName, String partialDescription, boolean useAnd) {
        if (partialName != null) {
            if (partialDescription != null) {
                if (useAnd) {
                    return itemRepository
                            .findByNameContainingAndDescriptionContainingAllIgnoreCase(partialName, partialDescription);
                } else {
                    return itemRepository.findByNameContainingOrDescriptionContainingAllIgnoreCase(
                            partialName, partialDescription);
                }
            } else {
                return itemRepository.findByNameContaining(partialName);
            }
        } else {
            if (partialDescription != null) {
                return itemRepository.findByDescriptionContainingIgnoreCase(partialDescription);
            } else {
                return itemRepository.findAll();
            }
        }
    }
}
