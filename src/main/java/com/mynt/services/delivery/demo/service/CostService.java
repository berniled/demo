package com.mynt.services.delivery.demo.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.services.delivery.demo.model.Cost;
import com.mynt.services.delivery.demo.model.Priority;
import com.mynt.services.delivery.demo.model.VoucherItem;

public class CostService {
        private Cost cost;
        private Resource priorityConfig;
        private HashMap<Integer,Priority> priority; //in 1,2,3,4,5
        private List<Priority> jsonObjPriority;
        private VoucherItem jsonObjVoucher;
        private String statusParcel;



        // A constructor to initialize the fields
        public CostService(String name, double weight, double height, double width, double length, Optional<String> voucher, Resource priorityConfig, Resource voucherConfig) {
            Cost costCompute = new Cost();
            costCompute.setName(name);
            costCompute.setWeight(weight);
            costCompute.setHeight(height);
            costCompute.setWidth(width);
            costCompute.setLength(length);
            costCompute.setDiscountCode(voucher);
            
            this.setCost(costCompute);
            this.priorityConfig = priorityConfig;
            this.jsonObjPriority = parseJsonObject(priorityConfig);
            this.jsonObjVoucher = parseJsonObjectVoucher(voucherConfig);
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

        public VoucherItem parseJsonObjectVoucher(Resource voucherConfig){
            ObjectMapper objectMapper = new ObjectMapper();
            
            try {
                String vouchCfg = StreamUtils.copyToString(voucherConfig.getInputStream(), Charset.defaultCharset());
                VoucherItem voucherItem = objectMapper.readValue(vouchCfg,new TypeReference<VoucherItem>(){});
                return voucherItem;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new VoucherItem();
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
                          costing = costing - applyDiscountOrNot(value);
                          break;
                    case 3:
                    case 4:
                    case 5:
                        costing = value.getCostValue() * (this.getCost().getLength() * this.getCost().getWidth() * this.getCost().getHeight());
                    default:
                        costing = costing - applyDiscountOrNot(value);
                        
                }
            }

            return costing;
        }

        private double applyDiscountOrNot(Priority discount){
                Optional<String> stat = Optional.of(discount.getRuleName());
                if (stat.isPresent()){
                    this.getCost().setStatus(stat.get());
                }
                if (this.getCost().getDiscountCode().isPresent()){
                    return getDiscountAmount(this.getCost().getDiscountCode().get());
                }
                return 0.0;
        }

        /**
         * Call and apply the discount code
         * @param discountCode
         * @return
         */
        private double getDiscountAmount(String discountCode){
            double discount = 0.0;
            try {
                ResponseEntity<VoucherItem> responseEntity = new RestTemplate().getForEntity(
                jsonObjVoucher.getPath() + discountCode, VoucherItem.class);

                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                            if (responseEntity.getBody() != null){
                                discount = responseEntity.getBody().getDiscount();
                            }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("Microservice failure");
                } 
            return discount;
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

        public void setJsonObjVoucher(VoucherItem jsonObjVoucher){
            this.jsonObjVoucher = jsonObjVoucher;
        }

        public VoucherItem getJsonObjVoucher(){
            return jsonObjVoucher;
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