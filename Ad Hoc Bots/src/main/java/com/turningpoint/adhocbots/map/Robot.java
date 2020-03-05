package com.turningpoint.adhocbots.map;

public class Robot {
	public static final float ROBOT_SIGNAL_REACH = 5f;
	private static final float ROBOT_SPEED = 0.05f;
	private static final float ROBOT_BASE_LIFESPAN = 300f;
	private static final float ROBOT_CARRY_CAPACITY = 100f;
	private float x, y;
	private float commandX, commandY;
	private float lifespan;
	private Integer resourceCarriedType;
	private Integer resourceCarriedAmount;
	
	public Robot(float x, float y) {
		this.x = x; this.y = y;
		this.commandX = x; this.commandY = y;
		this.lifespan = Robot.ROBOT_BASE_LIFESPAN;
		this.resourceCarriedAmount = 0;
		this.resourceCarriedType = -1;
	}
	
	public float getX() {return this.x;}
	public float getY() {return this.y;}
	
	public void receiveCommand(float commandX, float commandY) {
		if((commandX > 50f) || (commandX < 0f)) return;
		if((commandY > 50f) || (commandY < 0f)) return;
		this.commandX = commandX;
		this.commandY = commandY;
	}
	
	public void walk() {
		Float commandVectorX = this.commandX - this.x;
		Float commandVectorY = this.commandY - this.y;
		//Calculate that vector's length
		Float vectorLength = (float) Math.sqrt(Math.pow(commandVectorX, 2)+Math.pow(commandVectorY, 2));
		if(vectorLength.isNaN()) return;
		//Recalculate the same vector, but with size 1
		Float commandVectorXNormalized = commandVectorX / vectorLength;
		Float commandVectorYNormalized = commandVectorY / vectorLength;
		if(commandVectorXNormalized.isNaN()) return;
		//Do 1 step towards command, adjusted by the robot's speed
		//If the step brings the robot slightly beyond command, place it at command location
		//	and set command as null
		this.x += commandVectorXNormalized * Robot.ROBOT_SPEED;
		if((this.x >= (this.commandX - 0.1f)) && (this.x <= (this.commandX + 0.1f)))
			this.commandX = this.x;
		this.y += commandVectorYNormalized * Robot.ROBOT_SPEED;
		if((this.y >= (this.commandY - 0.1f)) && (this.y <= (this.commandY + 0.1f)))
			this.commandY = this.y;
		//System.out.println("Robot walked to ("+this.x+","+this.y+")");
	}
	
	public void gatherResource(Integer resourceType) {
		//If robot hands are free
		if(this.resourceCarriedAmount == 0) {
			this.resourceCarriedAmount++;
			this.resourceCarriedType = resourceType;
		}
		//If the resource is of same type and robot still has capacity
		else if((this.resourceCarriedAmount < Robot.ROBOT_CARRY_CAPACITY) &&
				(this.resourceCarriedType.equals(resourceType)))
			this.resourceCarriedAmount++;
	}
}
