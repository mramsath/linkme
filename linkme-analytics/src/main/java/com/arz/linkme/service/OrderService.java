package com.arz.linkme.service;

import org.kie.api.runtime.KieContainer;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final KieContainer kieContainer;

    public OrderService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public double findDiscountForOrders(){
        return 0;
    }
}
