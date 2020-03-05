package com.turningpoint.adhocbots.window.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public enum Texture {
    FONT ("FONT", "resources/font.ttf"),
    BUTTON_START ("BUTTON_START", "resources/button/button-start.png"),
    BUTTON_EXIT ("BUTTON_EXIT", "resources/button/button-exit.png"),
    BUTTON_NEXT ("BUTTON_NEXT", "resources/button/button-next.png"),
    BUTTON_PREVIOUS ("BUTTON_PREVIOUS", "resources/button/button-previous.png"),
    BUTTON_SKIP ("BUTTON_SKIP", "resources/button/button-skip.png"),
    ROBOT ("ROBOT", "resources/entity/robot-down.png"),
    ROBOT_SELECTED ("ROBOT_SELECTED", "resources/entity/robot-down-selected.png"),
    COMMAND_CENTER ("COMMAND_CENTER", "resources/entity/command-center.png"),
    MONEY_SERVER ("MONEY_SERVER", "resources/entity/money-server.png"),
    WRECKED_BUILDING ("WRECKED_BUILDING", "resources/entity/wrecked-building.png"),
    HARDWARE_FACTORY ("HARDWARE_FACTORY", "resources/entity/hardware-factory.png"),
    GROUND_GRASS ("GROUND_GRASS", "resources/map/ground-grass.png"),
    FRAME ("FRAME", "resources/ui/frame.png"),
    FRAME_BUILDING ("FRAME_BUILDING", "resources/ui/frame-building.png"),
    FRAME_ROBOT ("FRAME_ROBOT", "resources/ui/frame-robot.png"),
    FRAME_RESOURCES ("FRAME_RESOURCES", "resources/ui/frame-resources.png"),
    WALLPAPER_INSTRUCTION_1 ("WALLPAPER_INSTRUCTION_1", "resources/wallpaper/instruction-1.png"),
    WALLPAPER_INSTRUCTION_2 ("WALLPAPER_INSTRUCTION_2", "resources/wallpaper/instruction-2.png"),
    WALLPAPER_INSTRUCTION_3 ("WALLPAPER_INSTRUCTION_3", "resources/wallpaper/instruction-3.png"),
    WALLPAPER_MAIN_MENU ("WALLPAPER_MAIN_MENU", "resources/wallpaper/main-splash.png");

    private static final int BYTES_PER_PIXEL = 4;

    private String name;
    private String path;

    Texture(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int loadTexture() {
        BufferedImage image = loadImage(this.path);
        byte R, G, B, A;
        if(image == null) throw new IllegalArgumentException("Image is null");
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                R = ((byte) ((pixel >> 16) & 0xFF));
                buffer.put(R);     // Red component
                G = ((byte) ((pixel >> 8) & 0xFF));
                buffer.put(G);      // Green component
                B = ((byte) (pixel & 0xFF));
                buffer.put(B);               // Blue component
                //Sets magenta as chroma key
                if((R & 0xFF) == 255 && ((G & 0xFF) == 0) && (B & 0xFF) == 255)
                    A = ((byte) (0 & 0xFF));
                else A = ((byte) ((pixel >> 24) & 0xFF));
                buffer.put(A);    // Alpha component. Only for RGBA
            }
        }
        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
        int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenTextures();
        glEnable(GL_TEXTURE_2D);
        return textureID;
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        File file = new File(path);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
