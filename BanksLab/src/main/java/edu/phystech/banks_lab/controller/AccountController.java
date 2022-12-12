package edu.phystech.banks_lab.controller;

import java.util.Collection;

import edu.phystech.banks_lab.controller.model.CreateAccountRequest;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.repository.AccountDao;
import edu.phystech.banks_lab.service.AccountService;
import edu.phystech.banks_lab.service.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountController {

    AccountDao accountDao;

    AccountService accountService;
    TimeService timeService;

    @PostMapping
    @ResponseBody
    public Account create(@RequestBody CreateAccountRequest request) {
        return accountDao.save(Account.builder()
                        .bankId(request.getBankId())
                        .clientId(request.getClientId())
                        .type(request.getType())
                        .accountValue(0)
                        .depositCanTransferDate(request.getDepositCanTransferDate())
                        .updateTime(timeService.getCurrentTime())
                        .bufferedValue(0)
                        .bufferedCommissionStartDate(timeService.getCurrentTime())
                .build());
    }

    @GetMapping
    @ResponseBody
    public Collection<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Account get(@PathVariable int id) {
        return accountService.get(id);
    }

    @GetMapping("withdraw/{id}/{amount}")
    @ResponseBody
    public Account withdraw(@PathVariable int amount, @PathVariable int id) {
        return accountService.withdraw(id, amount);
    }

    @GetMapping("replenish/{id}/{amount}")
    public Account replenish(@PathVariable int amount, @PathVariable int id) {
        return accountService.replanish(id, amount);
    }
}
