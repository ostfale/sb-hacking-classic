package de.ostfale.book.sbhackingclassic.repositories;

import de.ostfale.book.sbhackingclassic.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
