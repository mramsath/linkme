package com.arz.linkme.analytics.api;

import com.arz.linkme.analytics.model.Order;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @Autowired
    private KieSession kieSession;

    @PostMapping("orderDiscount")
    public Order orderNow(@RequestBody Order order){
        kieSession.insert(order);
        kieSession.fireAllRules();
        return order;
    }

}
