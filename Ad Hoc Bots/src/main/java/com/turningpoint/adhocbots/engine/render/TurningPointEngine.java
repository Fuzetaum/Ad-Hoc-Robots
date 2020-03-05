package com.turningpoint.adhocbots.engine.render;

import com.turningpoint.adhocbots.engine.render.model.Sprite;
import com.turningpoint.adhocbots.engine.render.sprite.SpriteRenderer;
import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;
import com.turningpoint.adhocbots.engine.render.window.WindowRenderer;

import java.io.InvalidClassException;
import java.util.Map;

public abstract class TurningPointEngine {
    private static final WindowRenderer WINDOW_RENDERER = new WindowRenderer();
    private static final SpriteRenderer SPRITE_RENDERER = new SpriteRenderer();

    public static void addSprite(Sprite sprite) { SPRITE_RENDERER.loadSprite(sprite); }

    public static void flushSprites() { SPRITE_RENDERER.flushSprites(); }

    public static void loadTextures(Map<String, String> textures) { textures.forEach(SPRITE_RENDERER::loadTexture); }

    public static void startEngine() {
        WINDOW_RENDERER.run(SPRITE_RENDERER);
    }
}
