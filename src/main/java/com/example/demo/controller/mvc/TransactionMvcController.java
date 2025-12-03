package com.example.demo.controller.mvc;

import com.example.demo.dto.TransactionCreateRequest;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.entity.TransactionType;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/transactions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
public class TransactionMvcController {
    
    private final TransactionService transactionService;
    
    @GetMapping
    public String listTransactions(Model model) {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions/list";
    }
    
    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        return "transactions/view";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transaction", new TransactionCreateRequest());
        model.addAttribute("types", TransactionType.values());
        model.addAttribute("statuses", TransactionStatus.values());
        return "transactions/form";
    }
    
    @PostMapping
    public String createTransaction(@ModelAttribute TransactionCreateRequest request) {
        transactionService.createTransaction(request);
        return "redirect:/admin/transactions";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", TransactionType.values());
        model.addAttribute("statuses", TransactionStatus.values());
        return "transactions/form";
    }
    
    @PostMapping("/{id}/status")
    public String updateTransactionStatus(@PathVariable Long id, @RequestParam TransactionStatus status) {
        transactionService.updateTransactionStatus(id, status);
        return "redirect:/admin/transactions/" + id;
    }
    
    @PostMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/admin/transactions";
    }
    
    @GetMapping("/subscriber/{subscriberId}")
    public String listTransactionsBySubscriber(@PathVariable Long subscriberId, Model model) {
        List<TransactionDTO> transactions = transactionService.getTransactionsBySubscriber(subscriberId);
        model.addAttribute("transactions", transactions);
        model.addAttribute("subscriberId", subscriberId);
        return "transactions/list";
    }
    
    @GetMapping("/merchant/{merchantId}")
    public String listTransactionsByMerchant(@PathVariable Long merchantId, Model model) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByMerchant(merchantId);
        model.addAttribute("transactions", transactions);
        model.addAttribute("merchantId", merchantId);
        return "transactions/list";
    }
}
