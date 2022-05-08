package ru.job4j.todo.persistent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;
import ru.job4j.todo.models.Task;

import java.util.Collection;
import java.util.List;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 06/04/2022 - 15:36
 */
@Repository
public class TasksDBStore {
    private final SessionFactory sf;

    public TasksDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public void addTask(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
    }

    public void updateTask(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteTask(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = new Task();
        task.setId(id);
        session.delete(task);
        session.getTransaction().commit();
        session.close();
    }

    public void doneTask(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update ru.job4j.todo.models.Task t set t.done = true where t.id = :id")
                .setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Collection<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.models.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Collection<Task> findByCondition(boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery(
                "from ru.job4j.todo.models.Task where done = :condition")
                .setParameter("condition", condition).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
