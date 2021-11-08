package de.ostfale.book.sbhackingclassic.repositories;

import de.ostfale.book.sbhackingclassic.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findByNameContaining(String partialName);

    List<Item> findByNameContainingIgnoringCase(String partialName);

    List<Item> findByDescriptionContainingIgnoreCase(String partialName);

    List<Item> findByNameContainingAndDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

    List<Item> findByNameContainingOrDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

    @Query("select i from Item i where i.name = ?1 and i.price = ?2")
    List<Item> findItemsForCustomerMonthlyReport(String name, int price);

    @Query("select i from Item i order by i.price")
    List<Item> findSortedStuffForWeeklyReport();
}
