package com.violin.practise.currency;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;
import java.math.BigInteger;

/**
 * 线程安全的并且没有使用同步类的servlet
 *
 * @author guo.lin
 */
public class ThreadServlet {
  private BigInteger lastNumber;
  private BigInteger[] lastFactors;
  private long hits;
  private long cacheHits;

  public synchronized long getHits() {
    return hits;
  }

  public synchronized double getCacheHitRatio() {
    return (double) cacheHits / (double) hits;
  }

  public void service(ServletRequest request, ServletResponse response) {
    BigInteger i = getFromRequest(request);
    BigInteger[] factors = null;
    synchronized (this) {
      ++hits;
      if (i.equals(lastNumber)) {
        ++cacheHits;
        factors = lastFactors.clone();
      }
    }

    if (factors == null) {
      factors = factor(i);
      synchronized (this) {
        lastNumber = i;
        lastFactors = factors.clone();
      }
      encodeIntoResponse(response, factors);
    }
  }

  private void encodeIntoResponse(ServletResponse response, BigInteger[] factors) {
    try {
      response.getWriter().write(factors.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private BigInteger[] factor(BigInteger i) {
    return new BigInteger[0];
  }

  private BigInteger getFromRequest(ServletRequest request) {
    return new BigInteger("someNumber");
  }
}
