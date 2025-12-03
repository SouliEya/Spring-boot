package com.example.demo.controller.mvc;

import com.example.demo.dto.SubscriberCreateRequest;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.entity.SubscriberStatus;
import com.example.demo.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/subscribers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
public class SubscriberMvcController {
    
    private final SubscriberService subscriberService;
    
    @GetMapping
    public String listSubscribers(Model model) {
        List<SubscriberDTO> subscribers = subscriberService.getAllSubscribers();
        model.addAttribute("subscribers", subscribers);
        return "subscribers/list";
    }
    
    @GetMapping("/{id}")
    public String viewSubscriber(@PathVariable Long id, Model model) {
        SubscriberDTO subscriber = subscriberService.getSubscriberById(id);
        model.addAttribute("subscriber", subscriber);
        return "subscribers/view";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("subscriber", new SubscriberCreateRequest());
        model.addAttribute("statuses", SubscriberStatus.values());
        return "subscribers/form";
    }
    
    @PostMapping
    public String createSubscriber(@ModelAttribute SubscriberCreateRequest request) {
        subscriberService.createSubscriber(request);
        return "redirect:/admin/subscribers";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        SubscriberDTO subscriber = subscriberService.getSubscriberById(id);
        model.addAttribute("subscriber", subscriber);
        model.addAttribute("statuses", SubscriberStatus.values());
        return "subscribers/form";
    }
    
    @PostMapping("/{id}")
    public String updateSubscriber(@PathVariable Long id, @ModelAttribute SubscriberCreateRequest request) {
        subscriberService.updateSubscriber(id, request);
        return "redirect:/admin/subscribers";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return "redirect:/admin/subscribers";
    }
    
    @PostMapping("/{id}/status")
    public String updateSubscriberStatus(@PathVariable Long id, @RequestParam SubscriberStatus status) {
        subscriberService.updateSubscriberStatus(id, status);
        return "redirect:/admin/subscribers/" + id;
    }
    
    @PostMapping("/{id}/balance")
    public String updateAccountBalance(@PathVariable Long id, @RequestParam Double amount) {
        subscriberService.updateAccountBalance(id, amount);
        return "redirect:/admin/subscribers/" + id;
    }
}
