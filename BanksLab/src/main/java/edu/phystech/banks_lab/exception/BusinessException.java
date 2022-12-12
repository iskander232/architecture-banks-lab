package edu.phystech.banks_lab.exception;

public class BusinessException extends RuntimeException {
    public enum Reason {
        NO_SUCH_CLIENT_EXCEPTION,
        TOO_LITTLE_MONEY,
        CAN_NOT_PARSE_BANK_BODY,
        CAN_NOT_TRANSFER_MONEY,
        NO_TRUST_CLIENT
    }

    public BusinessException(Reason reason) {
        super(reason.name());
    }
}
