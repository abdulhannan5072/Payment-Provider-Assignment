package com.starzplay.paymentprovider.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentPlanRequest {

    @JsonProperty
    private long id;

    @NotNull
    @NotBlank(message = "Name must not be empty")
    @JsonProperty(required = true)
    private double netAmount;

    @NotNull
    @NotBlank(message = "Tax Amount must not be empty")
    @JsonProperty(required = true)
    private double taxAmount;

    @NotNull
    @NotBlank(message = "Gross Amount must not be empty")
    @JsonProperty(required = true)
    private double grossAmount;

    @NotNull
    @NotBlank(message = "Currency must not be empty")
    @JsonProperty(required = true)
    private String currency;

    @NotNull
    @NotBlank(message = "Duration must not be empty")
    @JsonProperty(required = true)
    private String duration;

    private PaymentMethodRequest paymentMethod;
}
