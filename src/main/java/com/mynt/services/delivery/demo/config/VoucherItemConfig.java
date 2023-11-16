package com.mynt.services.delivery.demo.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class VoucherItemConfig {
    
    @Value("classpath:voucher.json")
    private Resource voucherConfig;

    public Resource getVoucherConfig() {
        return voucherConfig;
    }

    public void setVoucherConfig(Resource voucherConfig) {
        this.voucherConfig = voucherConfig;
    }

}
