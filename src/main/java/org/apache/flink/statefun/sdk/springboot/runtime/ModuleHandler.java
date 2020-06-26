package org.apache.flink.statefun.sdk.springboot.runtime;

import org.apache.flink.statefun.flink.core.polyglot.generated.FromFunction;
import org.apache.flink.statefun.flink.core.polyglot.generated.ToFunction;

import java.util.Map;

class ModuleHandler  {

    private final String path;

    private final Map<String, FunctionHandler> handlersByType;

    private final BatchContext ctx;

    ModuleHandler(String path, Map<String, FunctionHandler> handlersByType) {
        this.path = path;
        this.handlersByType = handlersByType;
        this.ctx = new BatchContext();
    }

    String getPath() {
        return path;
    }

    FromFunction handle(ToFunction request) {
        ToFunction.InvocationBatchRequest invocation = request.getInvocation();

        String functionType = FunctionType.fromAddress(invocation.getTarget());
        FunctionHandler handler = handlersByType.get(functionType);

        ctx.setup(invocation.getTarget(), invocation.getStateList());

        for (ToFunction.Invocation payload : invocation.getInvocationsList()) {
            ctx.setCaller(payload.getCaller());
            handler.invoke(payload.getArgument(), ctx);
        }

        FromFunction.InvocationResponse response = ctx.getResponse();
        return FromFunction.newBuilder().setInvocationResult(response).build();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ModuleHandler[");

        String functions = String.join(", ", handlersByType.keySet());

        return builder.append(functions).append("]").toString();
    }
}
