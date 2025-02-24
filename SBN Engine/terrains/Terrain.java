package terrains;

import renderEngine.Loader;
import textures.TerrainTexturePack;
import textures.TerrainTextures;
import toolbox.Maths;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class Terrain {

	private static final float SIZE = -1600;
	private static final float MAX_HEIGHT = 50;
	private static final float MAX_PIXEL_COLOUR = 255 * 255 * 255;
	
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTextures blendMap;
	
	private float[][] heights;
	
	public Terrain(float gridX,float gridZ,Loader loader,TerrainTexturePack texturePack,
			TerrainTextures blendMap, String heightMap) {
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
	}
	public float getX() {
		return x;
	}
	public float getZ() {
		return z;
	}
	public RawModel getModel() {
		return model;
	}
	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}
	public TerrainTextures getBlendMap() {
		return blendMap;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if(gridX >=  heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}
		float XCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float ZCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if(XCoord <= (1 - ZCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(XCoord, ZCoord));
		}else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(XCoord, ZCoord));
		}
		return answer;
	}
	
	private RawModel generateTerrain(Loader loader, String HeightMap) { // KOPYALA-YAPI�TIR
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(Class.class.getResource(HeightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	// Bu fonksiyon olmasayd� normaller b�t�n y�zeylerde 90* olurdu yani g�lge olmazd�... Yani �ok �nemli.
	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float HeightL = getHeight(x - 1, z, image);
		float HeightR = getHeight(x + 1, z, image);
		float HeightD = getHeight(x, z - 1, image);
		float HeightU = getHeight(x, z + 1, image);
		Vector3f normal = new Vector3f(HeightL - HeightR, 2f, HeightD - HeightU);
		normal.normalise();
		return normal;
	}
	
	private float getHeight(int x, int z, BufferedImage imagee) {
		if(x<0 || x >= imagee.getHeight() || z<0 || z >= imagee.getHeight()) {
			return 0;
		}
		
		float height = imagee.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}
}