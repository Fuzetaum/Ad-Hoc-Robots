package com.turningpoint.adhocbots.window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;

import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;
import com.turningpoint.adhocbots.window.util.ImageLoader;
import com.turningpoint.adhocbots.window.util.Texture;

public class RenderMainMenu {
	private static int wallpaperTexture, buttonStartTexture, buttonExitTexture;
	private static float buttonUpperEnd = (WindowConfigurations.getWindowHeightAsFloat()/5f)*4;
	private static float buttonLowerEnd = RenderMainMenu.buttonUpperEnd+100;
	private static float buttonStartLeftEnd = (WindowConfigurations.getWindowWidthAsFloat()/5f);
	private static float buttonStartRightEnd = RenderMainMenu.buttonStartLeftEnd+200;
	private static float buttonExitLeftEnd = (WindowConfigurations.getWindowWidthAsFloat()/5f)*3;
	private static float buttonExitRightEnd = RenderMainMenu.buttonExitLeftEnd+200;
	private static float mouseX, mouseY;
	private static boolean isMouseCallbackSet=false;
	
	private static void drawWallpaper() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image = ImageLoader.loadImage(Texture.TEXTURE_WALLPAPER_MAIN_MENU);
		RenderMainMenu.wallpaperTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
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
	
	private static void drawButtons() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image = ImageLoader.loadImage(Texture.TEXTURE_BUTTON_START);
		RenderMainMenu.buttonStartTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
		//Draw Start button
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderMainMenu.buttonStartLeftEnd, RenderMainMenu.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderMainMenu.buttonStartLeftEnd, RenderMainMenu.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderMainMenu.buttonStartRightEnd, RenderMainMenu.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderMainMenu.buttonStartRightEnd, RenderMainMenu.buttonUpperEnd);
		glEnd();
		//Draw Exit button
		image = ImageLoader.loadImage(Texture.TEXTURE_BUTTON_EXIT);
		RenderMainMenu.buttonExitTexture = ImageLoader.loadTexture(image);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderMainMenu.buttonExitLeftEnd, RenderMainMenu.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderMainMenu.buttonExitLeftEnd, RenderMainMenu.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderMainMenu.buttonExitRightEnd, RenderMainMenu.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderMainMenu.buttonExitRightEnd, RenderMainMenu.buttonUpperEnd);
		glEnd();
	}
	
	private static void setMouseClickListener(long gameWindow) {
		glfwSetCursorPosCallback(gameWindow, (window, xpos, ypos) -> {
			RenderMainMenu.mouseX = (float)xpos;
			RenderMainMenu.mouseY = (float)ypos;
		});
		glfwSetMouseButtonCallback(gameWindow, (window, button, action, mods) -> {
		 	if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE ) {
		 		//Check click inside both buttons' Y axis
		 		if(RenderMainMenu.mouseY >= RenderMainMenu.buttonUpperEnd
		 				&& RenderMainMenu.mouseY <= RenderMainMenu.buttonLowerEnd) {
			 		//Check click inside Start button
			 		if(RenderMainMenu.mouseX >= RenderMainMenu.buttonStartLeftEnd
			 				&& RenderMainMenu.mouseX <= RenderMainMenu.buttonStartRightEnd) {
			 			WindowSettings.setScreenMode(WindowSettings.SCREENMODE_INSTRUCTIONS);
			 			glfwFreeCallbacks(gameWindow);
			 			RenderMainMenu.isMouseCallbackSet = false;
			 		}
			 		//Check click inside Exit button
			 		else if(RenderMainMenu.mouseX >= RenderMainMenu.buttonExitLeftEnd
			 				&& RenderMainMenu.mouseX <= RenderMainMenu.buttonExitRightEnd)
			 			glfwSetWindowShouldClose(window, true);
		 		}
		 	}
		});
		RenderMainMenu.isMouseCallbackSet = true;
	}
	
	public static void draw(long window) {
		RenderMainMenu.drawWallpaper();
		RenderMainMenu.drawButtons();
		if(!RenderMainMenu.isMouseCallbackSet) RenderMainMenu.setMouseClickListener(window);
	}
}
