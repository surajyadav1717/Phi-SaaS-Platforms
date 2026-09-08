package com.dashboard.saas.service.payments;

import com.dashboard.saas.dtos.PaymentVerificationRequest;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;

public interface PaymentService {

    public String  createOrder(BigDecimal  amount) throws RazorpayException ;

    public  boolean verifyPaymentSignature(PaymentVerificationRequest paymentVerificationRequest);
}
