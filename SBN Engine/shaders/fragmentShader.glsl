#version 460 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

//const float levels = 3.0; // Cell Shading icin :D

void main(void)
{

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDotl = dot(unitNormal,unitLightVector);
	float brightness = max(nDotl, 0.2);

	//float level = floor(brightness * levels); // Cell Shading icin :D
	//brightness = level / levels;			    // Cell Shading icin :D

	vec3 diffuse = brightness * lightColour;
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
	
	float specularFactor = dot(reflectedLightDirection,unitVectorToCamera);
	specularFactor = max(specularFactor,0.0);
	float dampedFactor = pow(specularFactor,shineDamper);
	
	//level = floor(dampedFactor * levels); // Cell Shading icin :D
	//dampedFactor = level / levels;		// Cell Shading icin :D
	
	vec3 finalSpecular = dampedFactor * reflectivity * lightColour;
	
	vec4 textureColour = texture(textureSampler,pass_textureCoords);
	if(textureColour.a < 0.5) {
		discard;
	}
	
	out_Color = vec4(diffuse,1.0) * textureColour + vec4(finalSpecular,1.0);
	out_Color = mix(vec4(skyColour,1.0),out_Color,visibility);
	
}