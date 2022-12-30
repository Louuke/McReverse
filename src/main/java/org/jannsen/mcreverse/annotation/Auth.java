package org.jannsen.mcreverse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auth {

    enum Type {
        Basic("Basic"),
        BasicBearer("Bearer"),
        Bearer("Bearer");

        private final String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String prefix() {
            return prefix;
        }
    }

    Type type() default Type.Bearer;
}
