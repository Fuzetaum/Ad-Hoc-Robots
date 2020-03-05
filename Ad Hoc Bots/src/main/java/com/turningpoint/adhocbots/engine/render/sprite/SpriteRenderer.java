package com.turningpoint.adhocbots.engine.render.sprite;

import com.turningpoint.adhocbots.engine.render.model.Sprite;
import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class SpriteRenderer {
    private static final Map<String, Integer> TEXTURE_ID_MAP = new HashMap<>();
    private static final List<Sprite> SPRITE_LIST = new ArrayList<>();

    public void draw(Sprite sprite) {
        glBegin(GL_QUADS);
            glTexCoord2f(0f, 0f);
            glVertex2f(sprite.getLeftCoordinate(), sprite.getTopCoordinate());
            glTexCoord2f(0f, 1f);
            glVertex2f(sprite.getLeftCoordinate(), sprite.getBottomCoordinate());
            glTexCoord2f(1f, 1f);
            glVertex2f(sprite.getRightCoordinate(), sprite.getBottomCoordinate());
            glTexCoord2f(1f, 0f);
            glVertex2f(sprite.getRightCoordinate(), sprite.getTopCoordinate());
        glEnd();
    }

    public void flushSprites() { SPRITE_LIST.clear(); }

    public void loadSprite(Sprite sprite) { SPRITE_LIST.add(sprite); }

    public void loadTexture(String name, String path) { TEXTURE_ID_MAP.put(name, TextureLoader.loadTexture(path)); }

    public void renderSprites(long window) {}

    public void setUpOpenGLForRendering() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
    }
}
