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
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

import com.turningpoint.adhocbots.engine.render.window.WindowConfigurations;
import com.turningpoint.adhocbots.map.Building;
import com.turningpoint.adhocbots.map.Map;
import com.turningpoint.adhocbots.map.Robot;
import com.turningpoint.adhocbots.map.UserController;
import com.turningpoint.adhocbots.window.util.ImageLoader;
import com.turningpoint.adhocbots.window.util.Texture;

public class RenderGameMap {
	//Textures
	private static int tileGroundGrassTexture, commandCenterTexture, moneyServerTexture, WreckedBuildingTexture;
	private static int hardwareFactoryTexture, robotTexture;
	//The tile size is 100x50
	private static final float TILE_HORIZONTAL_OFFSET = 50f;
	private static final float TILE_VERTICAL_OFFSET = 25f;
	//Top left point describing what the player actually sees
	private static float viewportX, viewportY;
	//Mouse position
	private static int mouseX, mouseY;
	//Flags to control listeners' setup
	private static boolean isMapGenerated = false;
	private static boolean isMouseCallbackSet = false;
	private static boolean isKeyboardCallbackSet = false;
	
	private static void initMapViewport() {
		Random rand = new Random();
		//Calculate the Command Center starting position
		Integer commXPos = rand.nextInt(Map.getSize())+1;
		Integer commYPos = rand.nextInt(Map.getSize())+1;
		Map.placeCommandCenter(commXPos, commYPos);
		//Calculate where the viewport actually is
		RenderGameMap.viewportX = (RenderGameMap.TILE_HORIZONTAL_OFFSET*(commXPos+commYPos))-(4.62f*100f);
		RenderGameMap.viewportY = -(RenderGameMap.TILE_VERTICAL_OFFSET*(commXPos-commYPos))-(7.18f*50f);
		//Place the starting robots
		Map.addRobot(commXPos+1, commYPos);
		Map.addRobot(commXPos-1, commYPos);
		Map.addRobot(commXPos, commYPos+1);
	}
	
	private static void generateMap() {
		//Set map configs
		Map.setSize(50);
		Map.setAmountOfNodes(50);
		for(int i=1; i<=30; i++)
			Map.placeRandomTileElement();
		RenderGameMap.isMapGenerated = true;
		//Initialize player's resource stockpile
		UserController.initializeResources();
	}
	
