package com.turningpoint.adhocbots.engine.render.window;

public abstract class WindowConfigurations {
    public static final String GAME_TITLE = "Ad Hoc Robots";
    private static boolean fullscreen = false;
    private static Double width = 800.0;
    private static Double height = 600.0;
    private static Double FPS = 60.0;
    private static boolean vsync = true;

    public static void setWindowSize(Double height, Double width) {
        WindowConfigurations.width = width;
        WindowConfigurations.height = height;
    }
    public static void setVsync(boolean enabled) { WindowConfigurations.vsync = enabled; }
    public static void setFPS(Double fps) { WindowConfigurations.FPS = fps; }
    public static void setFullscreen(boolean enabled) { WindowConfigurations.fullscreen = enabled; }

    public static boolean getVsync() { return WindowConfigurations.vsync; }
    public static Double getWindowWidth() { return WindowConfigurations.width; }
    public static float getWindowWidthAsFloat() { return Float.parseFloat(WindowConfigurations.width.toString()); }
    public static Double getWindowHeight() { return WindowConfigurations.height; }
    public static float getWindowHeightAsFloat() { return Float.parseFloat(WindowConfigurations.height.toString()); }
    public static Double getFPS() { return WindowConfigurations.FPS; }
}
