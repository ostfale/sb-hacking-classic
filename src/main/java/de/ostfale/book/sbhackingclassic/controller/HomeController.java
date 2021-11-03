package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Cart;
import de.ostfale.book.sbhackingclassic.repositories.CartRepository;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    String home(Model model) {
        model.addAttribute("items", this.itemRepository.findAll());
        model.addAttribute("cart", this.cartRepository.findById("My Cart").orElseGet(() -> new Cart("My Cart")));
        return "home";
    }
}
