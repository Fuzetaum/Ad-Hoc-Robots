package com.turningpoint.adhocbots.launch;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import com.turningpoint.adhocbots.window.RenderGameMap;
import com.turningpoint.adhocbots.window.RenderGameUI;
import com.turningpoint.adhocbots.window.RenderInstructions;
import com.turningpoint.adhocbots.window.RenderMainMenu;
import com.turningpoint.adhocbots.window.WindowSettings;

public class WindowSetup implements Runnable {
	private Thread thread;
    private long window;
    public boolean running = true;
	
    @Override
    public void run() {
    	this.init();
        // lastTime: time when last iteration took place
        long lastTime = System.nanoTime();
        // delta: portion of a frame that has already passed
        double delta = 0.0;
        // ns: amount of nanoseconds per frame
        double ns = 1000000000.0/WindowSettings.getFPS();
        // timer: measures when a whole second has passed
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        // While-loop for rendering & updating screen
        while(this.running) {
            delta += (System.nanoTime() - lastTime) / ns;
            lastTime = System.nanoTime();
            if(delta >= 1.0) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(glfwWindowShouldClose(window))
            	this.running = false;
        }
    }
	
    public void start() {
        this.running = true;
        WindowSettings.setScreenMode(WindowSettings.SCREENMODE_MENU);
        this.thread = new Thread(this, "MyGame");
        this.thread.start();
    }
	
    public void init() {
    	// Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW. Most GLFW functions will not work before doing this.
     	if ( !glfwInit() )
     		throw new IllegalStateException("Unable to initialize GLFW");
     	// Configure GLFW
     	glfwDefaultWindowHints(); // optional, the current window hints are already the default
     	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
     	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
     	// Create the window
     	window = glfwCreateWindow(WindowSettings.getWindowWidth().intValue(),
     			WindowSettings.getWindowHeight().intValue(), "Ad Hoc Bots", NULL, NULL);
     	if ( window == NULL )
     		throw new RuntimeException("Failed to create the GLFW window");
		if(window == NULL) System.err.println("Window creation failed");
	 	// Get the thread stack and push a new frame
	 	try ( MemoryStack stack = stackPush() ) {
	 		IntBuffer pWidth = stack.mallocInt(1); // int*
	 		IntBuffer pHeight = stack.mallocInt(1); // int*
	 		// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
	 		// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	 		// Center the window
			glfwSetWindowPos(window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
	 		} // the stack frame is popped automatically
	 	// Make the OpenGL context current
	 	glfwMakeContextCurrent(window);
	 	// Enable v-sync
	 	glfwSwapInterval(1);
	 	// Make the window visible
	 	glfwShowWindow(window);
    }
	
    public void update() {
    	glfwPollEvents();
    }
	
    public void render() {
    	GL.createCapabilities();
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
    	glEnable(GL_BLEND);
    	if(WindowSettings.getScreenMode() == WindowSettings.SCREENMODE_INGAME) {
    		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Set the clear color to black
    		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    		RenderGameMap.draw(window);
    		RenderGameUI.draw(window);
    	}
    	else {
    		glClearColor(1.0f, 1.0f, 1.0f, 0.0f); // Set the clear color to white
    		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    	}
    	//Check which screen type is being viewed right now - ingame screen view was already checked
    	if(WindowSettings.getScreenMode() == WindowSettings.SCREENMODE_MENU) RenderMainMenu.draw(this.window);
    	else if(WindowSettings.getScreenMode() == WindowSettings.SCREENMODE_INSTRUCTIONS) RenderInstructions.draw(this.window);
        glfwSwapBuffers(window);
    }
}
