package com.starzplay.paymenthandler.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "payment_type", unique = true)
    private String paymentType;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
    private Set<PaymentPlanEntity> paymentPlans;

    public void setPaymentPlans(Set<PaymentPlanEntity> paymentPlans) {
        paymentPlans.forEach(paymentPlan -> paymentPlan.setPaymentMethod(this));
        this.paymentPlans = paymentPlans;
    }
}
