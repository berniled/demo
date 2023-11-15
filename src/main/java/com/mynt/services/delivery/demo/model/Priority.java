package com.mynt.services.delivery.demo.model;

public class Priority {
    private Integer priority;
    private String ruleName;
    private String condition;
    private boolean weightCondition;
    private boolean volumeCondition;
    private long weightConfigValue;
    private long volumeConfigValue;
   
    private float costValue;
    
    public float getCostValue() {
        return costValue;
    }
    public void setCostValue(float costValue) {
        this.costValue = costValue;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setWeightCondition(boolean weightCondition) {
        this.weightCondition = weightCondition;
    }
    public void setVolumeCondition(boolean volumeCondition) {
        this.volumeCondition = volumeCondition;
    }
    
    public Integer getPriority() {
        return priority;
    }
    public String getRuleName() {
        return ruleName;
    }
    public String getCondition() {
        return condition;
    }
    public boolean isWeightCondition() {
        return weightCondition;
    }
    public boolean isVolumeCondition() {
        return volumeCondition;
    }
    
    public long getWeightConfigValue() {
        return weightConfigValue;
    }
    public void setWeightConfigValue(long weightConfigValue) {
        this.weightConfigValue = weightConfigValue;
    }
    public long getVolumeConfigValue() {
        return volumeConfigValue;
    }
    public void setVolumeConfigValue(long volumeConfigValue) {
        this.volumeConfigValue = volumeConfigValue;
    }

}
