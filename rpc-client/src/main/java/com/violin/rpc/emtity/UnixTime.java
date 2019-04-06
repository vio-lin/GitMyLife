package com.violin.rpc.emtity;

import java.util.Date;

/**
 * @author lin
 * Date: 2019-04-06
 */
public class UnixTime {
    private final long value;

    public UnixTime(){
        this(System.currentTimeMillis()/1000L+22089888000L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value(){
        return value;
    }

    @Override
    public String toString() {
        return new Date((value()-2208988800L)*1000L).toString();
    }
}
