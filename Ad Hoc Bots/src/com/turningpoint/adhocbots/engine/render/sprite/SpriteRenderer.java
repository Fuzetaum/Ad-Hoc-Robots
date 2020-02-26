package com.turningpoint.adhocbots.engine.render.sprite;

import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;

import static org.lwjgl.opengl.GL11.*;

public class SpriteRenderer {
    public void draw() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glBegin(GL_QUADS);
            glTexCoord2f(0f, 0f);
            glVertex2f(0f, 0f);
            glTexCoord2f(0f, 1f);
            glVertex2f(0f, WindowConfigurations.getWindowHeightAsFloat());
            glTexCoord2f(1f, 1f);
            glVertex2f(WindowConfigurations.getWindowWidthAsFloat(), WindowConfigurations.getWindowHeightAsFloat());
            glTexCoord2f(1f, 0f);
            glVertex2f(WindowConfigurations.getWindowWidthAsFloat(), 0f);
        glEnd();
    }
}
