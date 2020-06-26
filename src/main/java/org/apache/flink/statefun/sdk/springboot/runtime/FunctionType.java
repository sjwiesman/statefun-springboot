package org.apache.flink.statefun.sdk.springboot.runtime;

import org.apache.flink.statefun.flink.core.polyglot.generated.Address;
import org.apache.flink.statefun.sdk.springboot.annotations.StatefulFunction;

import java.util.regex.Pattern;

class FunctionType {

    private static final Pattern PATTERN = Pattern.compile("(?<namespace>[a-zA-Z0-9]+)/(?<type>[a-zA-Z0-9]+)");

    static String fromAnnotation(StatefulFunction annotation) {
        if (!PATTERN.matcher(annotation.value()).matches()) {
            throw new RuntimeException("Invalid function type [" + annotation.value() + "]. FunctionTypes must be in the form <namespace>/<type>");
        }

        return annotation.value();
    }

    static String fromAddress(Address address) {
        return String.format("%s/%s", address.getNamespace(), address.getType());
    }
}
