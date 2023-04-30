package com.starzplay.paymentprovider.api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentPlanDTO {
    private long id;
    private double netAmount;
    private double taxAmount;
    private double grossAmount;
    private String currency;
    private String duration;
}
