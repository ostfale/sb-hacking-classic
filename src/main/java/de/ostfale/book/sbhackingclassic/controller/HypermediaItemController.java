package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HypermediaItemController {

    private final ItemRepository itemRepository;

    public HypermediaItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/hypermedia/items/{id}")
    EntityModel<Item> findOne(@PathVariable Integer id) {
        HypermediaItemController controller = methodOn(HypermediaItemController.class);
        Link selfLink = linkTo(controller.findOne(id)).withSelfRel();
        Link aggregateLink = linkTo(controller.findAll())
                .withRel(IanaLinkRelations.ITEM);

        return this.itemRepository.findById(id)
                .map(item -> EntityModel.of(item, selfLink, aggregateLink))
                .orElseThrow(() -> new IllegalStateException("Couldn't find item " + id));
    }

    @GetMapping("/hypermedia/items")
    CollectionModel<EntityModel<Item>> findAll() {
        List<EntityModel<Item>> entityModels = StreamSupport.stream(this.itemRepository.findAll().spliterator(), false)
                .map(item -> findOne(item.getId()))
                .collect(Collectors.toList());

        return CollectionModel.of(
                entityModels,
                linkTo(methodOn(HypermediaItemController.class).findAll()).withSelfRel());
    }
}
