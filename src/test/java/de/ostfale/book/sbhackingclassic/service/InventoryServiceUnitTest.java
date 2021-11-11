package de.ostfale.book.sbhackingclassic.service;

import de.ostfale.book.sbhackingclassic.model.Cart;
import de.ostfale.book.sbhackingclassic.model.CartItem;
import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.CartRepository;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) // custom test handler
@DisplayName("Unit test with some collaborator")
class InventoryServiceUnitTest {

    private InventoryService inventoryService;

    @MockBean // spring boot test annotation -> mockito based
    private ItemRepository itemRepository;

    @MockBean
    private CartRepository cartRepository;

    private Item sampleItem;

    @BeforeEach
    void setUp() {
        // define test data
        sampleItem = new Item(1, "TV Tray", "Alf TV Tray", 19.99);
        CartItem cartItem = new CartItem(sampleItem, null);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(cartItem));
        cartItem.setCart(sampleCart);

        // define mock interactions
        when(cartRepository.findById(anyString())).thenReturn(Optional.empty());
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(sampleCart);
        inventoryService = new InventoryService(itemRepository, cartRepository);
    }

    @Test
    @DisplayName("Add item to an empty cart")
    void addItemToEmptyCartShouldProduceOneCartItem() {
        // when
        Cart cart = inventoryService.addItemToCart("My Cart", 1);
        // then
        assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                .containsExactlyInAnyOrder(1);
        assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                .containsExactly(sampleItem);
    }
}