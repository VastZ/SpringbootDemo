package com.zoro.config;

import com.zoro.dto.Car;
import com.zoro.dto.Wheel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhang.wenhan
 * @description MyConfigration
 * @date 2019/6/6 13:58
 */
@Configuration
public class MyConfigration {

    @Bean
    public Car getCar(){
        return new Car(getWheel());
    }

    @Bean
    public Wheel getWheel(){
        return new Wheel();
    }
}
