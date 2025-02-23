package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {

	public static float clamp(float val, float min, float max) {
 	    return Math.max(min, Math.min(max, val));
 	}
	
	public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
        Vector3f forward = new Vector3f(0, 0, 0);
        Vector3f.sub(center, eye, forward);
        forward.normalise();

        Vector3f side = new Vector3f(0, 0, 0);
        Vector3f.cross(forward, up, side);
        side.normalise();

        Vector3f.cross(side, forward, up);

        Matrix4f matrix = new Matrix4f();
        matrix.m00 = side.x;
        matrix.m01 = side.y;
        matrix.m02 = side.z;
        matrix.m10 = up.x;
        matrix.m11 = up.y;
        matrix.m12 = up.z;
        matrix.m20 = -forward.x;
        matrix.m21 = -forward.y;
        matrix.m22 = -forward.z;
        matrix.invert();

        return matrix;
     } 
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f translation,
			float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity(); // matrixi sýfýrlýyor yeni kimlik oluþturuyor
								// kýsaca matrixi resetliyo
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0),
				matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0),
				matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1),
				matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera){ // kopyaladým view mattrix yapýk ama roll kullanmadýk
		  Matrix4f viewMatrix = new Matrix4f();				// rollu sonra ben kullanýrým
		  viewMatrix.setIdentity();
		  Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix,
		    viewMatrix);
		  Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix,
		    viewMatrix);
		  Vector3f cameraPos = camera.getPosition();
		  Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		  Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		  return viewMatrix;
		  }
}