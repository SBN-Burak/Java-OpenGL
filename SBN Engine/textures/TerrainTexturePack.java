package textures;

public class TerrainTexturePack {

	private TerrainTextures arkaplanTexture;
	private TerrainTextures rTexture;
	private TerrainTextures gTexture;
	private TerrainTextures bTexture;
	
	public TerrainTexturePack(TerrainTextures arkaplanTexture, TerrainTextures rTexture, TerrainTextures gTexture,
			TerrainTextures bTexture) {
		this.arkaplanTexture = arkaplanTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}

	public TerrainTextures getArkaplanTexture() {
		return arkaplanTexture;
	}

	public TerrainTextures getrTexture() {
		return rTexture;
	}

	public TerrainTextures getgTexture() {
		return gTexture;
	}

	public TerrainTextures getbTexture() {
		return bTexture;
	}
}