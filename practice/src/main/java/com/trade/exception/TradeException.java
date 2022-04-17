package com.trade.exception;

public class TradeException extends RuntimeException {

    private final String id;

    public TradeException(final String id) {
        super("Trade is not valid: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
