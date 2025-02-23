package shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); // <--- Matrix4f(4x4=16)
	
	public ShaderProgram(String vertexFile,String fragmentFile)
	{
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		 // shaderlar y�klendi�inde bunuda y�kle ki b�t�n uni.varia. eri�ebilelim
		getAllUniformLocations();
	}
	
	// b�t�n shaderprogramlar� i�in uniform.varia.. al�yoruz
	protected abstract void getAllUniformLocations();
	
	// uniform variable 'y� bulmam�za yard�mc� oldu
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	 // UniformLocation'a float valuelar atmam�z i�in
	protected void loadFloat(int location,float value) {
		GL20.glUniform1f(location, value);
	}
	protected void loadInt(int location,int value) {
		GL20.glUniform1i(location, value);
	}
	//UniformLocation'a 3 y�nl� vector atmam�z i�in
	protected void loadVector(int location,Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	//UniformLocation'a 2 y�nl� vector atmam�z i�in
		protected void load2DVector(int location,Vector2f vector) {
			GL20.glUniform2f(location, vector.x, vector.y);
		}
	//UniformLocation'a bool atmam�z i�in
	protected void loadBoolean(int location,boolean value) {
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	//UniformLocation'a matrix atmam�z i�in ama bunu y�klemek i�in FloatBuffer ihtiya� var en �stte
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	public void start()
	{
		GL20.glUseProgram(programID);
	}	
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public void cleanUp()
	{
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute,String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	private static int loadShader(String file, int type){
		  StringBuilder shaderSource = new StringBuilder();
		  try{
		   InputStream in = Class.class.getResourceAsStream(file);
		   BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		   String line;
		   while((line = reader.readLine())!=null){
		    shaderSource.append(line).append("//\n");
		   }
		   reader.close();
		  }catch(IOException e){
		   e.printStackTrace();
		   System.exit(-1);
		  }
		  int shaderID = GL20.glCreateShader(type);
		  GL20.glShaderSource(shaderID, shaderSource);
		  GL20.glCompileShader(shaderID);
		  if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			  System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			  System.err.println("Could not compile shader!");
			  System.exit(-1);
		  }
		  return shaderID;
	}
}