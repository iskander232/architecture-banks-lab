package edu.phystech.banks_lab.service.account_process;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AccountProcessServiceFactory {

    Map<String, AccountProcessService> services;

    public AccountProcessServiceFactory(CreditAccountProcessService creditAccountProcessService,
                                        DebitAccountProcessService debitAccountProcessService,
                                        DepositAccountProcessService depositAccountProcessService) {
        services = Map.of(
                "credit", creditAccountProcessService,
                "debit", debitAccountProcessService,
                "deposit", depositAccountProcessService
        );
    }


    public AccountProcessService getAccountProcessService(String type) {
        return services.get(type);
    }
}
