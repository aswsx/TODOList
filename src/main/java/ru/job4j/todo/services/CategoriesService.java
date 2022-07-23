package ru.job4j.todo.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.persistent.CategoriesDBStore;

import java.util.Collection;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 13/07/2022 - 21:11
 */
@ThreadSafe
@Service
public class CategoriesService {
    private final CategoriesDBStore store;

    public CategoriesService(CategoriesDBStore store) {
        this.store = store;
    }

    public Collection<Category> findAll() {
        return store.findAll();
    }

    public Category findById(int id) {
        return store.findById(id);
    }

    public void addCategory(Category category) {
        store.addCategory(category);
    }
}
