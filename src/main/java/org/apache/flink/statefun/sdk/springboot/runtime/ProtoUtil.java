package org.apache.flink.statefun.sdk.springboot.runtime;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

class ProtoUtil {

    @SuppressWarnings("unchecked")
    static <T extends Message> T maybeUnpack(Any value, Class<T> type) throws InvalidTypeException, InvalidProtocolBufferException {
        if (Any.class.isAssignableFrom(type)) {
            return (T) value;
        }

        if (!value.is(type)) {
            throw new InvalidTypeException(type.getName(), value.getTypeUrl());
        }

        return value.unpack(type);
    }

    static <T extends Message> Any maybePack(T value) {
        if (value instanceof Any) {
            return (Any) value;
        }

        return Any.pack(value);
    }
}
