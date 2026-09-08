package com.dashboard.saas.controllers.razorpay;

import com.dashboard.saas.dtos.CreatePaymentOrderRequest;
import com.dashboard.saas.service.payments.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestBody CreatePaymentOrderRequest request)
            throws RazorpayException {

        String order =
                paymentService.createOrder(request.getAmount());

        return ResponseEntity.ok(order);
    }


}
