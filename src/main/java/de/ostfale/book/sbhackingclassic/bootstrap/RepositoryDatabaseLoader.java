package de.ostfale.book.sbhackingclassic.bootstrap;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDatabaseLoader {

  /*  @Bean
    CommandLineRunner initializer(ItemRepository repository) {
        return args -> {
            repository.save(new Item("Alf - Alarm Clock", 19.99));
            repository.save(new Item("Smurf - TV Tray", 24.99));
        };
    }*/
}
