package com.starzplay.paymenthandler.repository;

import com.starzplay.paymenthandler.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {

    PaymentMethodEntity findByPaymentType(String type);

    List<PaymentMethodEntity> findByName(String name);

}
