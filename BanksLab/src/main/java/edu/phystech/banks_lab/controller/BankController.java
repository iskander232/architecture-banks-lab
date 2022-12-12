package edu.phystech.banks_lab.controller;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.phystech.banks_lab.controller.model.EditBankRequest;
import edu.phystech.banks_lab.model.Bank;
import edu.phystech.banks_lab.repository.BankDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("banks")
public class BankController {

    BankDao bankDao;

    ObjectWriter ow;

    public BankController(BankDao bankDao) {
        this.bankDao = bankDao;

        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GetMapping
    @ResponseBody
    public Collection<Bank> getAll() {
        return bankDao.findAllByIdIsNotNull();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Bank get(@PathVariable("id") int id) {
        try {
            return bankDao.getById(id);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @PostMapping("{id}")
    @ResponseBody
    public Bank edit(@PathVariable("id") int id, @RequestBody EditBankRequest request) throws JsonProcessingException {
        return bankDao.save(Bank.builder()
                .id(id)
                .debitCommission(request.getDebitCommission())
                .creditCommission(request.getCreditCommission())
                .depositValue(ow.writeValueAsString(request.getDepositValue()))
                .creditLimit(request.getCreditLimit())
                .precent(request.getPrecent())
                .unauthorizedLimit(request.getUnauthorizedLimit())
                .build());
    }

    @PostMapping
    @ResponseBody
    public Bank add(@RequestBody EditBankRequest request) throws JsonProcessingException {
        return bankDao.save(Bank.builder()
                .debitCommission(request.getDebitCommission())
                .creditCommission(request.getCreditCommission())
                .depositValue(ow.writeValueAsString(request.getDepositValue()))
                .creditLimit(request.getCreditLimit())
                .precent(request.getPrecent())
                .unauthorizedLimit(request.getUnauthorizedLimit())
                .build());
    }
}
