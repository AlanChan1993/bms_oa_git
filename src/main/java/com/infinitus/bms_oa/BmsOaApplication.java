package com.infinitus.bms_oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.infinitus.bms_oa.mapper")
@EnableScheduling
//@EnableTransactionManagement//用于事务回滚
public class BmsOaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsOaApplication.class, args);
    }
}
