package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TexturedModel;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;

	private static final float FOV 		  = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE  = 1000f;
	private static final float RED 	 = .6f;
	private static final float GREEN = .6f;
	private static final float BLUE	 = .6f;

	private Matrix4f projectionMatrix;

	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();

	// Bu liste ise TexturedModel'deki modelleri listeliyor. ayný modelden birdan
	// fazla varsa a1,a2,a3 þeklinde
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer() {
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}

	public static void enableCulling() {
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public static void disableCulling() {
		glDisable(GL_CULL_FACE);
	}

	public void render(Light sun, Camera camera) {
		ClearScreen();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
		terrainShader.start();
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		terrains.clear();
		entities.clear();
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void ClearScreen() {
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(RED, GREEN, BLUE, 1f);
		glEnable(GL_FRAMEBUFFER_SRGB);
	}

	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}
}