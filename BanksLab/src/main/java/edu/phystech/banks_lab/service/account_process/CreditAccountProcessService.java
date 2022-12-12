package edu.phystech.banks_lab.service.account_process;

import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Bank;
import edu.phystech.banks_lab.service.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditAccountProcessService implements AccountProcessService {

    TimeService timeService;

    @Override
    public Account addMoney(int amount, Account account, Bank bank) {
        account.setAccountValue(account.getAccountValue() + amount);

        if (account.getAccountValue() >= 0) {
            account.setUpdateTime(timeService.getCurrentTime());
        }

        return account;
    }

    @Override
    public Account substractMoney(int amount, Account account, Bank bank) {
        if (account.getAccountValue() - amount < bank.getCreditLimit()) {
            throw new BusinessException(BusinessException.Reason.TOO_LITTLE_MONEY);
        } else {
            if (account.getAccountValue() >= 0 && account.getAccountValue() - amount < 0) {
                account.setUpdateTime(timeService.getCurrentTime());
            }
            account.setAccountValue(account.getAccountValue() - amount);
        }

        return account;
    }

    @Override
    public Account accountMonthlyUpdate(Account account, Bank bank) {
        int diffMonths = timeService.diffMonths(account.getUpdateTime());
        if (diffMonths > 0) {
            account.setUpdateTime(timeService.getCurrentTime());
            account.setAccountValue(account.getAccountValue() - diffMonths * bank.getCreditCommission());
        }

        return account;
    }
}
