package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import de.ostfale.book.sbhackingclassic.service.CartService;
import de.ostfale.book.sbhackingclassic.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final InventoryService inventoryService;


    public HomeController(ItemRepository itemRepository, CartService cartService, InventoryService inventoryService) {
        this.itemRepository = itemRepository;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
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

    @GetMapping("/search")
    String search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd,
            Model model) {
        model.addAttribute("results", inventoryService.searchByExample(name, description, useAnd));
        return "home";
    }

}
