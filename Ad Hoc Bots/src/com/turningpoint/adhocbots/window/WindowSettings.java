package com.turningpoint.adhocbots.window;

public class WindowSettings {
	public static final Integer SCREENMODE_MENU = 0;
	public static final Integer SCREENMODE_INSTRUCTIONS = 1;
	public static final Integer SCREENMODE_INGAME = 2;
	private static boolean fullscreen = false;
	private static Double width=1024.0;
	private static Double height=768.0;
	private static Double FPS=60.0;
	private static boolean vsync=true;
	private static Integer screenMode;
	
	public static void setWindowSize(Double height, Double width) {
		WindowSettings.width = width; WindowSettings.height = height;
	}
	public static void setVsync(boolean newVal) {WindowSettings.vsync = newVal;}
	public static void setFPS(Double newFPS) {WindowSettings.FPS = newFPS;}
	public static void setFullscreen(boolean newVal) {WindowSettings.fullscreen = newVal;}
	public static void setScreenMode(Integer mode) {WindowSettings.screenMode = mode;}
	
	public static boolean getVsync() {return WindowSettings.vsync;}
	public static Double getWindowWidth() {return WindowSettings.width;}
	public static float getWindowWidthAsFloat() {return Float.parseFloat(WindowSettings.width.toString());}
	public static Double getWindowHeight() {return WindowSettings.height;}
	public static float getWindowHeightAsFloat() {return Float.parseFloat(WindowSettings.height.toString());}
	public static Double getFPS() {return WindowSettings.FPS;}
	public static Integer getScreenMode() { return WindowSettings.screenMode;}
}
