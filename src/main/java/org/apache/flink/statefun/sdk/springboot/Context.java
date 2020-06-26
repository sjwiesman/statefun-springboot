package org.apache.flink.statefun.sdk.springboot;

import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.core.polyglot.generated.Address;
import org.apache.flink.statefun.flink.core.polyglot.generated.Egress;

import java.time.Duration;
import java.util.Optional;

public interface Context {

    Address self();

    Address caller();

    <T extends Message> void send(Address target, T message);

    <T extends Message> void send(Egress target, T message);

    <T extends Message> void reply(T message);

    <T extends Message> void sendAfter(Address target, Duration duration, T message);

    <T extends Message> Optional<T> get(String name, Class<T> type);

    <T extends Message> void update(String name, T value);

    void clear(String name);
}
