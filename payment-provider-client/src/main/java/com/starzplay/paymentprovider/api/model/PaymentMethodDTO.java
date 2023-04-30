package com.starzplay.paymentprovider.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PaymentMethodDTO {
    private long id;
    private String name;
    private String displayName;
    private String paymentType;
    private Set<PaymentPlanDTO> paymentPlans;
}
