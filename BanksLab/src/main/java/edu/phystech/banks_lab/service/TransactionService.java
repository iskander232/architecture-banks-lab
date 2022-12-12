package edu.phystech.banks_lab.service;

import edu.phystech.banks_lab.controller.model.TransactionRequest;
import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Transaction;
import edu.phystech.banks_lab.repository.AccountDao;
import edu.phystech.banks_lab.repository.TransactionDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    AccountService accountService;
    TimeService timeService;

    AccountDao accountDao;
    TransactionDao transactionDao;

    public Transaction makeTransaction(TransactionRequest transactionRequest) {
        Account from = accountService.get(transactionRequest.getFromAccount());
        Account to = accountService.get(transactionRequest.getToAccount());

        try {
            accountService.withdraw(from.getId(), transactionRequest.getValue());
            accountService.replanish(from.getId(), transactionRequest.getValue());
        } catch (BusinessException e) {
            accountDao.save(from);
            accountDao.save(to);

            throw e;
        }

        return transactionDao.save(Transaction.builder()
                        .fromAccount(from.getId())
                        .toAccount(to.getId())
                        .transactionValue(transactionRequest.getValue())
                        .status("active")
                        .timestamp(timeService.getCurrentTime())
                .build());
    }

    public Transaction revoke(int id) {
        Transaction transaction = transactionDao.getById(id);

        Account from = accountService.get(transaction.getFromAccount());
        Account to = accountService.get(transaction.getToAccount());

        from.setAccountValue(from.getAccountValue() + transaction.getTransactionValue());
        to.setAccountValue(to.getAccountValue() - transaction.getTransactionValue());

        accountDao.save(from);
        accountDao.save(to);

        transaction.setStatus("revoked");

        return transactionDao.save(transaction);
    }
}
