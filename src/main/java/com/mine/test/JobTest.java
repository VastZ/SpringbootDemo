package com.mine.test;

import java.util.ArrayList;
import java.util.List;

public class JobTest {

    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        User User0 = new User();
        User User1 = new User();
        User User2 = new User();
        User User3 = new User();
        list.add(User0);
        list.add(User1);
        list.add(User2);
        list.add(User3);

        for (final User User : list) {
            ExecutorServiceFactory.es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "变化前的" + User.getName());
                    System.out.println(Thread.currentThread().getName() + "变化前的" + User);
                    User Usernew = change(User);
                    System.out.println(Thread.currentThread().getName() + "变化后的" + Usernew.getName());
                    System.out.println(Thread.currentThread().getName() + "变化后的" + User);
                }
            });
        }

    }

    private static User change(User user) {
        user.setName(Thread.currentThread().getName() + " test");
        return user;
    }

}

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}