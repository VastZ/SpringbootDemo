package com.zoro.controller;

import com.zoro.dto.Car;
import com.zoro.dto.Wheel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

/**
 * @author zhang.wenhan
 * @description TestController
 * @date 2019/6/6 13:58
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    Car car;

    @Autowired
    Wheel wheel;

    @GetMapping("/bean")
    public String testBean(){
        System.out.println(car.getWheel());
        System.out.println(wheel);
        System.out.println(car.getWheel() == wheel);
        return "Done";
    }
}
