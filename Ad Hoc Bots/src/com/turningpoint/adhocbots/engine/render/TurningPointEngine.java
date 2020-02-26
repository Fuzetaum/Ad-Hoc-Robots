package com.turningpoint.adhocbots.engine.render;

import com.turningpoint.adhocbots.engine.render.sprite.SpriteRenderer;
import com.turningpoint.adhocbots.engine.render.window.WindowRenderer;

public abstract class TurningPointEngine {
    private static final WindowRenderer WINDOW_RENDERER = new WindowRenderer();
    private static final SpriteRenderer SPRITE_RENDERER = new SpriteRenderer();

    public static void startEngine() {
        WINDOW_RENDERER.initialize();
    }
}
