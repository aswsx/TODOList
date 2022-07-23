package ru.job4j.todo.persistent;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.models.User;

import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 07/05/2022 - 15:52
 */
@Repository
public class UsersDBStore implements DBStore {
    private final SessionFactory sf;

    public UsersDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> addUser(User user) {
        try {
            tx(session -> session.save(user), sf);
        } catch (Exception ex) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findUser(String email, String password) {
        return tx(session -> session.createQuery(
                "from User where email = :email and password = :password")
                .setParameter("email", email)
                .setParameter("password", password).uniqueResultOptional(), sf);
    }
}
