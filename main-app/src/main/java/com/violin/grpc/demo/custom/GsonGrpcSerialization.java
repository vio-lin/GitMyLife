package com.violin.grpc.demo.custom;

import io.grpc.MethodDescriptor;
import io.grpc.Status;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;


/**
 * @author guo.lin
 */
public class GsonGrpcSerialization implements GrpcSerialization {
  private static final Gson SERIALIZATION_INSTANCE = new Gson();
  private static final Map<Class<?>, MethodDescriptor.Marshaller> marshallerMap = new ConcurrentHashMap<>();

  @Override
  public <T> MethodDescriptor.Marshaller<T> getMarshaller(Class<T> clazz) {
    MethodDescriptor.Marshaller marshaller = marshallerMap.computeIfAbsent(clazz, (key) -> {
      return new MethodDescriptor.Marshaller<T>() {
        @Override
        public InputStream stream(T value) {
          try {
            return new ByteArrayInputStream(SERIALIZATION_INSTANCE.toJson(value).getBytes("utf-8"));
          } catch (UnsupportedEncodingException e) {
            throw Status.INTERNAL
                    .withCause(e)
                    .withDescription("Unable to print json proto")
                    .asRuntimeException();
          }
        }

        @Override
        public T parse(InputStream stream) {
          return (T) SERIALIZATION_INSTANCE.fromJson(new InputStreamReader(stream), clazz);
        }
      };
    });
    return marshaller;
  }
}
