package com.turningpoint.adhocbots.engine.annotation.texture;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TextureAnnotationProcessor {
    private Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(""))
                .setScanners(new TypeElementsScanner(), new SubTypesScanner(), new TypeAnnotationsScanner())
    );

    public Map<String, Integer> loadTextures() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InvalidClassException {
        String ENUM_FIELD_NAME_PATH = "getPath";
        String ENUM_FIELD_NAME_NAME = "getName";
        Map<String, Integer> textureIds = new HashMap<>();

        Set<Class<?>> classes = this.reflections.getTypesAnnotatedWith(TextureLoader.class);
        for (Class<?> textureLoadingClass : classes) {
            if (!textureLoadingClass.isEnum())
                throw new InvalidClassException(String.format("Class %s is expected to be declared as \"enum\"", textureLoadingClass.getName()));
            for (Enum<?> textureDefinition : ((Enum[])textureLoadingClass.getEnumConstants())) {
                Integer textureId = com.turningpoint.adhocbots.engine.render.sprite.TextureLoader.loadTexture(
                        "src/main/resources/" + textureLoadingClass.getDeclaredMethod(ENUM_FIELD_NAME_PATH).invoke(textureDefinition));
                textureIds.put(
                        (String) textureLoadingClass.getDeclaredMethod(ENUM_FIELD_NAME_NAME).invoke(textureDefinition),
                        textureId);
            }
        }
        return textureIds;
    }
}
