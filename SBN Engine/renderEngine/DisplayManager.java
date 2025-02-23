package renderEngine;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.ImageIOImageData;

public class DisplayManager {
	
	private static int WIDTH;
	private static int HEIGHT;
	private static String BASLIK;
	private static boolean  FULL_SCREEN;
	private static final int FPS_CAP = 60;
	
	private static long lastFrameTime;
	private static float delta;
	
	private static ByteBuffer[] byteBufferArray;
	private static BufferedImage bufferedImage;
	private static ImageIOImageData imageIOdata;
	
	public DisplayManager(String title, int width, int height, boolean fullscreen) 
	{
		DisplayManager.FULL_SCREEN = fullscreen;
		DisplayManager.BASLIK = title;
		DisplayManager.WIDTH = width;
		DisplayManager.HEIGHT = height;			
	}
	
	public static void createDisplay() throws IOException 
	{

		ContextAttribs atr = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try 
		{
			Display.create(new PixelFormat(4, 4, 0, 4), atr);
			Display.setIcon(loadIcon("/res/ikon.png"));
			Display.setVSyncEnabled(true); // Vertical Syncronization
			
			if(FULL_SCREEN) {				
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			}
			else {
				Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
				Display.setTitle(BASLIK);
				Display.setResizable(true);
			}
			
		} catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT); // ekraný 0,0dan baþlatýp kordinat width,height'a kadar
		lastFrameTime = getCurrentTime();
	}
	
	public static void inputs()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_1)) 
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);//GL11.GL_FRONT_AND_BACK
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_2)) 
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}
	
	public static void updateDisplay() 
	{
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
		
		if(Display.wasResized()) 
		{
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}
	
	public static float getFrameTimeSeconds() 
	{
		return delta;
	}
	
	public static void closeDisplay() 
	{
		Display.destroy();
	}
	
	private static long getCurrentTime() 
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public static Vector2f getNormalizedMouseCoordinates() 
	{
		float normX = -1.0f + 2.0f * Mouse.getX() / Display.getWidth();
		float normY =  1.0f - 2.0f * Mouse.getY() / Display.getHeight();
		return new Vector2f(normX, normY);
	}
	
	private static ByteBuffer[] loadIcon(String path) {
	    imageIOdata = new ImageIOImageData();
	    try {
	    	InputStream is = new BufferedInputStream(new FileInputStream("Resources" + path));
	        bufferedImage = ImageIO.read(is);
	        byteBufferArray = new ByteBuffer[] {imageIOdata.imageToByteBuffer(bufferedImage, false, false, null)};
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return byteBufferArray;
	}
}