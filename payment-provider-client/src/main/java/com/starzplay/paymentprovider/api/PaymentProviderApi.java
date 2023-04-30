package com.starzplay.paymentprovider.api;

import com.starzplay.paymentprovider.api.model.request.PaymentMethodsListRequest;
import com.starzplay.paymentprovider.api.model.response.BaseResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping(value = "/v1.0/configuration")
public interface PaymentProviderApi {

    @GetMapping(value = "/payment-methods")
    public ResponseEntity<PaymentMethodsResponse> getPaymentMethods();

    @GetMapping(value = "/payment-methods", params = {"name"})
    public ResponseEntity<PaymentMethodsResponse> getPaymentMethodsByName
            (@RequestParam String name);

    @GetMapping(value =  "/payment-methods", params = {"id"})
    public ResponseEntity<PaymentMethodResponse> getPaymentMethodId
            (@RequestParam(name = "id") long id);

    @PostMapping(value = "/payment-methods")
    public ResponseEntity<BaseResponse> savePaymentMethods
            (@Valid @RequestBody PaymentMethodsListRequest paymentMethodsListRequest);

    @PutMapping(value = "/payment-methods")
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethods
            (@RequestParam(name = "payment-methods") long paymentMethodId, @Valid @RequestBody PaymentMethodsListRequest paymentMethodsListRequest);

}
