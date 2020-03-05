package com.turningpoint.adhocbots.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Map {
	private static Integer size;
	private static Integer amountOfNodes;
	private static HashMap<Integer,Building> buildingList = new HashMap<Integer,Building>();
	private static List<Robot> robotList = new ArrayList<Robot>();
	
	public static void setSize(Integer size) {Map.size = size;}
	public static void setAmountOfNodes(Integer amount) {Map.amountOfNodes = amount;}
	public static void setTileElement(Integer tileX, Integer tileY, Integer elem) {
		Map.buildingList.put((tileY*Map.getSize())+tileX, new Building(tileX,tileY,elem));}
	public static void addRobot(float x, float y) {Map.robotList.add(new Robot(x,y));}
	
	public static Integer getSize() {return Map.size;}
	public static Integer getAmountOfNodes() {return Map.amountOfNodes;}
	public static Building getTileElement(Integer tileX, Integer tileY) {return Map.buildingList.get((tileY*Map.getSize())+tileX);}
	public static Robot getRobot(float x, float y) {
		for(Robot robot : Map.robotList) {
			if((robot.getX() > (x-1f)) && (robot.getX() < (x+1f)))
				if((robot.getY() > (y-1f)) && (robot.getY() < (y+1f)))
					return robot;
		}
		return null;
	}
	public static Iterator<Robot> getRobotListIterator() {return Map.robotList.iterator();}
	
	public static boolean tileContainsRobot(float x, float y) {
		return true;
	}
	
	/* Decide which structures will appear throughout the map
	 * 0 - Command Center (inserted via special procedure)
	 * 1 - Money Server
	 * 2 - Wrecked Building
	 * 3 - Hardware Factory
	 */
	public static void placeRandomTileElement() {
		Random rand = new Random();
		Integer tileX = rand.nextInt(Map.size);
		Integer tileY = rand.nextInt(Map.size);
		while(Map.buildingList.containsKey((tileY*Map.getSize())+tileX)) {
			tileX = rand.nextInt(Map.size);
			tileY = rand.nextInt(Map.size);
		}
		Map.buildingList.put((tileY*Map.getSize())+tileX, new Building(tileX,tileY,rand.nextInt(3)+1));
	}
	
	public static void placeCommandCenter(Integer x, Integer y) {
		Map.buildingList.put((y*Map.getSize())+x, new Building(x,y,Building.BUILDING_RESOURCE_COMMAND_CENTER));}
}
