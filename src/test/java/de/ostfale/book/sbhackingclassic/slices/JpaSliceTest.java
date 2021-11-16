package de.ostfale.book.sbhackingclassic.slices;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Slice test with DataJpa")
public class JpaSliceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Test saving items with ItemRepository")
    void itemRepositorySavesItems() {
        // given
        var sampleItem = new Item("name", "description", 1.99);
        // when
        var savedItem = itemRepository.save(sampleItem);
        // then
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("name");
        assertThat(savedItem.getDescription()).isEqualTo("description");
        assertThat(savedItem.getPrice()).isEqualTo(1.99);
    }
}
