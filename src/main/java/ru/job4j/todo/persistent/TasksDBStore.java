package ru.job4j.todo.persistent;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.models.Task;

import java.util.Collection;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 06/04/2022 - 15:36
 */
@Repository
@ThreadSafe
public class TasksDBStore implements DBStore {
    private final SessionFactory sf;

    public TasksDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public void addTask(Task task) {
        tx(session -> {
            session.save(task);
            return new Object();
        }, sf);
    }

    public void updateTask(Task task) {
        tx(session -> {
            session.update(task);
            return new Object();
        }, sf);
    }

    public void deleteTask(int id) {
        Task task = new Task();
        task.setId(id);
        tx(session -> {
            session.delete(task);
            return new Object();
        }, sf);
    }

    public void doneTask(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = session.get(Task.class, id);
        task.setDone(true);
        session.getTransaction().commit();
        session.close();
    }

    public Collection<Task> findAll() {
        return tx(session -> session.createQuery(
                "from ru.job4j.todo.models.Task").list(), sf);
    }

    public Task findById(int id) {
        return tx(session -> session.get(Task.class, id), sf);
    }

    public Collection<Task> findByCondition(boolean condition) {
        return tx(session -> session
                .createQuery("from ru.job4j.todo.models.Task where done = :condition")
                .setParameter("condition", condition).list(), sf);
    }
}
