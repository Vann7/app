package com.van.app.netty;

import com.van.app.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/8/8
 * Time: 10:31 AM
 */

@Component
public class TimeServer implements ApplicationRunner {

    @Value("${netty.server-port}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bind(port);
    }

    private void bind(Integer port) {
        /**
         * interface EventLoopGroup extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService
         * 配置服务端的 NIO 线程池,用于网络事件处理，实质上他们就是 Reactor 线程组
         * bossGroup 用于服务端接受客户端连接，workerGroup 用于进行 SocketChannel 网络读写
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /** ServerBootstrap 是 Netty 用于启动 NIO 服务端的辅助启动类，用于降低开发难度
             *  SO_BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
             *  用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50
             * */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            /**服务器启动辅助类配置完成后，调用 bind 方法绑定监听端口，调用 sync 方法同步等待绑定操作完成*/
            ChannelFuture f = b.bind(port).sync();

            System.out.println("Netty - 服务器开始监听端口，等待客户端连接.........");
//            System.out.println(Thread.currentThread().getName() + ",服务器开始监听端口，等待客户端连接.........");
            /**下面会进行阻塞，等待服务器连接关闭之后 main 方法退出，程序结束
             *
             * */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /**优雅退出，释放线程池资源*/
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
//            arg0.pipeline().addLast(new TimeServerHandler());
            /**
             * LineBasedFrameDecoder : 依次遍历ByteBuf中刻度字节，判断看是否有
             * "\n"或者"\r\n",如果有,就一次位置未结束为止，从可读索引到结束位置区间的字节组成了一行
             * StringDecoder : 将接收到的对象转换成字符串，然后继续调用后面的Handler
             * LineBasedFrameDecoder + StringDecoder组合就是按行切换的文本解码器
             * 它被设计用来支持TCP的粘包和拆包
             */
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new TimeServerHandler());
        }

    }


}
