package com.starzplay.paymentprovider.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class PaymentMethodRequest {

    @NotNull
    @NotBlank(message = "Name must not be empty")
    @JsonProperty(required = true)
    private String name;

    @NotNull
    @NotBlank(message = "Display name must not be empty")
    @JsonProperty(required = true)
    private String displayName;

    @NotNull
    @NotBlank(message = "Payment Type must not be empty")
    @JsonProperty(required = true)
    private String paymentType;

    @NotNull
    @NotEmpty(message = "Payment Plans must not be empty")
    @JsonProperty(required = true)
    private Set<PaymentPlanRequest> paymentPlans;
}
