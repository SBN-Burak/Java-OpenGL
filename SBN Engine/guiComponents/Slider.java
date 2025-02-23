package guiComponents;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import guiRenderer.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public abstract class Slider implements ISlider {

	private String textureArka;
	private String texture÷n;
	
    private Vector2f ArkaGUIposition, ArkaGUIscale;
    private Vector2f ÷nGUIposition, ÷nGUIscale;
    
    private GuiTexture guiArka;
    private GuiTexture gui÷n;
    
    public float Value;
	
    public Slider(Loader loader, String guiArka, String gui÷n, Vector2f ArkaGUIposition, Vector2f ArkaGUIscale,
    		Vector2f ÷nGUIposition, Vector2f ÷nGUIscale) {
    	
        this.textureArka = guiArka;
        this.texture÷n = gui÷n;
        
        this.ArkaGUIposition = ArkaGUIposition;
        this.ArkaGUIscale = ArkaGUIscale;
        
        this.÷nGUIposition = ÷nGUIposition;
        this.÷nGUIscale = ÷nGUIscale;
        
        this.guiArka = new GuiTexture(loader.loadGUITexture(guiArka), ArkaGUIposition, ArkaGUIscale);
        this.gui÷n   = new GuiTexture(loader.loadGUITexture(gui÷n), ÷nGUIposition, ÷nGUIscale);
    }
    
    public void checkHover() 
	{
    			
    	Vector2f location÷n = gui÷n.getPosition();
    	Vector2f locationArka = guiArka.getPosition();
        Vector2f scaleArka    = guiArka.getScale();
        Vector2f mouseCoordinates = DisplayManager.getNormalizedMouseCoordinates();
    	
        if (locationArka.y + scaleArka.y > -mouseCoordinates.y && locationArka.y - scaleArka.y < -mouseCoordinates.y &&
        		locationArka.x + scaleArka.x > mouseCoordinates.x && locationArka.x - scaleArka.x < mouseCoordinates.x) 
        {
        	while(Mouse.next()) 
        	{       			
        		if (Mouse.isButtonDown(0)) {
        			location÷n.x = mouseCoordinates.x;
        			
        			Value = (int)(location÷n.x * 1539.90610);

        			tut«ek();
        			
        		}
        	}
        }
        
        else {
        	gui÷n.setScale(this.÷nGUIscale);
        	guiArka.setScale(this.ArkaGUIscale);
        }
    	
	}
    
    public void hide(List<GuiTexture> guiTextures) {
        stopRender(guiTextures);
    }
 
    public void show(List<GuiTexture> guiTextures) {
        startRender(guiTextures);
    }
    
    private void startRender(List<GuiTexture> guiTextureList) {
    	guiTextureList.add(guiArka);
    	guiTextureList.add(gui÷n);
    }
    
    private void stopRender(List<GuiTexture> guiTextureList) {
    	guiTextureList.remove(gui÷n);
    	guiTextureList.remove(guiArka);
    }
    
    public Vector2f get÷nGUIposition() {
		return ÷nGUIposition;
	}

	public void set÷nGUIposition(Vector2f ˆnGUIposition) {
		÷nGUIposition = ˆnGUIposition;
	}

	public Vector2f get÷nGUIscale() {
		return ÷nGUIscale;
	}

	public void set÷nGUIscale(Vector2f ˆnGUIscale) {
		÷nGUIscale = ˆnGUIscale;
	}

	public Vector2f getArkaGUIposition() {
		return ArkaGUIposition;
	}

	public void setArkaGUIposition(Vector2f arkaGUIposition) {
		ArkaGUIposition = arkaGUIposition;
	}

	public Vector2f getArkaGUIscale() {
		return ArkaGUIscale;
	}

	public void setArkaGUIscale(Vector2f arkaGUIscale) {
		ArkaGUIscale = arkaGUIscale;
	}

	public GuiTexture getGuiTexture() {
        return gui÷n;
    }
 
    public String getArkaTexture() {
        return textureArka;
    }
    
    public String get÷nTexture() {
        return texture÷n;
    }
 
    public void setScale(Vector2f scale) {
    	guiArka.setScale(scale);
    	gui÷n.setScale(scale);
    }
 
    public void setPosition(Vector2f position) {
    	guiArka.setPosition(position);
    	gui÷n.setPosition(position);
    }
}
