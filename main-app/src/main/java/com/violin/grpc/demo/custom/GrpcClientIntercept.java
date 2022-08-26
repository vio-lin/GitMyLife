package com.violin.grpc.demo.custom;

import io.grpc.*;
import javax.annotation.Nullable;
import static com.violin.grpc.demo.custom.GrpcCustomConstants.CONTEXT_KEY;
import static com.violin.grpc.demo.custom.GrpcCustomConstants.X_CONTENT_HEAD;

public class GrpcClientIntercept implements ClientInterceptor {
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    String codeString = CodeContext.getContext().getCodeString();
    Context context = Context.current().withValue(CONTEXT_KEY, codeString);
    Context pre = context.attach();
    ClientCall<ReqT, RespT> wrapperCall = next.newCall(method, callOptions);
    context.detach(pre);
    return new WrappedForwardingClientCall(wrapperCall,context);
  }

  static class WrappedForwardingClientCall extends ForwardingClientCall.SimpleForwardingClientCall {
    private Context context;
    private String code;
    protected WrappedForwardingClientCall(ClientCall delegate,Context context) {
      super(delegate);
      this.context =context;
      this.code = CodeContext.getContext().getCodeString();
    }

    @Override
    public void start(Listener responseListener, Metadata headers) {
      headers.put(X_CONTENT_HEAD,code);
      super.start(responseListener, headers);
    }

    @Override
    public void sendMessage(Object message) {
      context.run(()->{
        super.sendMessage(message);
      });
    }

    @Override
    public void request(int numMessages) {
      super.request(numMessages);
    }

    @Override
    public void cancel(@Nullable String message, @Nullable Throwable cause) {
      super.cancel(message, cause);
    }

    @Override
    public void halfClose() {
      super.halfClose();
    }

    @Override
    public void setMessageCompression(boolean enabled) {
      super.setMessageCompression(enabled);
    }

    @Override
    public boolean isReady() {
      return super.isReady();
    }

    @Override
    public Attributes getAttributes() {
      return super.getAttributes();
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }
}
