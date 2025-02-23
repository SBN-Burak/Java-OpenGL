package guiComponents;

import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import guiRenderer.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public abstract class Button implements IButton{

	private String texture;
    private Vector2f position, scale;
    private GuiTexture gui;
    
    public Button(Loader loader, String texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
        this.gui = new GuiTexture(loader.loadGUITexture(texture), position, scale);
    }

    public void checkHover() 
	{
    			
    	Vector2f location = gui.getPosition();
        Vector2f scale = gui.getScale();
        Vector2f mouseCoordinates = DisplayManager.getNormalizedMouseCoordinates();
    	
        if (location.y + scale.y > -mouseCoordinates.y && location.y - scale.y < -mouseCoordinates.y &&
        		location.x + scale.x > mouseCoordinates.x && location.x - scale.x < mouseCoordinates.x) 
        {
        	isHovering();
        	
        	while(Mouse.next()) 
        	{       			
        		if (Mouse.isButtonDown(0)) { // Fare Released olduðunda tepki versin!
        			click();
        		}
        	}
        }
        
        else {
        	gui.setScale(this.scale);
        }
    	
	}
    
    public void hide(List<GuiTexture> guiTextures) {
        stopRender(guiTextures);
    }
 
    public void show(List<GuiTexture> guiTextures) {
        startRender(guiTextures);
    }
    
    public void isHoveringAnimation(float ScaleDeðeri) {
        gui.setScale(new Vector2f(scale.x + ScaleDeðeri, scale.y + ScaleDeðeri));
    }
 
    public void clickAnimation(float ScaleDeðeri) {
        gui.setScale(new Vector2f(scale.x - (ScaleDeðeri * 2), scale.y - (ScaleDeðeri * 2)));
    }
    
    private void startRender(List<GuiTexture> guiTextureList) {
    	guiTextureList.add(gui);
    }
    
    private void stopRender(List<GuiTexture> guiTextureList) {
    	guiTextureList.remove(gui);
    }
    
    public GuiTexture getGuiTexture() {
        return gui;
    }
 
    public Vector2f getScale() {
        return scale;
    }
 
    public Vector2f getPosition() {
        return position;
    }
 
    public String getTexture() {
        return texture;
    }
 
    public void setScale(Vector2f scale) {
        gui.setScale(scale);
    }
 
    public void setPosition(Vector2f position) {
        gui.setPosition(position);
    }
}
