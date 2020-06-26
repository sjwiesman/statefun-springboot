package org.apache.flink.statefun.sdk.springboot.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface StatefulFunctionController {

    /**
     * See {@link Component#value()}.
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

    String path();
}
