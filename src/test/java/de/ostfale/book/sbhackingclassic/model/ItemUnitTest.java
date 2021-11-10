package de.ostfale.book.sbhackingclassic.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Item entity")
class ItemUnitTest {

    @Test
    @DisplayName("Run test methods using AssertJ")
    void itemBasicShouldWork() {
        // given
        var newItem = new Item(1, "TV-Tray", "Alf TV Tray", 19.99);
        // then
        assertThat(newItem.getId()).isEqualTo(1);
        assertThat(newItem.getName()).isEqualTo("TV-Tray");
        assertThat(newItem.getPrice()).isEqualTo(19.99);
    }
}