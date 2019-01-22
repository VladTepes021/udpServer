package com.udpserver.handlers;

import java.net.InetAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class IncommingPacketHandler extends  SimpleChannelInboundHandler<DatagramPacket> {

    public IncommingPacketHandler( ){

    }

//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
//        final InetAddress srcAddr = packet.sender().getAddress();
//        final ByteBuf buf = packet.content();
//        final int rcvPktLength = buf.readableBytes();
//        final byte[] rcvPktBuf = new byte[rcvPktLength];
//        buf.readBytes(rcvPktBuf);
//        System.out.println("Inside incomming packet handler");
//
//        //rcvPktProcessing(rcvPktBuf, rcvPktLength, srcAddr);
//    }

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, DatagramPacket packet) throws Exception {
		 final InetAddress srcAddr = packet.sender().getAddress();
	        final ByteBuf buf = packet.content();
	        final int rcvPktLength = buf.readableBytes();
	        final byte[] rcvPktBuf = new byte[rcvPktLength];
	        buf.readBytes(rcvPktBuf);
	        System.out.println("Inside incomming packet handler" + new String(rcvPktBuf));
		
	}
}
