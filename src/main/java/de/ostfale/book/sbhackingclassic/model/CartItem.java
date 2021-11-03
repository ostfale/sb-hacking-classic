package de.ostfale.book.sbhackingclassic.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CartItem {

    @Id
    @GeneratedValue
    private Integer id;

     @ManyToOne(fetch = FetchType.LAZY)
     private Cart cart;

     @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int quantity;

    protected CartItem() {
    }

    public CartItem(Cart cart, Item item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id.equals(cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
