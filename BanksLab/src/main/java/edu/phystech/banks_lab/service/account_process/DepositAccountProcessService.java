package edu.phystech.banks_lab.service.account_process;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import edu.phystech.banks_lab.controller.model.EditBankRequest;
import edu.phystech.banks_lab.exception.BusinessException;
import edu.phystech.banks_lab.model.Account;
import edu.phystech.banks_lab.model.Bank;
import edu.phystech.banks_lab.service.TimeService;
import org.springframework.stereotype.Service;

@Service
public class DepositAccountProcessService implements AccountProcessService {

    TimeService timeService;
    ObjectReader objectReader;

    DepositAccountProcessService(TimeService timeService) {
        this.timeService = timeService;
        this.objectReader = new ObjectMapper().reader();
    }

    @Override
    public Account addMoney(int amount, Account account, Bank bank) {
        if (account.getDepositCanTransferDate() > timeService.getCurrentTime()) {
            account.setAccountValue(account.getAccountValue() + amount);

            return account;
        } else {
            throw new BusinessException(BusinessException.Reason.CAN_NOT_TRANSFER_MONEY);
        }
    }

    @Override
    public Account substractMoney(int amount, Account account, Bank bank) {
        if (account.getDepositCanTransferDate() > timeService.getCurrentTime()
                && account.getAccountValue() <= amount) {
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

        account.setBufferedValue((int) (account.getBufferedValue() + diffMonths * getPrecent(account.getAccountValue(), bank) / 12.0));

        diffMonths = timeService.diffMonths(account.getBufferedCommissionStartDate());
        if (diffMonths >= 12) {
            account.setAccountValue(account.getAccountValue() + account.getBufferedValue());
            account.setBufferedValue((int) ((diffMonths % 12) * getPrecent(account.getAccountValue(), bank) / 12.0));
            if (account.getBufferedValue() > 0) {
                account.setBufferedCommissionStartDate(timeService.getCurrentTime());
            }
        }

        return account;
    }

    EditBankRequest.DepositValue getValue(Bank bank) {
        try {
            return objectReader.readValue(bank.getDepositValue(), EditBankRequest.DepositValue.class);
        } catch (IOException e) {
            throw new BusinessException(BusinessException.Reason.CAN_NOT_PARSE_BANK_BODY);
        }
    }

    private int getPrecent(int amount, Bank bank) {
        for (var item: getValue(bank).getItems()) {
            if (item.getMinLimit() == null && item.getMaxLimit() == null
                    || item.getMinLimit() == null && item.getMaxLimit() != null && amount <= item.getMaxLimit()
                    || item.getMinLimit() != null && item.getMaxLimit() != null && item.getMinLimit() <= amount && amount < item.getMaxLimit()
                    || item.getMinLimit() != null && item.getMinLimit() <= amount && item.getMaxLimit() == null) {
                return item.getPrecent();
            }
        }

        throw new BusinessException(BusinessException.Reason.CAN_NOT_PARSE_BANK_BODY);
    }
}
