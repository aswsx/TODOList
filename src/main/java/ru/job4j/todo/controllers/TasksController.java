package ru.job4j.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.CategoriesService;
import ru.job4j.todo.services.TasksService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 06/04/2022 - 15:36
 */
@Controller
public class TasksController {
    private static final String START_PAGE = "redirect:/allTasks";
    private static final String DESCRIPTION = "redirect:/description/";
    private static final String TASKS = "tasks";
    private static final String GUEST = "Гость";
    private final TasksService tasksService;
    private final CategoriesService categoriesService;

    public TasksController(TasksService tasksService, CategoriesService categoriesService) {
        this.tasksService = tasksService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/allTasks")
    public String tasks(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute(TASKS, tasksService.findAll());
        return "task/allTasks";
    }

    @GetMapping("/showActive")
    public String showActive(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute(TASKS, tasksService.findActiveTasks());
        return "/task/showActive";
    }

    @GetMapping("/showDone")
    public String showDone(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute(TASKS, tasksService.findDoneTasks());
        return "/task/showDone";
    }

    @GetMapping("/formAddTask")
    public String formAddTask(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("categories", categoriesService.findAll());
        return "task/addTask";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, HttpSession session,
                          @RequestParam(value = "categoriesId", required = false) List<Integer> categoriesId) {
        task.setCreated(new Date(System.currentTimeMillis()));
        task.setUser((User) session.getAttribute("user"));
        categoriesId.forEach(id -> task.addCategory(categoriesService.findById(id)));
        tasksService.addTask(task);
        return START_PAGE;
    }

    @GetMapping("/description/{taskId}")
    public String taskDescription(Model model, @PathVariable("taskId") int id, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("task", tasksService.findById(id));
        return "task/description";
    }

    @GetMapping("/formEditTask/{taskId}")
    public String formEditTask(Model model, @PathVariable("taskId") int id, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("task", tasksService.findById(id));
        model.addAttribute("categories", categoriesService.findAll());
        return "task/editTask";
    }

    @PatchMapping("/editTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam(value = "categoriesId", required = false)
            List<Integer> categoriesId) {
        Task updTask = tasksService.findById(task.getId());
        task.setCreated(updTask.getCreated());
        task.setName(updTask.getName());
        task.setUser(updTask.getUser());
        categoriesId.forEach(id -> task.addCategory(categoriesService.findById(id)));
        tasksService.updateTask(task);
        return DESCRIPTION + task.getId();
    }

    @GetMapping("/doneTask/{taskId}")
    public String doneTask(@PathVariable("taskId") int id) {
        tasksService.doneTask(id);
        return DESCRIPTION + id;
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        tasksService.deleteTask(id);
        return START_PAGE;
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail(GUEST);
        }
        return user;
    }
}
