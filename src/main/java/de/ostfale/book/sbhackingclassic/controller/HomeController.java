package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Cart;
import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final InventoryService inventoryService;

    public HomeController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    String home(Model model) {
        model.addAttribute("items", this.inventoryService.getInventory());
        model.addAttribute("cart", this.inventoryService.getCart("My Cart") //
                .orElseGet(() -> new Cart("My Cart")));
        return "home";
    }

    @PostMapping("/add/{id}")
    String addToCart(@PathVariable Integer id) {
        this.inventoryService.addItemToCart("My Cart", id);
        return "redirect:/";
    }

    @DeleteMapping("/remove/{id}")
    String removeFromCart(@PathVariable Integer id) {
        this.inventoryService.removeOneFromCart("My Cart", id);
        return "redirect:/";
    }

    @PostMapping
    String createItem(@RequestBody Item newItem) {
        this.inventoryService.saveItem(newItem);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    String deleteItem(@PathVariable Integer id) {
        this.inventoryService.deleteItem(id);
        return "redirect:/";
    }
}
