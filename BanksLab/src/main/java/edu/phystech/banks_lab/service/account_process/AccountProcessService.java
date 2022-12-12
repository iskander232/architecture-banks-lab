package edu.phystech.banks_lab.service.account_process;

import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Bank;

public interface AccountProcessService {
    Account addMoney(int amount, Account account, Bank bank);
    Account substractMoney(int amount, Account account, Bank bank);
    Account accountMonthlyUpdate(Account account, Bank bank);
}
