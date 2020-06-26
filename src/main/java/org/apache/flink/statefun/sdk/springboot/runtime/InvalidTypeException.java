package org.apache.flink.statefun.sdk.springboot.runtime;

public class InvalidTypeException extends IllegalStateException {
    public InvalidTypeException(String expected, String actual) {
        super(String.format("Failed to unwrap protobuf Any; expected type %s but found %s", expected, actual));
    }
}
