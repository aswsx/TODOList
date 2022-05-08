package ru.job4j.todo.persistent;

import org.hibernate.Session;
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
public class UsersDBStore {
    private final SessionFactory sf;

    public UsersDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> addUser(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }

    public Optional<User> findUser(String email, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> ou = session.createQuery(
                "from ru.job4j.todo.models.User where email = :email and password = :password")
                .setParameter("email", email)
                .setParameter("password", password).uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return ou;
    }
}
