package com.dashboard.saas.exceptions;

public class VariantDoesNotBelongToProduct  extends RuntimeException{

    public VariantDoesNotBelongToProduct(String message) {
        super(message);
    }
}
