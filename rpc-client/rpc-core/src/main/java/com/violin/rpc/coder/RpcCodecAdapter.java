package com.violin.rpc.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author lin
 * Date: 2019-04-07
 */
public class RpcCodecAdapter {
    // header length
    private static final int HEADER_LENGTH = 16;

    // magic header
    private static final short MAGIC = (short) 0xdabb;

    // message flag
    private static final byte FLAG_REQUEST = (byte)0x80;
    private static final byte FLAG_EVENT = (byte)0x40;
    private static final byte SERIALIZATION_MASK = 0x07;

    //TODO 后续添加一个logger

    private final ChannelHandler encode = new InternalEncode();
    private final ChannelHandler decode = new InternalDecoder();

    private class InternalDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        }
    }

    private class InternalEncode extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        }
    }


}
