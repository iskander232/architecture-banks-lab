package edu.phystech.banks_lab.controller.model;

import java.util.List;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditBankRequest {

    private int creditCommission;
    private int creditLimit;
    private int debitCommission;
    private int precent;
    private DepositValue depositValue;

    private int unauthorizedLimit;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepositItem {
        Integer minLimit;
        Integer maxLimit;
        int precent;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepositValue {
        List<DepositItem> items;
        long timeLimit;
    }
}
