package com.example.demo.controller.mvc;

import com.example.demo.dto.MerchantCreateRequest;
import com.example.demo.dto.MerchantDTO;
import com.example.demo.entity.MerchantStatus;
import com.example.demo.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/merchants")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
public class MerchantMvcController {
    
    private final MerchantService merchantService;
    
    @GetMapping
    public String listMerchants(Model model) {
        List<MerchantDTO> merchants = merchantService.getAllMerchants();
        model.addAttribute("merchants", merchants);
        return "merchants/list";
    }
    
    @GetMapping("/{id}")
    public String viewMerchant(@PathVariable Long id, Model model) {
        MerchantDTO merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant", merchant);
        return "merchants/view";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("merchant", new MerchantCreateRequest());
        model.addAttribute("statuses", MerchantStatus.values());
        return "merchants/form";
    }
    
    @PostMapping
    public String createMerchant(@ModelAttribute MerchantCreateRequest request) {
        merchantService.createMerchant(request);
        return "redirect:/admin/merchants";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        MerchantDTO merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant", merchant);
        model.addAttribute("statuses", MerchantStatus.values());
        return "merchants/form";
    }
    
    @PostMapping("/{id}")
    public String updateMerchant(@PathVariable Long id, @ModelAttribute MerchantCreateRequest request) {
        merchantService.updateMerchant(id, request);
        return "redirect:/admin/merchants";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return "redirect:/admin/merchants";
    }
    
    @PostMapping("/{id}/status")
    public String updateMerchantStatus(@PathVariable Long id, @RequestParam MerchantStatus status) {
        merchantService.updateMerchantStatus(id, status);
        return "redirect:/admin/merchants/" + id;
    }
    
    @PostMapping("/{id}/balance")
    public String updateAccountBalance(@PathVariable Long id, @RequestParam Double amount) {
        merchantService.updateAccountBalance(id, amount);
        return "redirect:/admin/merchants/" + id;
    }
}
