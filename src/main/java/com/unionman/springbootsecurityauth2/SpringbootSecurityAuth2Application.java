package com.unionman.springbootsecurityauth2;

import com.unionman.springbootsecurityauth2.utils.UDPTest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.unionman.springbootsecurityauth2.mapper")
@SpringBootApplication
public class SpringbootSecurityAuth2Application {
    public static void main(String[] args) {
        UDPTest.getIpAndPort();
        SpringApplication.run(SpringbootSecurityAuth2Application.class, args);
    }
}
