package com.violin.rpc.transport.nett4;

import com.violin.rpc.entity.RpcInvocation;
import com.violin.rpc.entity.RpcRequest;
import com.violin.rpc.entity.RpcResponse;
import com.violin.rpc.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author lin Date: 2019-04-07
 */
public class RpcCodecAdapter {
  // header length
  private static final int HEADER_LENGTH = 16;

  // magic header
  private static final short MAGIC = (short) 0xdabb;
  private static final byte MAGIC_HIGH = ByteUtil.shortToBytes(MAGIC)[0];
  private static final byte MAGIC_LOW = ByteUtil.shortToBytes(MAGIC)[1];

  // message flag
  private static final byte FLAG_REQUEST = (byte) 0x80;
  private static final byte FLAG_EVENT = (byte) 0x40;
  private static final byte SERIALIZATION_MASK = 0x07;

  private long id;
  // TODO 后续添加一个logger

  private final ChannelHandler encode = new InternalEncode();
  private final ChannelHandler decode = new InternalDecoder();

  public ChannelHandler getEncode() {
    return encode;
  }

  public ChannelHandler getDecode() {
    return decode;
  }

  private class InternalDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      int readable = in.readableBytes();
      byte[] header = new byte[Math.min(readable, HEADER_LENGTH)];
      in.readBytes(header);
      // check magic number
      if (readable > 0 && header[0] != MAGIC_HIGH || readable > 1 && header[1] != MAGIC_LOW) {
        int length = header.length;
        if (header.length < readable) {
          header = ByteUtil.copyOf(header, readable);
          in.readBytes(header, length, readable - length);
        }
        for (int i = 0; i < header.length - 1; i++) {
          if (header[i] == MAGIC_HIGH && header[i + 1] == MAGIC_LOW) {
            in.readerIndex(in.readerIndex() - header.length + i);
            header = ByteUtil.copyOf(header, i);
            break;
          }
        }
        // TODO 这边需要重新走一遍解析
      }

      // check length
      if (readable < HEADER_LENGTH) {
        // TODO 需要更多的数据
        return;
      }
      // get Data length.
      int len = ByteUtil.byte2int(header, 12);
      // 检查数据大小
      int tt = len + HEADER_LENGTH;
      if (readable < tt) {
        // TODO 需要更多数据
        return;
      }

      // 输入足够的数据
      try {
        out.add(decodeBody(ctx, in, len, header));
      } finally {
        ctx.close();
      }

    }

    private Object decodeBody(ChannelHandlerContext ctx, ByteBuf in, int len, byte[] header) {
      // TODO 获取序列化格式
      long id = ByteUtil.byte2long(header, 4);
      byte flag = header[2];
      if ((flag & FLAG_REQUEST) == 0) {
        // decode response
        RpcResponse resp = new RpcResponse(id);
        if ((flag & FLAG_EVENT) != 0) {
          resp.setEvent(RpcResponse.HEARTBEAT_EVENT);
        }
        // TODO 这块不做实现 get status
        int bodySize = ByteUtil.byte2int(header, 12);
        byte[] objectBytes = new byte[bodySize];
        in.readBytes(objectBytes);
        resp.setObject(readJavaSerializationObject(objectBytes));
        return resp;
      } else {
        // decode request
        RpcRequest req = new RpcRequest(id);
        if ((flag & FLAG_EVENT) != 0) {
          req.setEvent(RpcResponse.HEARTBEAT_EVENT);
        }

        int bodySize = ByteUtil.byte2int(header, 12);
        byte[] objectBytes = new byte[bodySize];
        in.readBytes(objectBytes);
        req.setObject(readJavaSerializationObject(objectBytes));
        return req;
      }
    }
  }

  private Object readJavaSerializationObject(byte[] objectBytes) {
    // TODO 考虑下这边的log怎么打
    ObjectInputStream objectInputStream = null;
    try {
      objectInputStream = new ObjectInputStream(new ByteArrayInputStream(objectBytes));
      RpcInvocation invocation = new RpcInvocation();
      String className = objectInputStream.readUTF();
      invocation.setClassName(className);
      String methodName = objectInputStream.readUTF();
      invocation.setMethodName(methodName);
      String paramType = objectInputStream.readUTF();
      invocation.setRequestType(paramType);
      invocation.setParameters(objectInputStream.readObject());
      return invocation;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (objectInputStream != null) {
        try {
          objectInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  private class InternalEncode extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
      if (msg instanceof RpcRequest) {
        // encode request
        RpcRequest request = (RpcRequest) msg;
        out.writeByte(MAGIC_HIGH);
        out.writeByte(MAGIC_LOW);
        byte flag = (byte) 0x0;
        flag |= FLAG_REQUEST;
        if (request.getEvent() == RpcRequest.HEART_BEAT_EVENT) {
          flag |= FLAG_EVENT;
        }
        out.writeByte(flag);
        out.writeByte(0x00);
        request.getObject();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] requestBody = writeJavaSerializationObject(baos, request);
        out.writeLong(request.getId());
        out.writeInt(requestBody.length);
        out.writeBytes(requestBody);
      } else {
        // encode response
        RpcResponse request = (RpcResponse) msg;
        out.writeByte(MAGIC_HIGH);
        out.writeByte(MAGIC_HIGH);
        byte flag = (byte) 0x0;
        flag |= FLAG_REQUEST;
        if (request.getEvent() == RpcRequest.HEART_BEAT_EVENT) {
          flag |= FLAG_EVENT;
        }
        out.writeByte(flag);
        out.writeByte(0x00);
        request.getObject();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(request.getObject());
        byte[] requestBody = baos.toByteArray();
        out.writeLong(request.getId());
        out.writeInt(requestBody.length);
        out.writeBytes(requestBody);
      }
    }

    private byte[] writeJavaSerializationObject(ByteArrayOutputStream baos, RpcRequest request) throws IOException {
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      RpcInvocation invocation = (RpcInvocation) request.getObject();
      oos.writeUTF(invocation.getClassName());
      oos.writeUTF(invocation.getMethodName());
      oos.writeUTF(invocation.getRequestType());
      oos.writeObject(invocation.getParameters());
      return baos.toByteArray();
    }
  }
}
