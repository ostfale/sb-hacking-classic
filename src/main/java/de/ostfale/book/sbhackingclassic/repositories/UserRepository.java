package de.ostfale.book.sbhackingclassic.repositories;

import de.ostfale.book.sbhackingclassic.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByName(String name);
}
