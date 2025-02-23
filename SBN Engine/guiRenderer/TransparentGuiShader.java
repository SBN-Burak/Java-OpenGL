package guiRenderer;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class TransparentGuiShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/guiRenderer/logoVS.glsl";
    private static final String FRAGMENT_FILE = "/guiRenderer/logoFS.glsl";
     
    private int location_transformationMatrix;
    private int location_alpha;
 
    public TransparentGuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
    
    public void shaderAlpha(TransparentGuiTexture gui) {
    	super.loadFloat(location_alpha, gui.getAlphaValue());
    }
    
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_alpha = super.getUniformLocation("vertexAlpha");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
