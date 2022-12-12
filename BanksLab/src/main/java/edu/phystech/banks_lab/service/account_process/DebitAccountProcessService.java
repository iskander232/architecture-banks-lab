package edu.phystech.banks_lab.service.account_process;

import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Bank;
import edu.phystech.banks_lab.service.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DebitAccountProcessService implements AccountProcessService {

    TimeService timeService;

    @Override
    public Account addMoney(int amount, Account account, Bank bank) {
        account.setAccountValue(account.getAccountValue() + amount);

        return account;
    }

    @Override
    public Account substractMoney(int amount, Account account, Bank bank) {
        if (account.getAccountValue() >= amount) {
            account.setAccountValue(account.getAccountValue() - amount);

            return account;
        } else {
            throw new BusinessException(BusinessException.Reason.CAN_NOT_TRANSFER_MONEY);
        }
    }

    @Override
    public Account accountMonthlyUpdate(Account account, Bank bank) {
        int diffMonths = timeService.diffMonths(account.getUpdateTime());

        account.setUpdateTime(timeService.getCurrentTime());
        if (account.getBufferedValue() == 0) {
            account.setBufferedCommissionStartDate(timeService.getCurrentTime());
        }

        account.setBufferedValue((int) (account.getBufferedValue() + diffMonths * bank.getPrecent() / 12.0));

        diffMonths = timeService.diffMonths(account.getBufferedCommissionStartDate());
        if (diffMonths >= 12) {
            account.setAccountValue(account.getAccountValue() + account.getBufferedValue());
            account.setBufferedValue((int) ((diffMonths % 12) * bank.getPrecent() / 12.0));
            if (account.getBufferedValue() > 0) {
                account.setBufferedCommissionStartDate(timeService.getCurrentTime());
            }
        }

        return account;
    }
}
