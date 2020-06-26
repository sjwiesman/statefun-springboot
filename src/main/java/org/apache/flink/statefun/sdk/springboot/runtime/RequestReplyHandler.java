package org.apache.flink.statefun.sdk.springboot.runtime;

import org.apache.flink.statefun.flink.core.polyglot.generated.FromFunction;
import org.apache.flink.statefun.flink.core.polyglot.generated.ToFunction;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.validation.constraints.NotNull;

public class RequestReplyHandler implements HandlerFunction<ServerResponse> {

    @NonNull
    private final ModuleHandler moduleHandler;

    public RequestReplyHandler(@NonNull ModuleHandler moduleHandler) {
        this.moduleHandler = moduleHandler;
    }

    @Override
    public ServerResponse handle(ServerRequest request) throws Exception {

        ToFunction toFunction = ToFunction.parseFrom(request.body(byte[].class));
        FromFunction response = moduleHandler.handle(toFunction);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response.toByteArray());
    }

    @Override
    public String toString() {
        return "RequestReplyHandler{" +
                "moduleHandler=" + moduleHandler +
                '}';
    }
}
