package com.turningpoint.adhocbots.window;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
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

public class RenderInstructions {
	private static int wallpaperTexture, buttonPreviousTexture, buttonExitTexture, buttonSkipTexture, buttonNextTexture;
	private static int exampleRobotTexture, exampleCommCenterTexture, exampleMoneyServerTexture;
	//Buttons location variables
	private static float buttonUpperEnd = ((WindowConfigurations.getWindowHeightAsFloat()/5f)*4)+30f;
	private static float buttonLowerEnd = RenderInstructions.buttonUpperEnd+100f;
	private static float buttonPreviousLeftEnd = 30f;
	private static float buttonPreviousRightEnd = RenderInstructions.buttonPreviousLeftEnd
			+ ((WindowConfigurations.getWindowWidthAsFloat()-120f)/4f);
	private static float buttonExitLeftEnd = RenderInstructions.buttonPreviousRightEnd + 20f;
	private static float buttonExitRightEnd = RenderInstructions.buttonExitLeftEnd
			+ ((WindowConfigurations.getWindowWidthAsFloat()-120f)/4f);
	private static float buttonSkipLeftEnd = RenderInstructions.buttonExitRightEnd + 20f;
	private static float buttonSkipRightEnd = RenderInstructions.buttonSkipLeftEnd
			+ ((WindowConfigurations.getWindowWidthAsFloat()-120f)/4f);
	private static float buttonNextLeftEnd = RenderInstructions.buttonSkipRightEnd + 20f;
	private static float buttonNextRightEnd = WindowConfigurations.getWindowWidthAsFloat()-30f;
	//Entity location variables
	//Page 1 entities
	private static float entityRobotUpperEnd = 240f;
	private static float entityRobotLowerEnd = RenderInstructions.entityRobotUpperEnd + 50f;
	private static float entityRobotLeftEnd = 300f;
	private static float entityRobotRightEnd = RenderInstructions.entityRobotLeftEnd + 27f;
	private static float entityCommCenterUpperEnd = 210f;
	private static float entityCommCenterLowerEnd = RenderInstructions.entityCommCenterUpperEnd + 109f;
	private static float entityCommCenterLeftEnd = 600f;
	private static float entityCommCenterRightEnd = RenderInstructions.entityCommCenterLeftEnd + 100f;
	//Page 2 entities
	private static float entityMoneyServerUpperEnd = 280f;
	private static float entityMoneyServerLowerEnd = RenderInstructions.entityMoneyServerUpperEnd + 90f;
	private static float entityMoneyServerLeftEnd = 475f;
	private static float entityMoneyServerRightEnd = RenderInstructions.entityMoneyServerLeftEnd + 68f;
	//Page 3 entities
	private static float entityWreckedBuildingUpperEnd = 230f;
	private static float entityWreckedBuildingLowerEnd = RenderInstructions.entityWreckedBuildingUpperEnd + 82f;
	private static float entityWreckedBuildingLeftEnd = 465f;
	private static float entityWreckedBuildingRightEnd = RenderInstructions.entityWreckedBuildingLeftEnd + 82f;
	private static float entityHardwareFactoryUpperEnd = 510f;
	private static float entityHardwareFactoryLowerEnd = RenderInstructions.entityHardwareFactoryUpperEnd + 90f;
	private static float entityHardwareFactoryLeftEnd = 475f;
	private static float entityHardwareFactoryRightEnd = RenderInstructions.entityHardwareFactoryLeftEnd + 65f;
	//Mouse position containers and utility flags
	private static float mouseX, mouseY;
	private static boolean isMouseCallbackSet=false;
	private static Integer instructionPage = 1;
	
