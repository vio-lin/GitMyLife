package com.violin.demo.api.service;

import com.violin.demo.api.DemoRequest;
import com.violin.demo.api.DemoResponse;
import com.violin.demo.api.DemoService;

/**
 * @author guo.lin  2019/4/15
 */
public class ServiceImpl implements DemoService {
  @Override
  public DemoResponse call(DemoRequest request) {
    DemoResponse response = new DemoResponse();
    response.setInstant(request.getInstant());
    response.setResponse("message form server :"+request.getReq());
    return response;
  }
}
