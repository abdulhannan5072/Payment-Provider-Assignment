package com.starzplay.paymenthandler.repository;

import com.starzplay.paymenthandler.entity.PaymentPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlanEntity, Long> {
    Set<PaymentPlanEntity> findByPaymentMethodId(long paymentMethodId);
}
