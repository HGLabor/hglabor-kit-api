package de.hglabor.kitapi.platform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Environment {

    ModdingAPI moddingAPI();

    JVMLanguage language() default JVMLanguage.JAVA;
}
