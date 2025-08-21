package dev.selena.luacore.annotations.gson;

import jdk.jfr.Experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Experimental()
/**
 * Annotation to add comments to fields in a class that will be serialized to JSON.
 * The value of the annotation will be used as the comment in the generated JSON.
 */
public @interface Comment {
    String value();
}
