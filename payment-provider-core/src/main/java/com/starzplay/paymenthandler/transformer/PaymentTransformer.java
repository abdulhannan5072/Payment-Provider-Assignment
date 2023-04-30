package com.starzplay.paymenthandler.transformer;

import com.starzplay.paymenthandler.entity.PaymentMethodEntity;
import com.starzplay.paymenthandler.entity.PaymentPlanEntity;
import com.starzplay.paymentprovider.api.model.PaymentMethodDTO;
import com.starzplay.paymentprovider.api.model.PaymentPlanDTO;
import com.starzplay.paymentprovider.api.model.request.PaymentMethodRequest;
import com.starzplay.paymentprovider.api.model.request.PaymentPlanRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaymentTransformer {
    public PaymentPlanEntity toPaymentPlanEntityFromRequest(PaymentPlanRequest paymentPlanRequest){
        PaymentPlanEntity payPlanEntity = new PaymentPlanEntity();
        payPlanEntity.setNetAmount(paymentPlanRequest.getNetAmount());
        payPlanEntity.setTaxAmount(paymentPlanRequest.getTaxAmount());
        payPlanEntity.setGrossAmount(paymentPlanRequest.getGrossAmount());
        payPlanEntity.setCurrency(paymentPlanRequest.getCurrency());
        payPlanEntity.setDuration(paymentPlanRequest.getDuration());
        return payPlanEntity;
    }

    public PaymentPlanEntity toPaymentPlanEntityFromDTO(PaymentPlanDTO paymentPlanDTO){
        PaymentPlanEntity payPlanEntity = new PaymentPlanEntity();
        payPlanEntity.setNetAmount(paymentPlanDTO.getNetAmount());
        payPlanEntity.setTaxAmount(paymentPlanDTO.getTaxAmount());
        payPlanEntity.setGrossAmount(paymentPlanDTO.getGrossAmount());
        payPlanEntity.setCurrency(paymentPlanDTO.getCurrency());
        payPlanEntity.setDuration(paymentPlanDTO.getDuration());
        payPlanEntity.setId(paymentPlanDTO.getId());
        return payPlanEntity;
    }

    public Set<PaymentPlanDTO> toPaymentPlanDTOListFromRequest(Set<PaymentPlanRequest> paymentPlanRequest){

        Set<PaymentPlanDTO> paymentPlans = new HashSet<>();
        paymentPlanRequest.forEach(paymentPlanReq -> {
            PaymentPlanDTO paymentPlanDTO = new PaymentPlanDTO();
            paymentPlanDTO.setNetAmount(paymentPlanReq.getNetAmount());
            paymentPlanDTO.setTaxAmount(paymentPlanReq.getTaxAmount());
            paymentPlanDTO.setGrossAmount(paymentPlanReq.getGrossAmount());
            paymentPlanDTO.setCurrency(paymentPlanReq.getCurrency());
            paymentPlanDTO.setDuration(paymentPlanReq.getDuration());
            paymentPlanDTO.setId(paymentPlanReq.getId());
            paymentPlans.add(paymentPlanDTO);
        });
        return paymentPlans;
    }

    public PaymentMethodEntity toPaymentMethodEntityFromRequest(PaymentMethodRequest paymentMethodRequest) {
        return toPaymentMethodEntityFromRequest(paymentMethodRequest, true);
    }

    public PaymentMethodEntity toPaymentMethodEntityFromRequest(PaymentMethodRequest paymentMethodRequest, boolean addPaymentPlans) {
        PaymentMethodEntity payMethodEntity = new PaymentMethodEntity();
        payMethodEntity.setName(paymentMethodRequest.getName());
        payMethodEntity.setDisplayName(paymentMethodRequest.getDisplayName());
        payMethodEntity.setPaymentType(paymentMethodRequest.getPaymentType());
        if(addPaymentPlans){
            payMethodEntity.setPaymentPlans(paymentMethodRequest.getPaymentPlans().stream()
                    .map(this::toPaymentPlanEntityFromRequest).collect(Collectors.toSet()));
        }
        return payMethodEntity;
    }

    public PaymentMethodDTO toPaymentMethodDTOFromEntity(PaymentMethodEntity paymentMethodEntity) {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setId(paymentMethodEntity.getId());
        paymentMethodDTO.setName(paymentMethodEntity.getName());
        paymentMethodDTO.setDisplayName(paymentMethodEntity.getDisplayName());
        paymentMethodDTO.setPaymentType(paymentMethodEntity.getPaymentType());
        paymentMethodDTO.setPaymentPlans(paymentMethodEntity.getPaymentPlans().stream().map(this::toPaymentPlanDTOFromEntity).collect(Collectors.toSet()));
        return paymentMethodDTO;
    }

    public PaymentPlanDTO toPaymentPlanDTOFromEntity(PaymentPlanEntity paymentPlanEntity) {
        PaymentPlanDTO paymentPlanDTO = new PaymentPlanDTO();
        paymentPlanDTO.setId(paymentPlanEntity.getId());
        paymentPlanDTO.setNetAmount(paymentPlanEntity.getNetAmount());
        paymentPlanDTO.setTaxAmount(paymentPlanEntity.getTaxAmount());
        paymentPlanDTO.setGrossAmount(paymentPlanEntity.getGrossAmount());
        paymentPlanDTO.setCurrency(paymentPlanEntity.getCurrency());
        paymentPlanDTO.setDuration(paymentPlanEntity.getDuration());
        return paymentPlanDTO;
    }

    public Set<PaymentMethodDTO> convertToPaymentMethodDTOList(List<PaymentMethodEntity> paymentMethodEntity){
        return paymentMethodEntity.stream().map(this::toPaymentMethodDTOFromEntity).collect(Collectors.toSet());
    }
}
