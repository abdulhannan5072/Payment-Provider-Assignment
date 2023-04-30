package com.starzplay.paymenthandler.service.impl;

import com.starzplay.paymenthandler.entity.PaymentMethodEntity;
import com.starzplay.paymenthandler.entity.PaymentPlanEntity;
import com.starzplay.paymenthandler.exception.ResourceNotFoundException;
import com.starzplay.paymenthandler.repository.PaymentMethodRepository;
import com.starzplay.paymenthandler.repository.PaymentPlanRepository;
import com.starzplay.paymenthandler.service.PaymentProviderService;
import com.starzplay.paymenthandler.transformer.PaymentTransformer;
import com.starzplay.paymentprovider.api.model.PaymentPlanDTO;
import com.starzplay.paymentprovider.api.model.request.PaymentMethodRequest;
import com.starzplay.paymentprovider.api.model.request.PaymentMethodsListRequest;
import com.starzplay.paymentprovider.api.model.request.PaymentPlanRequest;
import com.starzplay.paymentprovider.api.model.response.BaseResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodResponse;
import com.starzplay.paymentprovider.api.model.response.PaymentMethodsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PaymentProviderServiceImpl implements PaymentProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProviderServiceImpl.class);

    private PaymentTransformer paymentTransformer;

    private PaymentMethodRepository paymentMethodRepository;

    private PaymentPlanRepository paymentPlanRepository;

    public PaymentProviderServiceImpl(PaymentTransformer paymentTransformer,
                                      PaymentMethodRepository paymentMethodRepository,
                                      PaymentPlanRepository paymentPlanRepositor) {
        this.paymentTransformer = paymentTransformer;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentPlanRepository = paymentPlanRepositor;
    }

    @Override
    public PaymentMethodsResponse getAllPaymentMethods() {
        List<PaymentMethodEntity> paymentMethods = paymentMethodRepository.findAll();
        PaymentMethodsResponse response = new PaymentMethodsResponse();
        response.setResultCode(HttpStatus.OK.value());
        response.setResultMessage(HttpStatus.OK.getReasonPhrase());
        response.setPaymentMethodsList(paymentTransformer.convertToPaymentMethodDTOList(paymentMethods));
        return response;
    }

    @Override
    public PaymentMethodsResponse getPaymentMethodsByName(String name) {
        List<PaymentMethodEntity> paymentMethods = paymentMethodRepository.findByName(name);
        PaymentMethodsResponse response = new PaymentMethodsResponse();
        if (!paymentMethods.isEmpty()) {
            LOGGER.info("Payment method found of name -> {}", name);
            response.setResultCode(HttpStatus.OK.value());
            response.setResultMessage(HttpStatus.OK.getReasonPhrase());
            response.setPaymentMethodsList(paymentTransformer.convertToPaymentMethodDTOList(paymentMethods));
            return response;
        }
        LOGGER.info("Payment method not found of name -> {}", name);
        throw new ResourceNotFoundException("Payment method not found with name -> " + name);
    }

    @Override
    public PaymentMethodResponse getPaymentMethodById(long id) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        Optional<PaymentMethodEntity> optPaymentMethod = paymentMethodRepository.findById(id);
        if (optPaymentMethod.isPresent()) {
            LOGGER.info("Payment method found of id -> {}", id);
            PaymentMethodEntity paymentMethod = optPaymentMethod.get();
            response.setResultCode(HttpStatus.OK.value());
            response.setResultMessage(HttpStatus.OK.getReasonPhrase());
            response.setPaymentMethod(paymentTransformer.toPaymentMethodDTOFromEntity(paymentMethod));
            return response;
        }
        LOGGER.info("Payment method not found of id -> {}", id);
        throw new ResourceNotFoundException("Payment method not found with id -> " + id);
    }

    @Override
    @Transactional
    public BaseResponse savePaymentMethods(PaymentMethodsListRequest paymentMethodsListRequest) {
        paymentMethodsListRequest.getPaymentMethods().forEach(paymentMethod -> {
            if (paymentMethodRepository.findByPaymentType(paymentMethod.getPaymentType()) == null) {
                LOGGER.info("Saving payment method in database of payment type = {}", paymentMethod.getPaymentType());
                paymentMethodRepository.save(paymentTransformer.toPaymentMethodEntityFromRequest(paymentMethod));
            } else {
                LOGGER.warn("Payment Method already exit, hence skipping this payment type -> {} ", paymentMethod.getPaymentType());
            }
        });
        return new BaseResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    @Override
    public PaymentMethodResponse updatePaymentMethods(long paymentMethodId, PaymentMethodsListRequest paymentMethodsRequest) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        PaymentMethodRequest paymentMethodRequest = paymentMethodsRequest.getPaymentMethods().get(0);
        if (paymentMethodRepository.existsById(paymentMethodId)) {
            LOGGER.info("Payment method found in db of id -> {}", paymentMethodId);

            PaymentMethodEntity paymentMethod = paymentTransformer.toPaymentMethodEntityFromRequest(paymentMethodRequest, false);
            paymentMethod.setId(paymentMethodId);

            paymentMethod.setPaymentPlans(fetchCurrentPlansAndAddUpdatedOnes(paymentMethodId, paymentMethodRequest.getPaymentPlans()));
            PaymentMethodEntity paymentMethodResponse = paymentMethodRepository.save(paymentMethod);
            LOGGER.info("Updated payment method in db of id -> {}", paymentMethodId);
            response.setResultCode(HttpStatus.OK.value());
            response.setResultMessage(HttpStatus.OK.getReasonPhrase());
            response.setPaymentMethod(paymentTransformer.toPaymentMethodDTOFromEntity(paymentMethodResponse));
            return response;
        }
        LOGGER.info("Payment method not exist of id -> {}", paymentMethodId);
        throw new ResourceNotFoundException("Payment method not found with id -> " + paymentMethodId);
    }

    private Set<PaymentPlanEntity> fetchCurrentPlansAndAddUpdatedOnes(Long methodId, Set<PaymentPlanRequest> paymentPlanRequest){

        //Plans from Request
        Set<PaymentPlanDTO> paymentPlanDTOListFromRequest = paymentTransformer.toPaymentPlanDTOListFromRequest(paymentPlanRequest);
        //Plans from db for a method based on method id
        Set<PaymentPlanEntity> paymentPlanListFromDB = paymentPlanRepository.findByPaymentMethodId(methodId);

        Map<Long, PaymentPlanEntity> mapPaymentPlan = new HashMap<>();
        for (PaymentPlanEntity plan : paymentPlanListFromDB) {
            mapPaymentPlan.put(plan.getId(), plan);
        }

        Set<PaymentPlanDTO> newPlansToAdd = new HashSet<>();

        //Filtering new plans which was not exist in DB
        for (PaymentPlanDTO plan : paymentPlanDTOListFromRequest) {
            if (!mapPaymentPlan.containsKey(plan.getId())) {
                PaymentPlanDTO nPlan = plan;
                nPlan.setId(0L);
                newPlansToAdd.add(nPlan);
            }
        }

        //Filtering existing plans which have to update in DB
        Set<PaymentPlanEntity> plansToUpdate = new HashSet<>();
        for (PaymentPlanDTO plan : paymentPlanDTOListFromRequest) {
            if (mapPaymentPlan.containsKey(plan.getId())) {
                plansToUpdate.add(paymentTransformer.toPaymentPlanEntityFromDTO(plan));
            }
        }

        for (PaymentPlanDTO plan : newPlansToAdd) {
            plansToUpdate.add(paymentTransformer.toPaymentPlanEntityFromDTO(plan));
        }

        return plansToUpdate;
    }

}
