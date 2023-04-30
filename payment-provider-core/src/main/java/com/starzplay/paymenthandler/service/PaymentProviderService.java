package com.starzplay.paymenthandler.service;

import com.starzplay.paymentprovider.api.model.request.PaymentMethodsListRequest;
import com.starzplay.paymentprovider.api.model.response.BaseResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodsResponse;

public interface PaymentProviderService {
    PaymentMethodsResponse getAllPaymentMethods();

    PaymentMethodsResponse getPaymentMethodsByName(String name);

    PaymentMethodResponse getPaymentMethodById(long id);

    BaseResponse savePaymentMethods(PaymentMethodsListRequest paymentMethodsListRequest);

    PaymentMethodResponse updatePaymentMethods(long paymentMethodId, PaymentMethodsListRequest paymentMethodsRequest);

}
