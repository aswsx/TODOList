package ru.job4j.todo.persistent;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.models.Category;

import java.util.Collection;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 13/07/2022 - 21:15
 */
@Repository
public class CategoriesDBStore implements DBStore {
    private final SessionFactory sf;

    public CategoriesDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Collection<Category> findAll() {
        return tx(session -> session
                .createQuery("from Category")
                .list(), sf
        );
    }

    public Category findById(int id) {
        return tx(session -> (Category) session
                .createQuery("from Category as c where c.id = :fId")
                .setParameter("fId", id)
                .uniqueResult(), sf
        );
    }

    public void addCategory(Category category) {
        tx(session -> {
                    session.save(category);
                    return new Object();
                }, sf
        );
    }
}
