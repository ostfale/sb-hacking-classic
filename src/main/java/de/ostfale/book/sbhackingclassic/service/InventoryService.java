package de.ostfale.book.sbhackingclassic.service;

import de.ostfale.book.sbhackingclassic.model.Cart;
import de.ostfale.book.sbhackingclassic.model.CartItem;
import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.CartRepository;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public InventoryService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Optional<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Iterable<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    public Item saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    public void deleteItem(Integer id) {
        this.itemRepository.deleteById(id);
    }

    public Cart addItemToCart(String cartId, Integer itemId) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseGet(() -> new Cart("My Cart"));

        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId)) //
                .findAny() //
                .map(cartItem -> {
                    cartItem.increment();
                    return cart;
                }) //
                .orElseGet(() -> {
                    Item item = this.itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("Can't seem to find Item type " + itemId));
                    cart.getCartItems().add(new CartItem(item, cart));
                    return cart;
                });

        return this.cartRepository.save(cart);
    }

    public Cart removeOneFromCart(String cartId, Integer itemId) {

        Cart cart = this.cartRepository.findById("My Cart").orElseGet(() -> new Cart("My Cart"));

        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId)) //
                .findAny()
                .ifPresent(CartItem::decrement);

        List<CartItem> updatedCartItems = cart.getCartItems().stream() //
                .filter(cartItem -> cartItem.getQuantity() > 0) //
                .collect(Collectors.toList());

        cart.setCartItems(updatedCartItems);
        return this.cartRepository.save(cart);
    }
}
