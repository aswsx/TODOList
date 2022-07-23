package ru.job4j.todo.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.models.User;
import ru.job4j.todo.persistent.UsersDBStore;

import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 07/05/2022 - 11:52
 */
@ThreadSafe
@Service
public class UsersService {
    private final UsersDBStore store;

    public UsersService(UsersDBStore store) {
        this.store = store;
    }

    public Optional<User> addUser(User user) {
        return store.addUser(user);
    }

    public Optional<User> findUser(String email, String password) {
        return store.findUser(email, password);
    }
}
