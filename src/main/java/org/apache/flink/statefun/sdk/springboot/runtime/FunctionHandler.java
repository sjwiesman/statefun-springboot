package org.apache.flink.statefun.sdk.springboot.runtime;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import org.apache.flink.statefun.sdk.springboot.annotations.StatefulFunction;
import org.apache.flink.statefun.sdk.springboot.Context;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import static org.apache.flink.statefun.sdk.springboot.runtime.ProtoUtil.maybeUnpack;

class FunctionHandler {

    static FunctionHandler create(Object object, Method method) {
        StatefulFunction annotation = method.getAnnotation(StatefulFunction.class);
        String functionType = FunctionType.fromAnnotation(annotation);

        int numParameters = method.getParameterCount();
        if (numParameters != 2) {
            throw new IllegalStateException(annotation.value() + " must have two parameters");
        }

        Class<?>[] types = method.getParameterTypes();

        if (!Message.class.isAssignableFrom(types[0])) {
            throw new IllegalStateException(annotation.value() + " message type must be a Protobuf record");
        }

        @SuppressWarnings("unchecked")
        Class<? extends Message> messageType = (Class<? extends Message>) types[0];

        if (!Context.class.isAssignableFrom(types[1])) {
            throw new IllegalStateException(annotation.value() + " second parameters must type must be the context");
        }

        MethodHandle handle;
        try {
            handle = MethodHandles.lookup().unreflect(method).bindTo(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(annotation.value() + " must be declared public", e);
        }

        return new FunctionHandler(functionType, handle, messageType);
    }

    private final String functionType;

    private final MethodHandle handle;

    private final Class<? extends Message> messageType;

    FunctionHandler(String functionType, MethodHandle handle, Class<? extends Message> messageType) {
        this.functionType = functionType;
        this.handle = handle;
        this.messageType = messageType;
    }

    String getFunctionType() {
        return functionType;
    }

    void invoke(Any payload, Context ctx) {
        try {
            handle.invoke(maybeUnpack(payload, messageType), ctx);
        } catch (InvalidTypeException e) {
            throw new RuntimeException("Invalid message for function signature of " + functionType, e);
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to execute function " + functionType, throwable);
        }
    }
}
