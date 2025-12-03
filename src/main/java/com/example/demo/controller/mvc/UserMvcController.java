package com.example.demo.controller.mvc;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserRole;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserMvcController {
    
    private final UserService userService;
    
    @GetMapping
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }
    
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/view";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserCreateRequest());
        model.addAttribute("roles", UserRole.values());
        return "users/form";
    }
    
    @PostMapping
    public String createUser(@ModelAttribute UserCreateRequest request) {
        userService.createUser(request);
        return "redirect:/admin/users";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "users/form";
    }
    
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserCreateRequest request) {
        userService.updateUser(id, request);
        return "redirect:/admin/users";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    
    @PostMapping("/{id}/activate")
    public String activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return "redirect:/admin/users";
    }
    
    @PostMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return "redirect:/admin/users";
    }
}
