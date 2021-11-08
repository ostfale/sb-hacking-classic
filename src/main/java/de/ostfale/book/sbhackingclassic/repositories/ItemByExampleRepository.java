package de.ostfale.book.sbhackingclassic.repositories;

import de.ostfale.book.sbhackingclassic.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ItemByExampleRepository extends CrudRepository<Item, Integer>, QueryByExampleExecutor<Item> {
}
