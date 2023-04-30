package com.starzplay.paymentprovider.api.model.response;

import com.starzplay.paymentprovider.api.model.PaymentMethodDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodsResponse extends BaseResponse {
    private Set<PaymentMethodDTO> paymentMethodsList;
}
