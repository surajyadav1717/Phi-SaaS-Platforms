package com.dashboard.saas.dtos;

public class PaymentRequest {

    private com.dashboard.saas.dtos.Customer customer;
    private com.dashboard.saas.dtos.Order order;
    private com.dashboard.saas.dtos.Payment  payment;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
