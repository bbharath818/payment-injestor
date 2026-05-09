package com.lloyds.payments.ingestor.exception;


public class PaymentIngestorException extends BaseException {

    public PaymentIngestorException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage(),
                errorCode.getCode(),
                errorCode.getStatus());
    }

    public PaymentIngestorException(ErrorCode errorCode, String customMessage) {
        super(customMessage,
                errorCode.getCode(),
                errorCode.getStatus());
    }

    public PaymentIngestorException(String errorCode, String customMessage, int status) {
        super(customMessage,
                errorCode,
                status);
    }
}
