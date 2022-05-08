package ru.job4j.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.TasksService;

import javax.servlet.http.HttpSession;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 07/05/2022 - 19:40
 */
@Controller
public class IndexController {
    private final TasksService tasksService;

    public IndexController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("/index")
    public String todo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Guest");
        }
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasksService.findAll());
        return "index";
    }
}
