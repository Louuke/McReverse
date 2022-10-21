package org.jannsen.mcd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auth {

    enum Type {
        Basic,
        BasicBearer,
        Bearer;
    }

    Type type() default Type.Bearer;
}
