package com.turningpoint.adhocbots.window.util;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class ImageLoader {
	private static final int BYTES_PER_PIXEL = 4;
	
	public static int loadTexture(BufferedImage image) {
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
	
	public static BufferedImage loadImage(String loc) {
   	 BufferedImage image = null;
   	 File img = new File(loc);
   	 try {
			image = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
   	 return image;
    }
}
