#version 140

in vec2 position;

out vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform bool Button;

void main(void){

	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
	textureCoords = vec2(position.x, -position.y);//vec2(position.x, -position.y); //origin sol üstte //vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0); //origin merkezde
	
}