	private static void drawViewportTiles() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image = ImageLoader.loadImage(Texture.GROUND_GRASS.getPath());
		RenderGameMap.tileGroundGrassTexture = ImageLoader.loadTexture(image);
		glColor4f(1f, 1f, 1f, 1f);
		//Cycle through the tiles by y-axis
		for(int y=0;y<Map.getSize();y++) {
			//Cycle through the tiles by x-axis
			for(int x=0;x<Map.getSize();x++) {
				/* Each tile is drawn at its own position from (0,0), given the size offset
				 * Remember that the coordinate system for tile printing changes, since the map
				 * 	is diamond-shaped
				 * It is needed to add (viewportX, viewportY) to adjust the viewport's offset
				 * Also, each tile in a lower row must be printed with an offset in both axis
				 */
				glBegin(GL_QUADS);
					glTexCoord2f(0f, 0f);
					glVertex2f(0f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
							0f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
					glTexCoord2f(0f, 1f);
					glVertex2f(0f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
							50f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
					glTexCoord2f(1f, 1f);
					glVertex2f(100f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
							50f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
					glTexCoord2f(1f, 0f);
					glVertex2f(100f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
							0f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
				glEnd();
			}
		}
	}
	
	public static void drawMapBuildings() {
		Building entity;
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		//Cycle through each coordinate to draw buildings
		//Drawing backwards makes "front" building to be drawed above "back" ones
		//for(Integer y=Map.getSize()-1; y>=0; y--) for(Integer x=Map.getSize()-1; x>=0; x--) {
		for(Integer x=Map.getSize()-1; x>=0; x--) for(Integer y=0; y<Map.getSize(); y++) {
			if((entity = Map.getTileElement(x, y)) == null) continue;
			switch(entity.getBuildingType()) {
				//Command Center
				//case (Building.BUILDING_RESOURCE_COMMAND_CENTER):
				case 0:
					image = ImageLoader.loadImage(Texture.COMMAND_CENTER.getPath());
					RenderGameMap.commandCenterTexture = ImageLoader.loadTexture(image);
					break;
				//Money Server
				//case (Building.BUILDING_RESOURCE_MONEY):
				case 1:
					image = ImageLoader.loadImage(Texture.MONEY_SERVER.getPath());
					RenderGameMap.moneyServerTexture = ImageLoader.loadTexture(image);
					break;
				//Wrecked Building
				//case (Building.BUILDING_RESOURCE_STEEL):
				case 2:
					image = ImageLoader.loadImage(Texture.WRECKED_BUILDING.getPath());
					RenderGameMap.WreckedBuildingTexture = ImageLoader.loadTexture(image);
					break;
				//Hardware Factory
				//case (Building.BUILDING_RESOURCE_ELECTRONICS):
				case 3:
					image = ImageLoader.loadImage(Texture.HARDWARE_FACTORY.getPath());
					RenderGameMap.hardwareFactoryTexture = ImageLoader.loadTexture(image);
					break;
			}
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(4f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
					-48f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
				glTexCoord2f(0f, 1f);
				glVertex2f(4f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
					50f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
				glTexCoord2f(1f, 1f);
				glVertex2f(94f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
					50f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
				glTexCoord2f(1f, 0f);
				glVertex2f(94f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(x+y))-RenderGameMap.viewportX,
					-48f-(RenderGameMap.TILE_VERTICAL_OFFSET*(x-y))-RenderGameMap.viewportY);
			glEnd();
		}
	}
	
	public static void drawRobots() {
		//sets up the screen camera view
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		//redefines the (1,1) OpenGL native scale to (width,height)
		glOrtho(0, WindowConfigurations.getWindowWidth(), WindowConfigurations.getWindowHeight(), 0, 1, -1);
		BufferedImage image;
		Iterator<Robot> robotList = Map.getRobotListIterator();
		Robot robot;
		while(robotList.hasNext()) {
			robot = robotList.next();
			if(RenderGameUI.isGivenRobotSelected(robot)) image = ImageLoader.loadImage(Texture.ROBOT_SELECTED.getPath());
			else image = ImageLoader.loadImage(Texture.ROBOT.getPath());
			RenderGameMap.robotTexture = ImageLoader.loadTexture(image);
			robot.walk();
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS);
				glTexCoord2f(0f, 0f);
				glVertex2f(37f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(robot.getX()+robot.getY()))-RenderGameMap.viewportX,
					-19f-(RenderGameMap.TILE_VERTICAL_OFFSET*(robot.getX()-robot.getY()))-RenderGameMap.viewportY);
				glTexCoord2f(0f, 1f);
				glVertex2f(37f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(robot.getX()+robot.getY()))-RenderGameMap.viewportX,
					30f-(RenderGameMap.TILE_VERTICAL_OFFSET*(robot.getX()-robot.getY()))-RenderGameMap.viewportY);
				glTexCoord2f(1f, 1f);
				glVertex2f(64f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(robot.getX()+robot.getY()))-RenderGameMap.viewportX,
					30f-(RenderGameMap.TILE_VERTICAL_OFFSET*(robot.getX()-robot.getY()))-RenderGameMap.viewportY);
				glTexCoord2f(1f, 0f);
				glVertex2f(64f+(RenderGameMap.TILE_HORIZONTAL_OFFSET*(robot.getX()+robot.getY()))-RenderGameMap.viewportX,
					-19f-(RenderGameMap.TILE_VERTICAL_OFFSET*(robot.getX()-robot.getY()))-RenderGameMap.viewportY);
			glEnd();
		}
	}
	
	private static void setMouseClickListener(long gameWindow) {
		glfwSetCursorPosCallback(gameWindow, (window, xpos, ypos) -> {
			if(ypos <= (float)583f) {
				RenderGameMap.mouseX = (int)xpos;
				RenderGameMap.mouseY = (int)ypos;
			}
		});
		glfwSetMouseButtonCallback(gameWindow, (window, button, action, mods) -> {
			int xo = (int)(RenderGameMap.viewportX + RenderGameMap.mouseX);
			int yo = (int)(RenderGameMap.viewportY + RenderGameMap.mouseY)-2;
			Integer mapClickX = (int)(((xo/RenderGameMap.TILE_HORIZONTAL_OFFSET)-(yo/RenderGameMap.TILE_VERTICAL_OFFSET))/2f)+1;
			Integer mapClickY = (int)((yo/RenderGameMap.TILE_VERTICAL_OFFSET)+mapClickX);
		 	if ( button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE ) {
		 		Building building = Map.getTileElement(mapClickX-1, mapClickY-1);
		 		Robot robot = Map.getRobot(mapClickX-1, mapClickY-1);
		 		//Wether there is no building or a robot is on top of it, if there is a robot in that tile it must be selected instead
		 		if(robot != null)
		 			RenderGameUI.drawRobotInfo(robot);
		 		//Check if building exists at position
		 		else if(building != null)
		 			RenderGameUI.drawBuildingInfo(building);
		 		//If no cases before execute, then nothing was clicked. Unflag clicked entities
		 		else RenderGameUI.unclickEntities();
		 	}
		 	//click handling for when a robot is selected, and a move command is issued
		 	else if(button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_RELEASE && RenderGameUI.isRobotSelected())
		 		RenderGameUI.issueRobotCommand(mapClickX-1, mapClickY-1);
		});
		RenderGameMap.isMouseCallbackSet = true;
	}
	
	private static void setKeyboardListener(long gameWindow) {
		glfwSetKeyCallback(gameWindow, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_UP && (action == GLFW_REPEAT || action == GLFW_PRESS)) RenderGameMap.viewportY -= 30f;
			else if(key == GLFW_KEY_DOWN && (action == GLFW_REPEAT || action == GLFW_PRESS)) RenderGameMap.viewportY += 30f;
			if(key == GLFW_KEY_LEFT && (action == GLFW_REPEAT || action == GLFW_PRESS)) RenderGameMap.viewportX -= 30f;
			else if(key == GLFW_KEY_RIGHT && (action == GLFW_REPEAT || action == GLFW_PRESS)) RenderGameMap.viewportX += 30f;
		});
		RenderGameMap.isKeyboardCallbackSet = true;
	}
	
	public static void draw(long window) {
		if(!RenderGameMap.isMapGenerated) {
			RenderGameMap.generateMap();
			RenderGameMap.initMapViewport();
		}
		RenderGameMap.drawViewportTiles();
		RenderGameMap.drawMapBuildings();
		RenderGameMap.drawRobots();
		if(!RenderGameMap.isMouseCallbackSet) RenderGameMap.setMouseClickListener(window);
		if(!RenderGameMap.isKeyboardCallbackSet) RenderGameMap.setKeyboardListener(window);
	}
}
