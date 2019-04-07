package com.violin.demo.api;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author lin
 * Date: 2019-04-07
 */
public class DemoRequest implements Serializable {
    String req;
    Instant instant;

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
