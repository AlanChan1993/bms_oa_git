package com.infinitus.bms_oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.infinitus.bms_oa.mapper")
@EnableScheduling
public class BmsOaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsOaApplication.class, args);
    }
}
