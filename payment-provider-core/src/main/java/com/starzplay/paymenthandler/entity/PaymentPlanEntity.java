package com.starzplay.paymenthandler.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "payment_plan")
public class PaymentPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "net_amount")
    private double netAmount;

    @Column(name = "tax_amount")
    private double taxAmount;

    @Column(name = "gross_amount")
    private double grossAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "duration")
    private String duration;

    @ManyToOne
    @JoinColumn(name = "method_id", nullable = false)
    private PaymentMethodEntity paymentMethod;
}
