package de.ostfale.book.sbhackingclassic.service;

import de.ostfale.book.sbhackingclassic.model.Cart;
import de.ostfale.book.sbhackingclassic.model.CartItem;
import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.CartRepository;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Cart getCartById(String anId) {
        return this.cartRepository.findById(anId).orElseGet(() -> new Cart("My Cart"));
    }

    public Cart addToCart(String cartId, Integer itemId) {
        Cart cart = this.cartRepository.findById(cartId).orElseGet(() -> new Cart(cartId));
        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .map(cartItem -> {
                    cartItem.increment();
                    return cart;
                })
                .orElseGet(() -> {
                    Item item = this.itemRepository.findById(itemId)
                            .orElseThrow(() -> new IllegalStateException("Can't seem to find Item type " + itemId));
                    cart.getCartItems().add(new CartItem(item, cart));
                    return cart;
                });

        return this.cartRepository.save(cart);
    }
}
