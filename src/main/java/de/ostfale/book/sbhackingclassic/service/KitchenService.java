package de.ostfale.book.sbhackingclassic.service;

import de.ostfale.book.sbhackingclassic.model.Dish;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class KitchenService {

    private final Random picker = new Random();

    public List<Dish> getDishes() {
        return List.of(randomDish());
    }

    private final List<Dish> menu = Arrays.asList(
            new Dish("Sesame Chicken"),
            new Dish("Lo mein noodles, plain"),
            new Dish("Sweet & Sour Beef")
    );

    private Dish randomDish() {
        return this.menu.get(picker.nextInt(3));
    }
}
