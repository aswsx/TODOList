package ru.job4j.todo.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.persistent.TasksDBStore;

import java.util.Collection;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 06/04/2022 - 15:37
 */
@ThreadSafe
@Service
public class TasksService {
    private final TasksDBStore store;

    public TasksService(TasksDBStore store) {
        this.store = store;
    }

    public void addTask(Task task) {
        store.addTask(task);
    }

    public void updateTask(Task task) {
        store.updateTask(task);
    }

    public void deleteTask(int id) {
        store.deleteTask(id);
    }

    public void doneTask(int id) {
        store.doneTask(id);
    }

    public Collection<Task> findAll() {
        return store.findAll();
    }

    public Task findById(int id) {
        return store.findById(id);
    }

    public Collection<Task> findActiveTasks() {
        return store.findByCondition(false);
    }

    public Collection<Task> findDoneTasks() {
        return store.findByCondition(true);
    }
}
