package com.starzplay.paymenthandler.controller;

import com.starzplay.paymenthandler.service.PaymentProviderService;
import com.starzplay.paymentprovider.api.PaymentProviderApi;
import com.starzplay.paymentprovider.api.model.request.PaymentMethodsListRequest;
import com.starzplay.paymentprovider.api.model.response.BaseResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PaymentProviderController implements PaymentProviderApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderController.class);
    private PaymentProviderService paymentProviderService;

    public PaymentProviderController(PaymentProviderService paymentProviderService) {
        this.paymentProviderService = paymentProviderService;
    }

    @Override
    public ResponseEntity<PaymentMethodsResponse> getPaymentMethods() {
        LOGGER.info("Fetch list of payment methods");
        PaymentMethodsResponse response = paymentProviderService.getAllPaymentMethods();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentMethodsResponse> getPaymentMethodsByName(String name) {
        LOGGER.info("Going to fetch list of payment methods by name: {}", name);
        PaymentMethodsResponse response = paymentProviderService.getPaymentMethodsByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentMethodResponse> getPaymentMethodId(long id) {
        LOGGER.info("Going to fetch list of payment methods by id: {}", id);
        PaymentMethodResponse response = paymentProviderService.getPaymentMethodById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> savePaymentMethods(PaymentMethodsListRequest paymentMethodsListRequest) {
        LOGGER.info("Going to save list of payment methods");
        BaseResponse response = paymentProviderService.savePaymentMethods(paymentMethodsListRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethods(long paymentMethodId, @Valid PaymentMethodsListRequest paymentMethodsListRequest) {
        LOGGER.info("Going to update payment method by id: {}", paymentMethodId);
        PaymentMethodResponse response = paymentProviderService.updatePaymentMethods(paymentMethodId, paymentMethodsListRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
