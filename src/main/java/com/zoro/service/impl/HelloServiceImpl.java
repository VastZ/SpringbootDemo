package com.zoro.service.impl;

import com.zoro.service.HelloService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by AA on 2019/3/15.
 */

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "HelloServiceImpl 处理了 " + msg;
    }
}
