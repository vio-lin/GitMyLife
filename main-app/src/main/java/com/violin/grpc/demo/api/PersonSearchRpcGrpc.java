package com.violin.grpc.demo.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

import com.violin.grpc.demo.custom.CustomMarshaller;

/**
 * <pre>
 * Interface exported by the server.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.0)",
    comments = "Source: Test.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PersonSearchRpcGrpc {

  private PersonSearchRpcGrpc() {}

  public static final String SERVICE_NAME = "PersonSearchRpc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<PersonRequest,
      PersonResponse> getGetUserInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserInfo",
      requestType = PersonRequest.class,
      responseType = PersonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<PersonRequest,
      PersonResponse> getGetUserInfoMethod() {
    io.grpc.MethodDescriptor<PersonRequest, PersonResponse> getGetUserInfoMethod;
    if ((getGetUserInfoMethod = PersonSearchRpcGrpc.getGetUserInfoMethod) == null) {
      synchronized (PersonSearchRpcGrpc.class) {
        if ((getGetUserInfoMethod = PersonSearchRpcGrpc.getGetUserInfoMethod) == null) {
          PersonSearchRpcGrpc.getGetUserInfoMethod = getGetUserInfoMethod =
              io.grpc.MethodDescriptor.<PersonRequest, PersonResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUserInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(new CustomMarshaller<>(PersonRequest.class))
              .setResponseMarshaller(new CustomMarshaller<>(PersonResponse.class))
              .setSchemaDescriptor(new PersonSearchRpcMethodDescriptorSupplier("getUserInfo"))
              .build();
        }
      }
    }
    return getGetUserInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<PersonRequestPojo,
          PersonResponsePojo> getGetUserInfoPojoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserInfoPojo",
      requestType = PersonRequestPojo.class,
      responseType = PersonResponsePojo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<PersonRequestPojo,
          PersonResponsePojo> getGetUserInfoPojoMethod() {
    io.grpc.MethodDescriptor<PersonRequestPojo, PersonResponsePojo> getGetUserInfoPojoMethod;
    if ((getGetUserInfoPojoMethod = PersonSearchRpcGrpc.getGetUserInfoPojoMethod) == null) {
      synchronized (PersonSearchRpcGrpc.class) {
        if ((getGetUserInfoPojoMethod = PersonSearchRpcGrpc.getGetUserInfoPojoMethod) == null) {
          PersonSearchRpcGrpc.getGetUserInfoPojoMethod = getGetUserInfoPojoMethod =
              io.grpc.MethodDescriptor.<PersonRequestPojo, PersonResponsePojo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUserInfoPojo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(new CustomMarshaller<>(PersonRequestPojo.class))
              .setResponseMarshaller(new CustomMarshaller<>(PersonResponsePojo.class))
              .setSchemaDescriptor(new PersonSearchRpcMethodDescriptorSupplier("getUserInfoPojo"))
              .build();
        }
      }
    }
    return getGetUserInfoPojoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PersonSearchRpcStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcStub>() {
        @Override
        public PersonSearchRpcStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonSearchRpcStub(channel, callOptions);
        }
      };
    return PersonSearchRpcStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PersonSearchRpcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcBlockingStub>() {
        @Override
        public PersonSearchRpcBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonSearchRpcBlockingStub(channel, callOptions);
        }
      };
    return PersonSearchRpcBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PersonSearchRpcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PersonSearchRpcFutureStub>() {
        @Override
        public PersonSearchRpcFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PersonSearchRpcFutureStub(channel, callOptions);
        }
      };
    return PersonSearchRpcFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static abstract class PersonSearchRpcImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUserInfo(PersonRequest request,
                            io.grpc.stub.StreamObserver<PersonResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserInfoMethod(), responseObserver);
    }

    /**
     */
    public void getUserInfoPojo(PersonRequestPojo request,
                                io.grpc.stub.StreamObserver<PersonResponsePojo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserInfoPojoMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                PersonRequest,
                PersonResponse>(
                  this, METHODID_GET_USER_INFO)))
          .addMethod(
            getGetUserInfoPojoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                      PersonRequestPojo,
                      PersonResponsePojo>(
                  this, METHODID_GET_USER_INFO_POJO)))
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class PersonSearchRpcStub extends io.grpc.stub.AbstractAsyncStub<PersonSearchRpcStub> {
    private PersonSearchRpcStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonSearchRpcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonSearchRpcStub(channel, callOptions);
    }

    /**
     */
    public void getUserInfo(PersonRequest request,
                            io.grpc.stub.StreamObserver<PersonResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserInfoPojo(PersonRequestPojo request,
                                io.grpc.stub.StreamObserver<PersonResponsePojo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserInfoPojoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class PersonSearchRpcBlockingStub extends io.grpc.stub.AbstractBlockingStub<PersonSearchRpcBlockingStub> {
    private PersonSearchRpcBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonSearchRpcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonSearchRpcBlockingStub(channel, callOptions);
    }

    /**
     */
    public PersonResponse getUserInfo(PersonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public PersonResponsePojo getUserInfoPojo(PersonRequestPojo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserInfoPojoMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class PersonSearchRpcFutureStub extends io.grpc.stub.AbstractFutureStub<PersonSearchRpcFutureStub> {
    private PersonSearchRpcFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PersonSearchRpcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PersonSearchRpcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PersonResponse> getUserInfo(
        PersonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PersonResponsePojo> getUserInfoPojo(
        PersonRequestPojo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserInfoPojoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_INFO = 0;
  private static final int METHODID_GET_USER_INFO_POJO = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PersonSearchRpcImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PersonSearchRpcImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_INFO:
          serviceImpl.getUserInfo((PersonRequest) request,
              (io.grpc.stub.StreamObserver<PersonResponse>) responseObserver);
          break;
        case METHODID_GET_USER_INFO_POJO:
          serviceImpl.getUserInfoPojo((PersonRequestPojo) request,
              (io.grpc.stub.StreamObserver<PersonResponsePojo>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PersonSearchRpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PersonSearchRpcBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return DemoGrpcService.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PersonSearchRpc");
    }
  }

  private static final class PersonSearchRpcFileDescriptorSupplier
      extends PersonSearchRpcBaseDescriptorSupplier {
    PersonSearchRpcFileDescriptorSupplier() {}
  }

  private static final class PersonSearchRpcMethodDescriptorSupplier
      extends PersonSearchRpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PersonSearchRpcMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PersonSearchRpcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PersonSearchRpcFileDescriptorSupplier())
              .addMethod(getGetUserInfoMethod())
              .addMethod(getGetUserInfoPojoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
