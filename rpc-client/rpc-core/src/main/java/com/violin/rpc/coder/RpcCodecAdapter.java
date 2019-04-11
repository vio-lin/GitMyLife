package com.violin.rpc.coder;

import com.violin.rpc.entity.RpcResponse;
import com.violin.rpc.util.ByteUtil;
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
    private static final byte MAGIC_HIGH = ByteUtil.shortToBytes(MAGIC)[0];
    private static final byte MAGIC_LOW = ByteUtil.shortToBytes(MAGIC)[1];

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
            int readable = in.readableBytes();
            byte[] header = new byte[Math.min(readable,HEADER_LENGTH)];
            in.readBytes(header);
            //check magic number
            if(readable>0 && header[0] != MAGIC||readable>1&&header[1]!=MAGIC_LOW){
                int length = header.length;
                if(header.length<readable){
                    header = ByteUtil.copyOf(header,readable);
                    in.readBytes(header,length,readable-length);
                }
                for (int i =0 ;i<header.length-1;i++){
                    if(header[i] ==MAGIC_HIGH&&header[i+1]==MAGIC_LOW){
                        in.readerIndex(in.readerIndex()-header.length+i);
                        header=ByteUtil.copyOf(header,i);
                        break;
                    }
                }
                // TODO 这边需要重新走一遍解析
            }

            // check length
            if(readable < HEADER_LENGTH){
                //TODO 需要更多的数据
            }
            // get Data length.
            int len = ByteUtil.byte2int(header,12);
            //检查数据大小
            int tt = len+HEADER_LENGTH;
            if(readable<tt){
                //TODO 需要更多数据
            }

            // 输入足够的数据
            try{
                decodeBody(ctx,in,len,header);
            }
            finally {
                ctx.close();
            }

        }

        private void decodeBody(ChannelHandlerContext ctx, ByteBuf in, int len, byte[] header) {
            //TODO 获取序列化格式
            long id = ByteUtil.byte2long(header,4);
            byte flag = header[2];
            if((flag&FLAG_REQUEST )== 0){
                //decode response
                RpcResponse resp = new RpcResponse(id);
                if((flag & FLAG_EVENT)!=0){
                   resp.setEvent(RpcResponse.HEARTBEAT_EVENT);
                }
                // get status

            }
        }
    }

    private class InternalEncode extends MessageToByteEncoder {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        }
    }


}
