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

import com.turningpoint.adhocbots.engine.render.model.Sprite;
import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;
import com.turningpoint.adhocbots.window.util.ImageLoader;
import com.turningpoint.adhocbots.window.util.Texture;

public class RenderMainMenu {
	private static int wallpaperTexture, buttonStartTexture, buttonExitTexture;
	private static float buttonUpperEnd = (WindowConfigurations.getWindowHeightAsFloat()/5f)*4;
	private static float buttonStartLeftEnd = (WindowConfigurations.getWindowWidthAsFloat()/5f);
	private static float buttonExitLeftEnd = buttonStartLeftEnd*3;
	private static Sprite buttonStart = new Sprite(
			RenderMainMenu.buttonStartLeftEnd,
			RenderMainMenu.buttonStartLeftEnd+200,
			RenderMainMenu.buttonUpperEnd,
			RenderMainMenu.buttonUpperEnd+100,
			Textures.BUTTON_START.getName());
	private static Sprite buttonExit = new Sprite(
			RenderMainMenu.buttonExitLeftEnd,
			RenderMainMenu.buttonExitLeftEnd+200,
			RenderMainMenu.buttonUpperEnd,
			RenderMainMenu.buttonUpperEnd+100,
			Textures.BUTTON_EXIT.getName());
	private static float mouseX, mouseY;
	private static boolean isMouseCallbackSet=false;
	
	private static void drawWallpaper() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image = ImageLoader.loadImage(Texture.WALLPAPER_MAIN_MENU.getPath());
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
		BufferedImage image = ImageLoader.loadImage(Texture.BUTTON_START.getPath());
		RenderMainMenu.buttonStartTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
		//Draw Start button
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(buttonStart.getLeftCoordinate(), buttonStart.getTopCoordinate());
			glTexCoord2f(0f, 1f);
			glVertex2f(buttonStart.getLeftCoordinate(), buttonStart.getBottomCoordinate());
			glTexCoord2f(1f, 1f);
			glVertex2f(buttonStart.getRightCoordinate(), buttonStart.getBottomCoordinate());
			glTexCoord2f(1f, 0f);
			glVertex2f(buttonStart.getRightCoordinate(), buttonStart.getTopCoordinate());
		glEnd();
		//Draw Exit button
		image = ImageLoader.loadImage(Texture.BUTTON_EXIT.getPath());
		RenderMainMenu.buttonExitTexture = ImageLoader.loadTexture(image);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(buttonExit.getLeftCoordinate(), buttonExit.getTopCoordinate());
			glTexCoord2f(0f, 1f);
			glVertex2f(buttonExit.getLeftCoordinate(), buttonExit.getBottomCoordinate());
			glTexCoord2f(1f, 1f);
			glVertex2f(buttonExit.getRightCoordinate(), buttonExit.getBottomCoordinate());
			glTexCoord2f(1f, 0f);
			glVertex2f(buttonExit.getRightCoordinate(), buttonExit.getTopCoordinate());
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
		 				&& RenderMainMenu.mouseY <= buttonStart.getBottomCoordinate()) {
			 		//Check click inside Start button
			 		if(RenderMainMenu.mouseX >= RenderMainMenu.buttonStartLeftEnd
			 				&& RenderMainMenu.mouseX <= buttonStart.getRightCoordinate()) {
			 			WindowSettings.setScreenMode(WindowSettings.SCREENMODE_INSTRUCTIONS);
			 			glfwFreeCallbacks(gameWindow);
			 			RenderMainMenu.isMouseCallbackSet = false;
			 		}
			 		//Check click inside Exit button
			 		else if(RenderMainMenu.mouseX >= RenderMainMenu.buttonExitLeftEnd
			 				&& RenderMainMenu.mouseX <= buttonExit.getRightCoordinate())
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