	private static void drawWallpaper() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		//Check which instruction page should be printed
		switch(RenderInstructions.instructionPage) {
		case 1:
			image = ImageLoader.loadImage(Texture.WALLPAPER_INSTRUCTION_1.getPath());
			break;
		case 2:
			image = ImageLoader.loadImage(Texture.WALLPAPER_INSTRUCTION_2.getPath());
			break;
		case 3:
			image = ImageLoader.loadImage(Texture.WALLPAPER_INSTRUCTION_3.getPath());
			break;
		default:
			image = ImageLoader.loadImage(Texture.WALLPAPER_INSTRUCTION_1.getPath());
		}
		RenderInstructions.wallpaperTexture = ImageLoader.loadTexture(image);
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
		BufferedImage image = ImageLoader.loadImage(Texture.BUTTON_PREVIOUS.getPath());
		RenderInstructions.buttonPreviousTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
		//Draw Previous button
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderInstructions.buttonPreviousLeftEnd, RenderInstructions.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderInstructions.buttonPreviousLeftEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderInstructions.buttonPreviousRightEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderInstructions.buttonPreviousRightEnd, RenderInstructions.buttonUpperEnd);
		glEnd();
		//Draw Exit button
		image = ImageLoader.loadImage(Texture.BUTTON_EXIT.getPath());
		RenderInstructions.buttonExitTexture = ImageLoader.loadTexture(image);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderInstructions.buttonExitLeftEnd, RenderInstructions.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderInstructions.buttonExitLeftEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderInstructions.buttonExitRightEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderInstructions.buttonExitRightEnd, RenderInstructions.buttonUpperEnd);
		glEnd();
		//Draw Skip button
		image = ImageLoader.loadImage(Texture.BUTTON_SKIP.getPath());
		RenderInstructions.buttonSkipTexture = ImageLoader.loadTexture(image);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderInstructions.buttonSkipLeftEnd, RenderInstructions.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderInstructions.buttonSkipLeftEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderInstructions.buttonSkipRightEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderInstructions.buttonSkipRightEnd, RenderInstructions.buttonUpperEnd);
		glEnd();
		//Draw Next button
		image = ImageLoader.loadImage(Texture.BUTTON_NEXT.getPath());
		RenderInstructions.buttonNextTexture = ImageLoader.loadTexture(image);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(RenderInstructions.buttonNextLeftEnd, RenderInstructions.buttonUpperEnd);
			glTexCoord2f(0f, 1f);
			glVertex2f(RenderInstructions.buttonNextLeftEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 1f);
			glVertex2f(RenderInstructions.buttonNextRightEnd, RenderInstructions.buttonLowerEnd);
			glTexCoord2f(1f, 0f);
			glVertex2f(RenderInstructions.buttonNextRightEnd, RenderInstructions.buttonUpperEnd);
		glEnd();
	}
	
	private static void drawExampleEntities() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		//Check which entities should be printed, according to the instruction page shown
		switch(RenderInstructions.instructionPage) {
		//On page 1, an example robot and Command Center must be printed
		case 1:
			image = ImageLoader.loadImage(Texture.ROBOT.getPath());
			RenderInstructions.exampleRobotTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityRobotLeftEnd, RenderInstructions.entityRobotUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityRobotLeftEnd, RenderInstructions.entityRobotLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityRobotRightEnd, RenderInstructions.entityRobotLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityRobotRightEnd, RenderInstructions.entityRobotUpperEnd);
			glEnd();
			image = ImageLoader.loadImage(Texture.COMMAND_CENTER.getPath());
			RenderInstructions.exampleCommCenterTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityCommCenterLeftEnd, RenderInstructions.entityCommCenterUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityCommCenterLeftEnd, RenderInstructions.entityCommCenterLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityCommCenterRightEnd, RenderInstructions.entityCommCenterLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityCommCenterRightEnd, RenderInstructions.entityCommCenterUpperEnd);
			glEnd();
			break;
		case 2:
			//On page 2, an example Money Server must be printed
			image = ImageLoader.loadImage(Texture.MONEY_SERVER.getPath());
			RenderInstructions.exampleMoneyServerTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityMoneyServerLeftEnd, RenderInstructions.entityMoneyServerUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityMoneyServerLeftEnd, RenderInstructions.entityMoneyServerLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityMoneyServerRightEnd, RenderInstructions.entityMoneyServerLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityMoneyServerRightEnd, RenderInstructions.entityMoneyServerUpperEnd);
			glEnd();
			break;
		case 3:
			//On page 3, an example Wrecked Building and Hardware Factory must be printed
			image = ImageLoader.loadImage(Texture.WRECKED_BUILDING.getPath());
			RenderInstructions.exampleMoneyServerTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityWreckedBuildingLeftEnd, RenderInstructions.entityWreckedBuildingUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityWreckedBuildingLeftEnd, RenderInstructions.entityWreckedBuildingLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityWreckedBuildingRightEnd, RenderInstructions.entityWreckedBuildingLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityWreckedBuildingRightEnd, RenderInstructions.entityWreckedBuildingUpperEnd);
			glEnd();
			image = ImageLoader.loadImage(Texture.HARDWARE_FACTORY.getPath());
			RenderInstructions.exampleMoneyServerTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityHardwareFactoryLeftEnd, RenderInstructions.entityHardwareFactoryUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityHardwareFactoryLeftEnd, RenderInstructions.entityHardwareFactoryLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityHardwareFactoryRightEnd, RenderInstructions.entityHardwareFactoryLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityHardwareFactoryRightEnd, RenderInstructions.entityHardwareFactoryUpperEnd);
			glEnd();
			break;
		default:
			image = ImageLoader.loadImage(Texture.ROBOT.getPath());
			RenderInstructions.exampleRobotTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityRobotLeftEnd, RenderInstructions.entityRobotUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityRobotLeftEnd, RenderInstructions.entityRobotLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityRobotRightEnd, RenderInstructions.entityRobotLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityRobotRightEnd, RenderInstructions.entityRobotUpperEnd);
			glEnd();
			image = ImageLoader.loadImage(Texture.COMMAND_CENTER.getPath());
			RenderInstructions.exampleRobotTexture = ImageLoader.loadTexture(image);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(RenderInstructions.entityCommCenterLeftEnd, RenderInstructions.entityCommCenterUpperEnd);
				glTexCoord2f(0f, 1f);
				glVertex2f(RenderInstructions.entityCommCenterLeftEnd, RenderInstructions.entityCommCenterLowerEnd);
				glTexCoord2f(1f, 1f);
				glVertex2f(RenderInstructions.entityCommCenterRightEnd, RenderInstructions.entityCommCenterLowerEnd);
				glTexCoord2f(1f, 0f);
				glVertex2f(RenderInstructions.entityCommCenterRightEnd, RenderInstructions.entityCommCenterUpperEnd);
			glEnd();
		}
	}

	private static void setMouseClickListener(long gameWindow) {
		glfwSetCursorPosCallback(gameWindow, (window, xpos, ypos) -> {
			RenderInstructions.mouseX = (float)xpos;
			RenderInstructions.mouseY = (float)ypos;
		});
		glfwSetMouseButtonCallback(gameWindow, (window, button, action, mods) -> {
		 	if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE ) {
		 		//Check click inside both buttons' Y axis
		 		if(RenderInstructions.mouseY >= RenderInstructions.buttonUpperEnd
		 				&& RenderInstructions.mouseY <= RenderInstructions.buttonLowerEnd) {
			 		//Check click inside Previous button
			 		if(RenderInstructions.mouseX >= RenderInstructions.buttonPreviousLeftEnd
			 				&& RenderInstructions.mouseX <= RenderInstructions.buttonPreviousRightEnd)
			 			RenderInstructions.instructionPage =
			 				(RenderInstructions.instructionPage == 1) ? 1 : RenderInstructions.instructionPage-1;
			 		//Check click inside Exit button
			 		else if(RenderInstructions.mouseX >= RenderInstructions.buttonExitLeftEnd
			 				&& RenderInstructions.mouseX <= RenderInstructions.buttonExitRightEnd) {
			 			WindowSettings.setScreenMode(WindowSettings.SCREENMODE_MENU);
			 			glfwFreeCallbacks(gameWindow);
			 			RenderInstructions.isMouseCallbackSet = false;
			 		}
			 		//Check click inside Skip button
			 		else if(RenderInstructions.mouseX >= RenderInstructions.buttonSkipLeftEnd
			 				&& RenderInstructions.mouseX <= RenderInstructions.buttonSkipRightEnd) {
						WindowSettings.setScreenMode(WindowSettings.SCREENMODE_INGAME);
			 			glfwFreeCallbacks(gameWindow);
			 			RenderInstructions.isMouseCallbackSet = false;
			 		}
			 		//Check click inside Next button
			 		else if(RenderInstructions.mouseX >= RenderInstructions.buttonNextLeftEnd
			 				&& RenderInstructions.mouseX <= RenderInstructions.buttonNextRightEnd) {
			 			if(RenderInstructions.instructionPage == 3) {
							WindowSettings.setScreenMode(WindowSettings.SCREENMODE_INGAME);
				 			glfwFreeCallbacks(gameWindow);
				 			RenderInstructions.isMouseCallbackSet = false;
				 		}
			 			else RenderInstructions.instructionPage = RenderInstructions.instructionPage+1;
			 		}
		 		}
		 	}
		});
		RenderInstructions.isMouseCallbackSet = true;
	}
	
	public static void draw(long window) {
		RenderInstructions.drawWallpaper();
		RenderInstructions.drawButtons();
		RenderInstructions.drawExampleEntities();
		if(!RenderInstructions.isMouseCallbackSet) RenderInstructions.setMouseClickListener(window);
	}
}
