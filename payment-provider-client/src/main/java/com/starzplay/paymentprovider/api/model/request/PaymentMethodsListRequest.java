package com.starzplay.paymentprovider.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Valid
public class PaymentMethodsListRequest {

    @NotEmpty
    private List<PaymentMethodRequest> paymentMethods;
}
