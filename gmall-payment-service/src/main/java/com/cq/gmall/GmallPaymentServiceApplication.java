package com.cq.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
public class GmallPaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentServiceApplication.class, args);
    }

}
