package com.zoro.service.impl;

import com.zoro.service.HelloService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by AA on 2019/3/15.
 */
@Primary
@Service
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String sayHello(String msg) {
        return "HelloServiceImpl2 处理了" + msg;
    }
}
