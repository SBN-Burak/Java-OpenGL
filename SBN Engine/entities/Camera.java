package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

public class Camera
{
	
	public Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 20; // yukarý aþaðý
	private float yaw 	= 40; // sað sol
 	private float roll  = 0;  // eðiklik
 	
 	private float rotationSpeed = 6.0f;
 	private float speed = 1.2f;
 	
 	public void move(Terrain terrain) {
 		
 		yaw   += Mouse.getDX() / rotationSpeed;
 		pitch -= Mouse.getDY() / rotationSpeed ;
 		
 		if(pitch <= -90) {
 			pitch = -90;
 		}
 		
 		if(pitch >= 90) {
 			pitch = 90;
 		}
 		
 		float terrainHeight = terrain.getHeightOfTerrain(position.x, position.z) + 10;
		if(position.y < terrainHeight) {
			position.y = terrainHeight;
		}
 		
 		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
 	        position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
 	        position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
 	    }
 	    else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
 	        position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
 	        position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;
 	    }
 	    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
 	        position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
 	        position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;
 	    }
 	    else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
 	        position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
 	        position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;
 	    }
 	    
 	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
 	    	position.y += speed * 3.0f;
 	    }
 	    
 	    position.y -= speed;
 		
 	}
 	
	public Vector3f getPosition() 
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
}
