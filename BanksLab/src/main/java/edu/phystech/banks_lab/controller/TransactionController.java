package edu.phystech.banks_lab.controller;

import java.util.List;

import edu.phystech.banks_lab.controller.model.TransactionRequest;
import edu.phystech.banks_lab.model.Transaction;
import edu.phystech.banks_lab.repository.TransactionDao;
import edu.phystech.banks_lab.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class TransactionController {

    TransactionDao transactionDao;
    TransactionService transactionService;

    @DeleteMapping("{id}")
    public Transaction revokeTransaction(@PathVariable("id") int id) {
        return transactionService.revoke(id);
    }

    @GetMapping
    @ResponseBody
    public List<Transaction> getAll() {
        return transactionDao.findAllByIdIsNotNull();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Transaction getTransaction(@PathVariable("id") int id) {
        return transactionDao.getById(id);
    }

    @PostMapping
    @ResponseBody
    public Transaction makeTransaction(@RequestBody TransactionRequest request) {
        return transactionService.makeTransaction(request);
    }
}
