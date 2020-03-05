package com.turningpoint.adhocbots.engine.annotation.texture;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TextureLoader {
    public String methodName() default "loadTextures";
}
