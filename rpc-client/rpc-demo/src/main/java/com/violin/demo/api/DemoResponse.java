package com.violin.demo.api;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author lin
 * Date: 2019-04-07
 */
public class DemoResponse implements Serializable {
    String response;
    Instant instant;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
