package com.mynt.services.delivery.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.services.delivery.demo.config.PriorityConfig;
import com.mynt.services.delivery.demo.model.Cost;
import com.mynt.services.delivery.demo.service.CostService;

@RestController
public class CostController {
    
    @Autowired
    private PriorityConfig priorityConfig;

    @RequestMapping(value = "/cost", consumes="application/json",produces ="application/json", method = RequestMethod.POST)
    @ResponseBody
    public Cost cost(@RequestBody Cost costRequest) {
                        CostService costing =new CostService(costRequest.getName(), costRequest.getWeight(), costRequest.getHeight(), costRequest.getWidth(), costRequest.getLength(), priorityConfig.getPriorityConfig());
                         return costing.getCost();
                        
                     }


}