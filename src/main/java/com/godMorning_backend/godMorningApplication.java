package com.godMorning_backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class godMorningApplication{
    @Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(godMorningApplication.class, args);
    }

}
//연결 과연? 8 57