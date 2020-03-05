package com.turningpoint.adhocbots.map;

public class UserController {
	private static Integer money;
	private static Integer steel;
	private static Integer electronics;
	
	public static Integer getMoney() {return UserController.money;}
	public static Integer getSteel() {return UserController.steel;}
	public static Integer getElectronics() {return UserController.electronics;}
	
	public static void addMoney(Integer amount) {UserController.money += amount;}
	public static void addSteel(Integer amount) {UserController.steel += amount;}
	public static void addElectronics(Integer amount) {UserController.electronics += amount;}
	public static void consumeMoney(Integer amount) {UserController.money -= amount;}
	public static void consumeSteel(Integer amount) {UserController.steel -= amount;}
	public static void consumeElectronics(Integer amount) {UserController.electronics -= amount;}
	
	public static void initializeResources() {
		UserController.money = 100;
		UserController.steel = 100;
		UserController.electronics = 100;
	}
}
