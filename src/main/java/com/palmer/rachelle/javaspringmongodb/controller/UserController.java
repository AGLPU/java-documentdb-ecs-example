package com.palmer.rachelle.javaspringmongodb.controller;

import com.palmer.rachelle.javaspringmongodb.model.User;
import com.palmer.rachelle.javaspringmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Handles listing of all users and searching.
     * @param model
     * @return
     */
    @GetMapping({"/index", "/search"})
    public String showUsers(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("users",
                this.userRepository.findByEmailStartsWithOrName(keyword, keyword));
            model.addAttribute("keyword", keyword);
        } else {
            List<User> users = this.userRepository.findAll();
            model.addAttribute("users", users);
        }
        return "index";
    }

    /**
     * Show form for adding a user document
     * @param user
     * @return
     */
    @GetMapping("/add-user")
    public String showAddUserForm(User user) {
        return "add-user";
    }

    /**
     * This will CREATE a users. C of CRUD.
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/add-user")
    public String addUser(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        this.userRepository.save(user);
        return "redirect:/user/index";
    }

    /**
     * Show form for editing a user document. The R in CRUD.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit-user/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return "edit-user";
    }

    /**
     * This will update a user document. The U in CRUD
     * @param id
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/edit-user/{id}")
    public String updateUser(@PathVariable("id") String id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "edit-user";
        }

        this.userRepository.save(user);

        return "redirect:/user/index";
    }

    /**
     * This will delete a user document. The D in CRUD.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        this.userRepository.delete(user);

        return "redirect:/user/index";
    }
}
