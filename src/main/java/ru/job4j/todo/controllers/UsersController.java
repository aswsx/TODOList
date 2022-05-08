package ru.job4j.todo.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 07/05/2022 - 11:49
 */
@ThreadSafe
@Controller
public class UsersController {
    private final UsersService usersService;
    private static final String GUEST = "Гость";

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail(GUEST);
        }
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        return "user/addUser";
    }

    @PostMapping("/addUser")
    public String addUser(Model model, @ModelAttribute User user) {
        Optional<User> regUser = usersService.addUser(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail(GUEST);
        }
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = usersService.findUser(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
