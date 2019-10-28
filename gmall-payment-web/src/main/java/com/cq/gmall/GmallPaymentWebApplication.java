package com.cq.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class})
@MapperScan(basePackages = "com.cq.gmall.payment.mapper")
public class GmallPaymentWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentWebApplication.class, args);
    }

}
