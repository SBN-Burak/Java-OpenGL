package models;

public class RawModel {

	private int vaoID;
	private int vertexCounter;
	
	public RawModel(int vaoID,int vertexCounter) {
		this.vaoID = vaoID;
		this.vertexCounter = vertexCounter;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCounter;
	}
}