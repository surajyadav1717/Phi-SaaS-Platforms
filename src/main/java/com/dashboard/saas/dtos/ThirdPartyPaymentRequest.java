package com.dashboard.saas.dtos;

public class ThirdPartyPaymentRequest {


    private String merchantId;
    private ThirdPartyCustomer customer;
    private Transaction transaction;
    private ThirdPartyPaymentMethod paymentMethod;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public ThirdPartyCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ThirdPartyCustomer customer) {
        this.customer = customer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ThirdPartyPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(ThirdPartyPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
