package com.violin.rpc.util;

/**
 * @author lin
 * Date: 2019-04-11
 */
public class ByteUtil {
    private ByteUtil(){}

    /**
     * <<      :     左移运算符，num << 1,相当于num乘以2
     * >>      :     右移运算符，num >> 1,相当于num除以2
     * >>>    :     无符号右移，忽略符号位，空位都以0补齐
     */
    public static byte[] shortToBytes(short value){
        byte[] bytes = {0,0};
        bytes[0] = (byte)value;
        bytes[1] = (byte)(value>>>8);
        return  bytes;
    }

    /**
     *
     * @param src
     * @param length
     * @return
     */
    public static byte[] copyOf(byte[] src, int length) {
        byte[] dest = new byte[length];
        System.arraycopy(src,0,dest,0,Math.min(src.length,length));
        return  dest;
    }

    public static int byte2int(byte[] bytes, int off) {
        return ((bytes[off+3]&0xFF)<<0)+
                ((bytes[off+2]&0xFF)<<8)+
                ((bytes[off+1]&0xFF)<<16)+
                ((bytes[off+0]&0xFF)<<24);
    }

    public static long byte2long(byte[] bytes, int off) {
        return ((bytes[off + 7] & 0xFFL) << 0) +
                ((bytes[off + 6] & 0xFFL) << 8) +
                ((bytes[off + 5] & 0xFFL) << 16) +
                ((bytes[off + 4] & 0xFFL) << 24) +
                ((bytes[off + 3] & 0xFFL) << 32) +
                ((bytes[off + 2] & 0xFFL) << 40) +
                ((bytes[off + 1] & 0xFFL) << 48) +
                (((long) bytes[off + 0]) << 56);
    }
}
