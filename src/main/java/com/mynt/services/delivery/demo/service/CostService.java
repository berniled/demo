package com.mynt.services.delivery.demo.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.services.delivery.demo.model.Cost;
import com.mynt.services.delivery.demo.model.Priority;

public class CostService {
        private Cost cost;
        private Resource priorityConfig;
        private HashMap<Integer,Priority> priority; //in 1,2,3,4,5
        private List<Priority> jsonObjPriority;
        private String statusParcel;



        // A constructor to initialize the fields
        public CostService(String name, double weight, double height, double width, double length, Resource priorityConfig) {
            Cost costCompute = new Cost();
            costCompute.setName(name);
            costCompute.setWeight(weight);
            costCompute.setHeight(height);
            costCompute.setWidth(width);
            costCompute.setLength(length);
            this.setCost(costCompute);
            this.priorityConfig = priorityConfig;
            this.jsonObjPriority = parseJsonObject(priorityConfig);
            this.priority = calculatePriority();
            this.getCost().setCost(calculateCost());

        }

        public List<Priority> parseJsonObject(Resource priorityConfig){
            ObjectMapper objectMapper = new ObjectMapper();
            
            try {
                String prioCfg = StreamUtils.copyToString(priorityConfig.getInputStream(), Charset.defaultCharset());
                List<Priority> priorityTable = objectMapper.readValue(prioCfg,new TypeReference<List<Priority>>(){});
                return priorityTable;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList<Priority>();
        }

        // A method to calculate the cost of the delivery of the item or package
        public double calculateCost() {
            double costing = -1.00;
            
            for (Map.Entry<Integer, Priority> entry : priority.entrySet()) {
                Integer key = entry.getKey();
                Priority value = entry.getValue();
                switch (key) { 
                    case 1:
                        break;
                    case 2:
                          costing = value.getCostValue() * this.getCost().getWeight();
                          break;
                    case 3:
                    case 4:
                    case 5:
                        costing = value.getCostValue() * (this.getCost().getLength() * this.getCost().getWidth() * this.getCost().getHeight());
                }
            }

            return costing;
        }

        // A method to calculate the priority of the delivery of the item or package
        public HashMap<Integer, Priority> calculatePriority() {
            double volumeParcel = this.getCost().getLength() * this.getCost().getWidth() * this.getCost().getHeight();
            HashMap<Integer, Priority> mpPriority = new HashMap<Integer, Priority>();
            jsonObjPriority.stream()
                        .filter(item -> item.getPriority() == Integer.valueOf(1))
                        .forEach(item -> mpPriority.put(item.getPriority(), item));
            jsonObjPriority.stream()
                        .filter(item -> item.getPriority() == Integer.valueOf(2))
                        .forEach(item -> mpPriority.put(item.getPriority(), item));
            jsonObjPriority.stream()
                        .filter(item -> item.getPriority() == Integer.valueOf(3))
                        .forEach(item -> mpPriority.put(item.getPriority(), item));
            jsonObjPriority.stream()
                        .filter(item -> item.getPriority() == Integer.valueOf(4))
                        .forEach(item -> mpPriority.put(item.getPriority(), item));
            jsonObjPriority.stream()
                        .filter(item -> item.getPriority() == Integer.valueOf(5))
                        .forEach(item -> mpPriority.put(item.getPriority(), item));

            if (this.getCost().getWeight() > mpPriority.get(1).getWeightConfigValue() ){
                mpPriority.remove(2);
                mpPriority.remove(3);
                mpPriority.remove(4);
                mpPriority.remove(5);
            } else if (this.getCost().getWeight() > mpPriority.get(2).getWeightConfigValue()){
                mpPriority.remove(1);
                mpPriority.remove(3);
                mpPriority.remove(4);
                mpPriority.remove(5);
            } else if (volumeParcel < mpPriority.get(3).getVolumeConfigValue()){
                mpPriority.remove(1);
                mpPriority.remove(2);
                mpPriority.remove(4);
                mpPriority.remove(5);
            } else if (volumeParcel < mpPriority.get(4).getVolumeConfigValue() ){
                mpPriority.remove(2);
                mpPriority.remove(3);
                mpPriority.remove(1);
                mpPriority.remove(5);
            } else {
                mpPriority.remove(2);
                mpPriority.remove(3);
                mpPriority.remove(4);
                mpPriority.remove(1);
            }
            return mpPriority;
        }

        // Getters and setters for the fields
        
        public Resource getPriorityConfig(){
            return priorityConfig;
        }

        public void setPriorityConfig(Resource priorityConfig){
            this.priorityConfig = priorityConfig;
        }

        public HashMap<Integer,Priority> getPriority() {
            return priority;
        }

        public void setPriority(HashMap<Integer,Priority> priority) {
            this.priority = priority;
        }

        public String getStatusParcel() {
            return statusParcel;
        }

        public void setStatusParcel(String statusParcel) {
            this.statusParcel = statusParcel;
        }

        public List<Priority> getJsonObjPriority() {
            return jsonObjPriority;
        }

        public void setJsonObjPriority(List<Priority> jsonObjPriority) {
            this.jsonObjPriority = jsonObjPriority;
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public Cost getCost(){
            return this.cost;
        }
        // A method to return a string representation of the item or package
        @Override
        public String toString() {
            return "DeliveryItem{" +
                    "name='" + getCost().getName() + '\'' +
                    ", weight=" + getCost().getWeight() +
                    ", height=" + getCost().getHeight() +
                    ", width=" + getCost().getWidth() +
                    ", length=" + getCost().getLength() +
                    ", cost=" + getCost().getCost() +
                    ", priority=" + priority +
                    '}';
        }
}