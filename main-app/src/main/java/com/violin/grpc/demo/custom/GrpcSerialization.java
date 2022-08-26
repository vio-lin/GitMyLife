package com.violin.grpc.demo.custom;

import io.grpc.MethodDescriptor;

public interface GrpcSerialization {

  /**
   *
   * @param T
   * @param <T>
   * @return
   */
  <T extends Object> MethodDescriptor.Marshaller<T> getMarshaller(Class<T> T);
}
