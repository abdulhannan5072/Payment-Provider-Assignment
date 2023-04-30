package com.starzplay.paymentprovider.api.model.response;

import com.starzplay.paymentprovider.api.model.PaymentMethodDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponse extends BaseResponse {
    private PaymentMethodDTO paymentMethod;
}
