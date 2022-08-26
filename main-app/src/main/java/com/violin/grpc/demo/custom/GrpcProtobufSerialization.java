package com.violin.grpc.demo.custom;

import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;

/**
 * @author guo.lin
 */
public class GrpcProtobufSerialization implements GrpcSerialization {
  private static Logger logger = LoggerFactory.getLogger(GrpcProtobufSerialization.class);
  private final static String SERIALIZATION_NAME = "GoogleProtobufSerialization";
  private final static Map<Class<?>, Object> DEFAULT_INSTANCE_MAP = new HashMap<>();
  private final static Map<Class<?>, MethodDescriptor.Marshaller> serializationMap = new ConcurrentHashMap<>();

  @Override
  public <T> MethodDescriptor.Marshaller getMarshaller(Class<T> clazz) {
    MethodDescriptor.Marshaller marshaller = serializationMap.computeIfAbsent(clazz, key -> {
      Object instance = DEFAULT_INSTANCE_MAP.computeIfAbsent(clazz, (instanceKey) -> getDefaultInstanceForType(instanceKey));
      if (!(instance instanceof Message)) {
        throw new IllegalStateException(String.format("can not find any Default Instance for [%s], please check if it GooglePb object", clazz.getName()));
      }
      return ProtoUtils.marshaller((Message) instance);
    });
    return marshaller;
  }

  private Object getDefaultInstanceForType(Class<?> clazz) {
    try {
      Method method = clazz.getDeclaredMethod("getDefaultInstance");
      return method.invoke(null);
    } catch (Throwable t) {
      logger.error("can not find any Default Instance for [{}], please check if it GooglePb object", clazz.getName(), t);
    }
    return null;
  }
}
