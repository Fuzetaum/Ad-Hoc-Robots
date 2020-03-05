package com.turningpoint.adhocbots.engine.render.window;

import com.turningpoint.adhocbots.engine.annotation.texture.TextureAnnotationProcessor;
import com.turningpoint.adhocbots.engine.render.sprite.SpriteRenderer;
import com.turningpoint.adhocbots.window.*;
import org.lwjgl.opengl.GL;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class WindowRenderer {
    private long window = -1L;
    private boolean running = true;
    private SpriteRenderer spriteRenderer;

    public void run(SpriteRenderer spriteRenderer) {
        this.spriteRenderer = spriteRenderer;
        try {
            this.initializeGame();
        } catch (Exception e) {
            System.out.println("Could not initialize game: an error happened");
            e.printStackTrace();
            System.exit(1);
        }
        // lastTime: time when last iteration took place
        long lastTime = System.nanoTime();
        // delta: portion of a frame that has already passed
        double elapsedFramePercentage = 0.0;
        // ns: amount of nanoseconds per frame
        double nsPerFrame = 1000000000.0/ WindowConfigurations.getFPS();
        // While-loop for rendering & updating screen
        while(this.running) {
            elapsedFramePercentage += (System.nanoTime() - lastTime) / nsPerFrame;
            lastTime = System.nanoTime();
            if(elapsedFramePercentage >= 1.0) {
                update();
                elapsedFramePercentage--;
            }
            render();
            if(glfwWindowShouldClose(window))
                this.running = false;
        }
    }

    private void initializeGame() throws NoSuchMethodException, InvalidClassException, IllegalAccessException, InvocationTargetException {
        System.out.print("Starting game window...");
        window = WindowSetup.init();
        System.out.println("finished");
        System.out.print("Loading textures...");
        Map<String, Integer> textureIds = new TextureAnnotationProcessor().loadTextures();
        System.out.println("finished");
        System.out.println(textureIds.toString());
    }

    private void render() {
//        GL.createCapabilities();
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glEnable(GL_BLEND);
        if(WindowSettings.getScreenMode().equals(WindowSettings.SCREENMODE_INGAME)) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Set the clear color to black
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            RenderGameMap.draw(window);
            RenderGameUI.draw(window);
        }
        else {
            glClearColor(1.0f, 1.0f, 1.0f, 0.0f); // Set the clear color to white
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        }
        //Check which screen type is being viewed right now - ingame screen view was already checked
        if(WindowSettings.getScreenMode().equals(WindowSettings.SCREENMODE_MENU)) RenderMainMenu.draw(this.window);
        else if(WindowSettings.getScreenMode().equals(WindowSettings.SCREENMODE_INSTRUCTIONS)) RenderInstructions.draw(this.window);
        spriteRenderer.renderSprites(this.window);
        glfwSwapBuffers(window);
    }

    private void update() {
        glfwPollEvents();
    }
}
