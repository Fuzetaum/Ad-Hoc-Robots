package com.turningpoint.adhocbots.window;

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
import com.turningpoint.adhocbots.map.Building;
import com.turningpoint.adhocbots.map.Robot;
import com.turningpoint.adhocbots.map.UserController;
import com.turningpoint.adhocbots.window.util.ImageLoader;
import com.turningpoint.adhocbots.window.util.Texture;

public class RenderGameUI {
	//Textures
	private static int frameTexture,resourceTexture;
	//Flags to control listeners' setup
	private static boolean isMouseCallbackSet = false;
	private static boolean isBuildingSelected = false;
	private static Building selectedBuilding;
	private static boolean isRobotSelected = false;
	private static Robot selectedRobot;
	private static Font textDrawer=null;
	
	public static boolean isRobotSelected() {return RenderGameUI.isRobotSelected;}
	public static boolean isGivenRobotSelected(Robot robot) {
		if(RenderGameUI.isRobotSelected)
			return (robot == RenderGameUI.selectedRobot);
		return false;
	}
	
	private static void drawFrame() {
		// sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		// redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		//Choose the proper frame to draw
		if(RenderGameUI.isBuildingSelected) image = ImageLoader.loadImage(Texture.TEXTURE_FRAME_BUILDING);
		else if(RenderGameUI.isRobotSelected) image = ImageLoader.loadImage(Texture.TEXTURE_FRAME_ROBOT);
		else image = ImageLoader.loadImage(Texture.TEXTURE_FRAME);
		RenderGameUI.frameTexture = ImageLoader.loadTexture(image);
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
		//Draw the resource stockpile frame
		image = ImageLoader.loadImage(Texture.TEXTURE_FRAME_RESOURCES);
		RenderGameUI.resourceTexture = ImageLoader.loadTexture(image);
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
		//Draw the resources amount
		try {
			if(RenderGameUI.textDrawer == null) RenderGameUI.textDrawer = new Font(Texture.PATH_FONT, 24);
			String moneyAmount = UserController.getMoney().toString();
			RenderGameUI.textDrawer.drawText(moneyAmount, 860, 19);
			String steelAmount = UserController.getSteel().toString();
			RenderGameUI.textDrawer.drawText(steelAmount, 860, 78);
			String electronicsAmount = UserController.getElectronics().toString();
			RenderGameUI.textDrawer.drawText(electronicsAmount, 860, 136);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void drawBuildingInfo(Building building) {
		//Signal a building selection
		RenderGameUI.isBuildingSelected = true;
		RenderGameUI.isRobotSelected = false;
		RenderGameUI.selectedBuilding = building;
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		switch(building.getBuildingType()) {
		//BUILDING_RESOURCE_COMMAND_CENTER
		case 0:
			image = ImageLoader.loadImage(Texture.TEXTURE_COMMAND_CENTER);
			break;
		//BUILDING_RESOURCE_MONEY
		case 1:
			image = ImageLoader.loadImage(Texture.TEXTURE_MONEY_SERVER);
			break;
		//BUILDING_RESOURCE_STEEL
		case 2:
			image = ImageLoader.loadImage(Texture.TEXTURE_WRECKED_BUILDING);
			break;
		//BUILDING_RESOURCE_ELECTRONICS
		case 3:
			image = ImageLoader.loadImage(Texture.TEXTURE_HARDWARE_FACTORY);
			break;
		default:
			image = ImageLoader.loadImage(Texture.TEXTURE_COMMAND_CENTER);
			break;
		}
		RenderGameUI.frameTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f);
			glVertex2f(35f, 615f);
			glTexCoord2f(0f, 1f);
			glVertex2f(35f, 745f);
			glTexCoord2f(1f, 1f);
			glVertex2f(146f, 745f);
			glTexCoord2f(1f, 0f);
			glVertex2f(146f, 615f);
		glEnd();
	}
	
	public static void drawRobotInfo(Robot robot) {
		//Signal a robot selection
		RenderGameUI.isBuildingSelected = false;
		RenderGameUI.isRobotSelected = true;
		RenderGameUI.selectedRobot = robot;
	}
	
	public static void unclickEntities() {
		RenderGameUI.isBuildingSelected = false;
		RenderGameUI.isRobotSelected = false;
	}
	
	public static void issueRobotCommand(float x, float y) {RenderGameUI.selectedRobot.receiveCommand(x, y);}
	
	public static void draw(long window) {
		RenderGameUI.drawFrame();
		if(RenderGameUI.isBuildingSelected) RenderGameUI.drawBuildingInfo(RenderGameUI.selectedBuilding);
		if(RenderGameUI.isRobotSelected) RenderGameUI.drawRobotInfo(RenderGameUI.selectedRobot);
	}
}
