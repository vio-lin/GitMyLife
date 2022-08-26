package com.violin.grpc.demo.custom;

import io.grpc.MethodDescriptor;
import static com.violin.grpc.demo.custom.GrpcCustomConstants.CONTEXT_KEY;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class  CustomMarshaller<T> implements MethodDescriptor.Marshaller<T> {
  private static final Map<String, GrpcSerialization> SERIALIZATION_MAP = new HashMap<>();
  private final Class<T> clazz;

  public CustomMarshaller(Class<T> clazz){
    this.clazz = clazz;
  }

  static{
    SERIALIZATION_MAP.put("protobuf",new GrpcProtobufSerialization());
    SERIALIZATION_MAP.put("json",new GsonGrpcSerialization());
  }

  @Override
  public InputStream stream(T value) {
    String usedMarshallerName = CONTEXT_KEY.get();
    GrpcSerialization grpcSerialization = SERIALIZATION_MAP.get(usedMarshallerName);
    return grpcSerialization.getMarshaller(clazz).stream(value);
  }

  @Override
  public T parse(InputStream stream) {
    String serialization = CONTEXT_KEY.get();
    GrpcSerialization grpcSerialization = SERIALIZATION_MAP.get(serialization);
    if(grpcSerialization == null){
      throw new IllegalArgumentException("can not find any serialization [" + serialization+ "] by content type in header.");
    }
    return grpcSerialization.getMarshaller(clazz).parse(stream);
  }
}
