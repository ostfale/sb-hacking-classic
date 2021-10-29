package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Dish;
import de.ostfale.book.sbhackingclassic.service.KitchenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ServerController {

    private final KitchenService kitchenService;

    public ServerController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/server")
    List<Dish> serveDishes() {
        return this.kitchenService.getDishes();
    }

    @GetMapping("/served-dishes")
    List<Dish> deliverDishes() {
        return this.kitchenService.getDishes()
                .stream()
                .map(Dish::deliver)
                .collect(Collectors.toList());
    }

}
