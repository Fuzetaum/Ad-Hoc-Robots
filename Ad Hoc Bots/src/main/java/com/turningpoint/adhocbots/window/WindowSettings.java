package com.turningpoint.adhocbots.window;

public class WindowSettings {
	public static final Integer SCREENMODE_MENU = 0;
	public static final Integer SCREENMODE_INSTRUCTIONS = 1;
	public static final Integer SCREENMODE_INGAME = 2;
	private static Integer screenMode;
	
	public static void setScreenMode(Integer mode) { WindowSettings.screenMode = mode; }
	
	public static Integer getScreenMode() { return WindowSettings.screenMode; }
}
