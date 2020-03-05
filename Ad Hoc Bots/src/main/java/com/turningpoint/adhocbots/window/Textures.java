package com.turningpoint.adhocbots.window;

import com.turningpoint.adhocbots.engine.annotation.texture.TextureLoader;
import com.turningpoint.adhocbots.engine.render.TurningPointEngine;

import java.util.HashMap;
import java.util.Map;

@TextureLoader(methodName = "loadAll")
public enum Textures {
    BUTTON_START ("BUTTON_START", "button/button-start.png"),
    BUTTON_EXIT ("BUTTON_EXIT", "button/button-exit.png"),
    BUTTON_NEXT ("BUTTON_NEXT", "button/button-next.png"),
    BUTTON_PREVIOUS ("BUTTON_PREVIOUS", "button/button-previous.png"),
    BUTTON_SKIP ("BUTTON_SKIP", "button/button-skip.png"),
    ROBOT ("ROBOT", "entity/robot-down.png"),
    ROBOT_SELECTED ("ROBOT_SELECTED", "entity/robot-down-selected.png"),
    COMMAND_CENTER ("COMMAND_CENTER", "entity/command-center.png"),
    MONEY_SERVER ("MONEY_SERVER", "entity/money-server.png"),
    WRECKED_BUILDING ("WRECKED_BUILDING", "entity/wrecked-building.png"),
    HARDWARE_FACTORY ("HARDWARE_FACTORY", "entity/hardware-factory.png"),
    GROUND_GRASS ("GROUND_GRASS", "map/ground-grass.png"),
    FRAME ("FRAME", "ui/frame.png"),
    FRAME_BUILDING ("FRAME_BUILDING", "ui/frame-building.png"),
    FRAME_ROBOT ("FRAME_ROBOT", "ui/frame-robot.png"),
    FRAME_RESOURCES ("FRAME_RESOURCES", "ui/frame-resources.png"),
    WALLPAPER_INSTRUCTION_1 ("WALLPAPER_INSTRUCTION_1", "wallpaper/instruction-1.png"),
    WALLPAPER_INSTRUCTION_2 ("WALLPAPER_INSTRUCTION_2", "wallpaper/instruction-2.png"),
    WALLPAPER_INSTRUCTION_3 ("WALLPAPER_INSTRUCTION_3", "wallpaper/instruction-3.png"),
    WALLPAPER_MAIN_MENU ("WALLPAPER_MAIN_MENU", "wallpaper/main-splash.png");

    private String name;
    private String path;

    Textures(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public static void loadAll() {
        Map<String, String> textures = new HashMap<>();
        textures.put(Textures.BUTTON_START.getName(), Textures.BUTTON_START.getPath());
        textures.put(Textures.BUTTON_EXIT.getName(), Textures.BUTTON_EXIT.getPath());
        textures.put(Textures.BUTTON_NEXT.getName(), Textures.BUTTON_NEXT.getPath());
        textures.put(Textures.BUTTON_PREVIOUS.getName(), Textures.BUTTON_PREVIOUS.getPath());
        textures.put(Textures.BUTTON_SKIP.getName(), Textures.BUTTON_SKIP.getPath());
        textures.put(Textures.ROBOT.getName(), Textures.ROBOT.getPath());
        textures.put(Textures.ROBOT_SELECTED.getName(), Textures.ROBOT_SELECTED.getPath());
        textures.put(Textures.COMMAND_CENTER.getName(), Textures.COMMAND_CENTER.getPath());
        textures.put(Textures.MONEY_SERVER.getName(), Textures.MONEY_SERVER.getPath());
        textures.put(Textures.WRECKED_BUILDING.getName(), Textures.WRECKED_BUILDING.getPath());
        textures.put(Textures.HARDWARE_FACTORY.getName(), Textures.HARDWARE_FACTORY.getPath());
        textures.put(Textures.GROUND_GRASS.getName(), Textures.GROUND_GRASS.getPath());
        textures.put(Textures.FRAME.getName(), Textures.FRAME.getPath());
        textures.put(Textures.FRAME_BUILDING.getName(), Textures.FRAME_BUILDING.getPath());
        textures.put(Textures.FRAME_ROBOT.getName(), Textures.FRAME_ROBOT.getPath());
        textures.put(Textures.FRAME_RESOURCES.getName(), Textures.FRAME_RESOURCES.getPath());
        textures.put(Textures.WALLPAPER_INSTRUCTION_1.getName(), Textures.WALLPAPER_INSTRUCTION_1.getPath());
        textures.put(Textures.WALLPAPER_INSTRUCTION_2.getName(), Textures.WALLPAPER_INSTRUCTION_2.getPath());
        textures.put(Textures.WALLPAPER_INSTRUCTION_3.getName(), Textures.WALLPAPER_INSTRUCTION_3.getPath());
        textures.put(Textures.WALLPAPER_MAIN_MENU.getName(), Textures.WALLPAPER_MAIN_MENU.getPath());
        System.out.print("Initializing game textures...");
        TurningPointEngine.loadTextures(textures);
        System.out.println("finished");
    }
}
