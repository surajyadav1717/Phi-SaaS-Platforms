package com.dashboard.saas.service.payments;

import com.dashboard.saas.configuration.RestClientConfig;
import com.dashboard.saas.dtos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl  implements PaymentService {

    private final RestClientConfig restClientConfig;

    private final RestClient restClient;

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String keySecret;


    public PaymentServiceImpl(RestClientConfig restClientConfig, RestClient restClient, RazorpayClient razorpayClient) {
        this.restClientConfig = restClientConfig;
        this.restClient = restClient;
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createOrder(BigDecimal amount) throws RazorpayException {

        long amountInPaise = amount.multiply(BigDecimal.valueOf(100)).longValue();

        JSONObject orderRequest = new JSONObject();

        ObjectMapper objectMapper = new ObjectMapper();

        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcpt id_11" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
    }

    @Override
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

    @Override
    public ThirdPartyPaymentResponse createPayment(PaymentRequest paymentRequest) {

        // Customer mapping

        ThirdPartyPaymentRequest thirdPartyPaymentRequest = new ThirdPartyPaymentRequest();

        thirdPartyPaymentRequest.setMerchantId("MERCHANT123");
        ThirdPartyCustomer thirdPartyCustomer = new ThirdPartyCustomer();
        thirdPartyCustomer.setName(paymentRequest.getCustomer().getName());
        thirdPartyCustomer.setEmail(paymentRequest.getCustomer().getEmail());

        thirdPartyPaymentRequest.setCustomer(thirdPartyCustomer);


        // Transaction mapping

        Transaction transaction = new Transaction();

        transaction.setReference(paymentRequest.getOrder().getOrderId());
        transaction.setAmount(paymentRequest.getOrder().getAmount());
        transaction.setCurrency(paymentRequest.getOrder().getCurrency());

        thirdPartyPaymentRequest.setTransaction(transaction);

        // Payment method mapping
        ThirdPartyPaymentMethod paymentMethod = new ThirdPartyPaymentMethod();

        paymentMethod.setType(paymentRequest.getPayment().getMethod());

        thirdPartyPaymentRequest.setPaymentMethod(paymentMethod);


        // 2. Call Third Party API

        ThirdPartyPaymentResponse thirdPartyPaymentResponse =
                restClient.post()
                        .uri("/api/v1/payment")
                        .body(thirdPartyPaymentRequest)
                        .retrieve()
                        .body(ThirdPartyPaymentResponse.class);


        return thirdPartyPaymentResponse;
    }
}
