package de.ostfale.book.sbhackingclassic.repositories;

import de.ostfale.book.sbhackingclassic.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, String> {
}
