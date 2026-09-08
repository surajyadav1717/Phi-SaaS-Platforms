package com.dashboard.saas.service.payments;

import com.dashboard.saas.dtos.PaymentVerificationRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl  implements PaymentService {



    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String keySecret;


    public PaymentServiceImpl(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createOrder(BigDecimal amount) throws RazorpayException {

        long amountInPaise = amount.multiply(BigDecimal.valueOf(100)).longValue();

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_11" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }

    public boolean verifyPaymentSignature(PaymentVerificationRequest paymentVerificationRequest) {

        JSONObject options = new JSONObject();
        options.put(
                "razorpay_order_id",
                paymentVerificationRequest.getRazorpayOrderId()
        );

        options.put(
                "razorpay_payment_id",
                paymentVerificationRequest.getRazorpayPaymentId()
        );

        options.put(
                "razorpay_signature",
                paymentVerificationRequest.getRazorpaySignature()
        );

        try {

            return Utils.verifyPaymentSignature(
                    options,
                    keySecret
            );

        } catch (RazorpayException e) {

            return false;
        }
    }
}
