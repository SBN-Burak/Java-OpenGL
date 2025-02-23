#version 140

in vec2 textureCoords;
in float pixelAlpha;

out vec4 out_Color;

uniform sampler2D guiTexture;

void main(void){

	out_Color = vec4(texture(guiTexture, textureCoords).rgb, pixelAlpha);

}