package com.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhang.wenhan
 * @description WebSocketServer
 * @date 2019/11/27 15:09
 */
public class WebSocketServer {

    public static void main(String[] args) {
        // 定义一对线程组
        // 主线程组, 用于接受客户端的连接
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // 从线程组, 负责IO交互工作
        EventLoopGroup subGroup = new NioEventLoopGroup();
        //netty服务器的创建, 辅助工具类，用于服务器通道的一系列配置
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //绑定两个线程组
        serverBootstrap.group(mainGroup, subGroup)
                //指定NIO的模式
                .channel(NioServerSocketChannel.class)
                //子处理器，用于处理workerGroup
                .childHandler(new WSServerInitialzer());
        // 启动server，并且设置8088为启动的端口号，同时启动方式为同步
        try {
            ChannelFuture future = serverBootstrap.bind(8088).sync();
            // 监听关闭的channel，设置位同步方式
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //退出线程组
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }

    }

}
