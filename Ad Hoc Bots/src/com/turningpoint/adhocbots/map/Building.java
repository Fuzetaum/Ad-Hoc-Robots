package com.turningpoint.adhocbots.map;

public class Building {
	public static final Integer BUILDING_RESOURCE_COMMAND_CENTER = 0;
	public static final Integer BUILDING_RESOURCE_MONEY = 1;
	public static final Integer BUILDING_RESOURCE_STEEL = 2;
	public static final Integer BUILDING_RESOURCE_ELECTRONICS = 3;
	private static final Integer BUILDING_RESOURCE_AMOUNT_BASE = 5000;
	private float x,y;
	private Integer type;
	private Integer resourceAmount;
	
	public Building(float x, float y, Integer type) {
		this.x = x; this.y = y;
		this.type = type;
	}
	
	public Integer getBuildingType() {return this.type;}
	public float getX() {return this.x;}
	public float getY() {return this.y;}
	
	public Integer extractResource(Integer amount) {
		if(this.resourceAmount >= amount) {
			this.resourceAmount -= amount;
			return amount;
		}
		else {
			Integer extracted = amount - this.resourceAmount;
			this.resourceAmount = 0;
			return extracted;
		}
	}
}
