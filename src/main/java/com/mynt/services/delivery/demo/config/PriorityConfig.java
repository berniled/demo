package com.mynt.services.delivery.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class PriorityConfig {
    
    @Value("classpath:cost.json")
    private Resource priorityConfig;


    /*
     * 
     * Sample config
     * 
     * { 
     *   "priority": 1,
     *   "rule_name": "Reject",
     *    "condition": "Weight exceeds 50kg",
     *   "cost": "N/A"
     * },
     * {
     *   "priority": 2,
     *  "rule_name": "Heavy Parcel",
    "condition": "Weight exceeds 10kg",
    "cost": "PHP 20 * Weight (kg)"
  },
  {
    "priority": 3,
    "rule_name": "Small Parcel",
    "condition": "Volume is less than 1500 cm3",
    "cost": "PHP 0.03 * Volume"
  },
  {
    "priority": 4,
    "rule_name": "Medium Parcel",
    "condition": "Volume is less than 2500 cm3",
    "cost": "PHP 0.04 * Volume"
  },
  {
    "priority": 5,
    "rule_name": "Large Parcel",
    "condition": "None",
    "cost": "PHP 0.05 * Volume"
     * 
     */
    public Resource getPriorityConfig() {
        return priorityConfig;
    }

    public void setPriorityConfig(Resource priorityConfig){
      this.priorityConfig = priorityConfig;
    }
}
