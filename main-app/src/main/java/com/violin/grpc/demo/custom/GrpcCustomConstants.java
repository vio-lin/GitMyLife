package com.violin.grpc.demo.custom;

import io.grpc.Context;
import io.grpc.Metadata;

public class GrpcCustomConstants {
  public static final Context.Key<String> CONTEXT_KEY = Context.key("custom_marsher");
  public static Metadata.Key<String> X_CONTENT_HEAD = Metadata.Key.of("x-content-type", Metadata.ASCII_STRING_MARSHALLER);
}