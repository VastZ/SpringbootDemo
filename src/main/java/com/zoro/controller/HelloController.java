package com.zoro.controller;

import com.zoro.dto.RequestDTO;
import com.zoro.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.HttpMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by AA on 2019/3/15.
 */
@RestController
public class HelloController {

    private HelloService helloService = null;

    @Autowired
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostConstruct
    public void init() {
        System.out.println("HelloController init  .......");
    }

    @GetMapping("/say")
    public String sayHello(@RequestParam("msg") String msg) {
        return helloService.sayHello(msg);
    }

    @PostMapping(path = "/say", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    // @PostMapping("/say/{name}")
    public String sayHello1(@RequestBody(required = false) RequestDTO req, @PathVariable("name") String name) {
        String str = "";
        if (req != null) {
            str += "当前用户名：" + req.getName() + "当前用户年龄：" + req.getAge();
        }
        str += " 另一个名字" + name;
        return str;
    }

    @GetMapping("/image")
    public String image() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("head.jpg");
        BufferedImage image = ImageIO.read(is);
//        BufferedImage image = ImageIO.read(new File(imgPath));
        return image.getHeight() + "";
    }
}