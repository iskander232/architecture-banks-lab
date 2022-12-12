package edu.phystech.banks_lab.service;

import java.util.List;

import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Bank;
import edu.phystech.banks_lab.model.Client;
import edu.phystech.banks_lab.repository.AccountDao;
import edu.phystech.banks_lab.repository.BankDao;
import edu.phystech.banks_lab.repository.ClientDao;
import edu.phystech.banks_lab.service.account_process.AccountProcessServiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    AccountDao accountDao;
    BankDao bankDao;
    ClientDao clientDao;

    TimeService timeService;
    AccountProcessServiceFactory accountProcessServiceFactory;

    public Account withdraw(int id, int amount) {
        Account account = get(id);
        Bank bank = bankDao.getById(account.getBankId());
        Client client = clientDao.getById(account.getClientId());

        if ((client.getPassportNumber() == null || client.getAddress() == null) && amount > bank.getUnauthorizedLimit()) {
            throw new BusinessException(BusinessException.Reason.NO_TRUST_CLIENT);
        }

        Account withdrawn = accountProcessServiceFactory
                .getAccountProcessService(account.getType())
                .substractMoney(amount, account, bank);

        return accountDao.save(withdrawn);
    }

    public Account replanish(int id, int amount) {
        Account account = get(id);
        Bank bank = bankDao.getById(account.getBankId());

        Client client = clientDao.getById(account.getClientId());

        if ((client.getPassportNumber() == null || client.getAddress() == null) && amount > bank.getUnauthorizedLimit()) {
            throw new BusinessException(BusinessException.Reason.NO_TRUST_CLIENT);
        }

        Account replanished = accountProcessServiceFactory
                .getAccountProcessService(account.getType())
                .addMoney(amount, account, bank);

        return accountDao.save(replanished);
    }

    public Account get(int id) {
        Account account = accountDao.getById(id);
        Bank bank = bankDao.getById(account.getBankId());
        account = accountProcessServiceFactory.getAccountProcessService(account.getType())
                .accountMonthlyUpdate(account, bank);
        accountDao.save(account);

        return account;
    }

    public List<Account> getAll() {
        accountDao.findAllByIdIsNotNull()
                .forEach(a -> {
                    Bank bank = bankDao.getById(a.getBankId());
                    a = accountProcessServiceFactory.getAccountProcessService(a.getType())
                            .accountMonthlyUpdate(a, bank);
                    accountDao.save(a);
                });

        return accountDao.findAllByIdIsNotNull();
    }
}
