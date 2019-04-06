package com.violin.rpc.coder;

import com.violin.rpc.emtity.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()<4){
            return;
        }
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
