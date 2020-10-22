package com.zoro.dto;

/**
 * @author zhang.wenhan
 * @description Car
 * @date 2019/6/6 13:59
 */
public class Car {

    Wheel wheel;

    public Car(Wheel wheel) {
        this.wheel = wheel;
    }

    public void say(){
        wheel.say();
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }
}
