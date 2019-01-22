/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udpserver;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.udpserver.handlers.IncommingPacketHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Spring Java Configuration and Bootstrap
 *
 * @author Jibeom Jung
 */
@SpringBootApplication
public class Application {

    

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        run();

    }

//    @Bean(name = "serverBootstrap")
//    public ServerBootstrap bootstrap() {
//        ServerBootstrap b = new ServerBootstrap();
//        b.group(bossGroup(), workerGroup())
//                .channel(NioServerSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.DEBUG))
//                .childHandler(somethingChannelInitializer);
//        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
//        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
//        for (@SuppressWarnings("rawtypes") ChannelOption option : keySet) {
//            b.option(option, tcpChannelOptions.get(option));
//        }
//        return b;
//    }
    public static void run() throws Exception {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                @Override
                public void initChannel(final NioDatagramChannel ch) throws Exception {

                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new IncommingPacketHandler());
                }
            });

            // Bind and start to accept incoming connections.
            Integer pPort = 8091;
            InetAddress address  = InetAddress.getLocalHost();
            System.out.printf("waiting for message %s %s",String.format(pPort.toString()),String.format( address.toString()));
            b.bind(address,8091).sync().channel().closeFuture().await();

        } finally {
        System.out.print("In Server Finally");
        }
    }

   



//    @Bean(destroyMethod = "shutdownGracefully")
//    public NioEventLoopGroup bossGroup() {
//        return new NioEventLoopGroup(nettyProperties.getBossCount());
//    }
//
//    @Bean(destroyMethod = "shutdownGracefully")
//    public NioEventLoopGroup workerGroup() {
//        return new NioEventLoopGroup(nettyProperties.getWorkerCount());
//    }
//
//    @Bean
//    public InetSocketAddress tcpSocketAddress() {
//        return new InetSocketAddress(nettyProperties.getTcpPort());
//    }

   

}