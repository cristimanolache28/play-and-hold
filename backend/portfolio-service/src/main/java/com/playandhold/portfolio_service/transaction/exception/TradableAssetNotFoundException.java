package com.playandhold.portfolio_service.transaction.exception;

public class TradableAssetNotFoundException extends RuntimeException {
    public TradableAssetNotFoundException(String message) {
        super(message);
    }
}
