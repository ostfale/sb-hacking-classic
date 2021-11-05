package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import de.ostfale.book.sbhackingclassic.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;
    private final CartService cartService;

    public HomeController(ItemRepository itemRepository, CartService cartService) {
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }

    @GetMapping
    String home(Model model) {
        model.addAttribute("items", this.itemRepository.findAll());
        model.addAttribute("cart", cartService.getCartById("My Cart"));
        return "home";
    }

    @PostMapping("/add/{id}")
    String addToCart(@PathVariable Integer id) {
        cartService.addToCart("My Cart", id);
        return "redirect:/";
    }
}
