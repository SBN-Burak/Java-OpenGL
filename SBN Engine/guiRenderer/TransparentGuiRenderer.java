package guiRenderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class TransparentGuiRenderer {
	
	private final RawModel quad;
	private TransparentGuiShader logoShader;
	
	public TransparentGuiRenderer(Loader loader) {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1}; //origin merkezde
		//float[] positions = {0, 0, 0, -1, 1, 0, 1, -1};		//origin sol üstte HealthBar için ama guivertexShaderdan deðiþtir.
		quad = loader.loadToVAO(positions);
		logoShader = new TransparentGuiShader();
	}
	
	public void render(TransparentGuiTexture gui) {
		logoShader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
		Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
		logoShader.loadTransformation(matrix);
		logoShader.shaderAlpha(gui);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		glEnable(GL11.GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		logoShader.stop();
	}
	
	public void cleanUp() {
		logoShader.cleanUp();
	}
}
