package guiRenderer;

import org.lwjgl.util.vector.Vector2f;

public class TransparentGuiTexture {
	
	private int texture;
	public Vector2f position;
	private Vector2f scale;
	public float alphaValue;

	public TransparentGuiTexture(int texture, Vector2f position, Vector2f scale, float alpha) 
	{
		this.texture    = texture;
		this.position   = position;
		this.scale      = scale;
		setAlphaValue(alpha);
	}

	public float getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(float alphaValue) {
		this.alphaValue = alphaValue;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
}
