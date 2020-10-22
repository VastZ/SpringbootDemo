package com.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description: 处
 * TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * 这里已经指定了类型 如果这里是Object 那么下面还需判断是不是TextWebSocketFrame类型
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channle
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 获取客户端传输过来的消息
        String content = textWebSocketFrame.text();
        System.out.println("接受到的数据：" + content);

//      for (Channel channel: clients) {
//          channel.writeAndFlush(
//              new TextWebSocketFrame(
//                      "[服务器在]" + LocalDateTime.now()
//                      + "接受到消息, 消息为：" + content));
//      }
        // 下面这个方法，和上面的for循环一致   向客户端发送数据
        clients.writeAndFlush(new TextWebSocketFrame("我是服务器,我收到你的消息为:" + content));
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端到的channle，并且放ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接，channle对应的长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端连接，channle对应的短id为：" + ctx.channel().id().asShortText());
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel,所以下面的remove不用我们再手写
//      clients.remove(ctx.channel());
        System.out.println("客户端断开，channle对应的长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channle对应的短id为：" + ctx.channel().id().asShortText());
    }
}
