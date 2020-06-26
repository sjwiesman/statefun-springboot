package com.example.restservice;

import com.google.protobuf.Any;
import org.apache.flink.statefun.sdk.springboot.Context;
import org.apache.flink.statefun.sdk.springboot.annotations.StatefulFunction;
import org.apache.flink.statefun.sdk.springboot.annotations.StatefulFunctionController;
import org.apache.flink.statefun.springboot.Greeting;

@StatefulFunctionController(path = "/morefunctions")
public class OtherModule {

    @StatefulFunction("remote/bye")
    public void bye(Any message, Context ctx) {
        ctx.reply(Greeting.newBuilder().setGreeting("bye").build());
    }
}
