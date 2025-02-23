package engineTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import physicsEngine.Bullet;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.Source;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexturePack;
import textures.TerrainTextures;
import toolbox.MousePicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guiComponents.Button;
import guiComponents.Slider;
import guiRenderer.GuiRenderer;
import guiRenderer.GuiTexture;
import guiRenderer.TransparentGuiRenderer;
import guiRenderer.TransparentGuiTexture;

public class Loop {
	
	public int frames = 0;
	public int ticks  = 0;
	
	public static float canAzaltma  = 0.45f;
	public float        kalpK���lme = 0.050f;
	public boolean      menu        = false;
	private boolean     editModu    = false;
	private boolean     fade 		= true;
	
	public Loop() throws IOException 
	{
	
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		TextMaster.init(loader);
		FontType font = new FontType(loader.loadGUITexture("/res/arial"), "/res/arial");
		GUIText text = new GUIText("", .6f, font, new Vector2f(.45f, .02f), 1, true);
		text.setColour(1, 1, 1);
		
		ModelData korkuncData = OBJFileLoader.loadOBJ("/res/korkunc");
		RawModel korkuncDataa = loader.loadToVAO(korkuncData.getVertices(), korkuncData.getTextureCoords(), korkuncData.getNormals(), korkuncData.getIndices());
		TexturedModel korkunc = new TexturedModel(korkuncDataa, 
				new ModelTexture(loader.loadTexture("/res/korkunc")));
		
		ModelData kediData = OBJFileLoader.loadOBJ("/res/cat");
		RawModel kediDataa = loader.loadToVAO(kediData.getVertices(), kediData.getTextureCoords(), kediData.getNormals(), kediData.getIndices());
		TexturedModel kedi = new TexturedModel(kediDataa, 
				new ModelTexture(loader.loadTexture("/res/k�p")));
		
		ModelData k�pData = OBJFileLoader.loadOBJ("/res/k�p");
		RawModel k�pDataa = loader.loadToVAO(k�pData.getVertices(), k�pData.getTextureCoords(), k�pData.getNormals(), k�pData.getIndices());
		TexturedModel k�pp = new TexturedModel(k�pDataa, 
				new ModelTexture(loader.loadTexture("/res/k�p")));
		
		ModelData ismimData = OBJFileLoader.loadOBJ("/res/ismim");
		RawModel ismimDataa = loader.loadToVAO(ismimData.getVertices(), ismimData.getTextureCoords(), ismimData.getNormals(), ismimData.getIndices());
		TexturedModel ismim = new TexturedModel(ismimDataa, 
				new ModelTexture(loader.loadTexture("/res/ismim")));
		
		ModelData agac3Data = OBJFileLoader.loadOBJ("/res/agac3");
		RawModel agac3Dataa = loader.loadToVAO(agac3Data.getVertices(), agac3Data.getTextureCoords(), agac3Data.getNormals(), agac3Data.getIndices());
		TexturedModel lowpolyagac = new TexturedModel(agac3Dataa, 
				new ModelTexture(loader.loadTexture("/res/agac3")));
		
		ModelData cimenData = OBJFileLoader.loadOBJ("/res/cimenmodel");
		RawModel cimenDataa = loader.loadToVAO(cimenData.getVertices(), cimenData.getTextureCoords(), cimenData.getNormals(), cimenData.getIndices());
		TexturedModel cimen = new TexturedModel(cimenDataa, 
				new ModelTexture(loader.loadTexture("/res/cimen")));
		cimen.getTexture().setHasTransparancy(true);
		cimen.getTexture().setUseFakeLighting(true);
		
		ModelData circleData = OBJFileLoader.loadOBJ("/res/circle");
		RawModel circleDataa = loader.loadToVAO(circleData.getVertices(), circleData.getTextureCoords(), circleData.getNormals(), circleData.getIndices());
		TexturedModel circle = new TexturedModel(circleDataa, 
				new ModelTexture(loader.loadTexture("/res/red")));
		
		ModelData fernData = OBJFileLoader.loadOBJ("/res/fern");
		RawModel fernDataa = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		TexturedModel fern = new TexturedModel(fernDataa, 
				new ModelTexture(loader.loadTexture("/res/fern")));
		fern.getTexture().setHasTransparancy(true);
		fern.getTexture().setUseFakeLighting(true);
		fern.getTexture().setNumberOfRows(2);
		
		ModelData sponzaData = OBJFileLoader.loadOBJ("/res/circle");
		RawModel sponzaDataa = loader.loadToVAO(sponzaData.getVertices(), sponzaData.getTextureCoords(), sponzaData.getNormals(), sponzaData.getIndices());
		TexturedModel sponza = new TexturedModel(sponzaDataa, 
				new ModelTexture(loader.loadTexture("/res/red")));
		
		//*************TERRAIN TEXTURE I�LERI****************
		
		TerrainTextures backgroundTexture = new TerrainTextures(loader.loadTexture("/res/grass"));
		TerrainTextures rTexture          = new TerrainTextures(loader.loadTexture("/res/dirt"));
		TerrainTextures gTexture 		  = new TerrainTextures(loader.loadTexture("/res/cicek"));
		TerrainTextures bTexture          = new TerrainTextures(loader.loadTexture("/res/stone"));
		TerrainTexturePack texturePack    = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTextures blendMap          = new TerrainTextures(loader.loadTexture("/res/blendMap"));
		
		//***************************************************
		
		Light light = new Light(new Vector3f(0, 10000, -10000),new Vector3f(1, 1, 1)); //sa�daki ���k rengi soldaki ���k a��s�
		
		Camera camera = new Camera();
		
		Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap, "/res/heightMap2"); //0,0
		
		List<Entity> varliklar = new ArrayList<Entity>();
		List<Entity> �izimVarliklar = new ArrayList<Entity>();
		
		// Adding Entities into the world (*Random) \\ 
		
		Random random = new Random();
		
		for(int i=0;i<2000;i++) 
		{
			float agacxx = random.nextFloat() * -1600;
			float agaczz = random.nextFloat() * -1600;
			float agacyy = terrain.getHeightOfTerrain(agacxx, agaczz);
			varliklar.add(new Entity(lowpolyagac,
			new Vector3f(agacxx,agacyy,agaczz), 0, random.nextFloat() * 360, 0, 0.8f));
		}
		
		for(int i=0;i<10000;i++) 
		{
			float cimenx = random.nextFloat() * -1600;
			float cimenz = random.nextFloat() * -1600;
			float cimeny = terrain.getHeightOfTerrain(cimenx, cimenz);
			varliklar.add(new Entity(cimen,
			new Vector3f(cimenx,cimeny,cimenz), 0, random.nextFloat() * 360, 0, 1f));
		}
		
		for(int i=0;i<10000;i++) 
		{
			float fernx = random.nextFloat() * -1600;
			float fernz = random.nextFloat() * -1600;
			float ferny = terrain.getHeightOfTerrain(fernx, fernz);
			varliklar.add(new Entity(fern,
			new Vector3f(fernx,ferny,fernz), 0, random.nextFloat() * 360, 0, 0.4f));
		}
		
		MasterRenderer renderer = new MasterRenderer();
		MousePicker mousePicker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		Entity Adam       = new Entity(korkunc,new Vector3f(-140,-9,-140)  ,0,0,0,3);
		Entity kedii      = new Entity(kedi,   new Vector3f(-110,-9,-140) ,0, 45,0, 5);
		Entity ismimm     = new Entity(ismim,  new Vector3f(-110,-9,-130), 0, 0, 0, 1);
		Entity editCircle = new Entity(circle, new Vector3f(0,0,0), 0, 0, 0, 1);
		Entity k�p        = new Entity(k�pp,   new Vector3f(-100,100,-100)  ,0,0,0,2);
		Entity k�p2       = new Entity(k�pp,   new Vector3f(-100,80,-100)  ,0,0,0,2);
		
		varliklar.add(Adam);
		varliklar.add(kedii);
		varliklar.add(ismimm);
		
		// FPS CALCULATIONS \\
		
		long before = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		
		// AUDIO \\
		
		AudioMaster.initSound();
		AudioMaster.setListenerData();
		
		int buffer2       = AudioMaster.loadSound("/res/loopSound.wav");
		int d�gmeSesi     = AudioMaster.loadSound("/res/d��me2.wav");
		//int yap�KoymaSesi = AudioMaster.loadSound("/res/yap�Koyma.wav");
		int yap�SilmeSesi = AudioMaster.loadSound("/res/yap�Silme.wav");
		
		Source source = new Source();
		Source source2 = new Source();
		Source d�gmeKayna�� = new Source();
		Source yap�Silme = new Source();
		
		//source2.loopAudio(buffer2);
		
		// STATIC GUIS \\
		 
		List<GuiTexture> guis   = new ArrayList<GuiTexture>();
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		//GuiTexture gui          = new GuiTexture(loader.loadTexture("/res/healthArka"), new Vector2f(0.50f, 0.95f), new Vector2f(canAzaltma, 0.03f));
		//GuiTexture gui2 		= new GuiTexture(loader.loadTexture("/res/healthLogo"), new Vector2f(0.475f, 0.955f), new Vector2f(kalpK���lme, kalpK���lme));
		GuiTexture gui3 		= new GuiTexture(loader.loadTexture("/res/vignette"), new Vector2f(-1f, 1f), new Vector2f(2, 2));
		GuiTexture debugMenuPanel = new GuiTexture(loader.loadTexture("/res/fade"), new Vector2f(.90f, .943f), new Vector2f(0.09f, 0.017f));
		
		//guis.add(gui);
		//guis.add(gui2);
		guis.add(gui3);
		guis.add(debugMenuPanel);
		// SPLASH SCREEN (LOGO) \\
	
		TransparentGuiRenderer logoRenderer = new TransparentGuiRenderer(loader);
		TransparentGuiTexture  LOGO         = new TransparentGuiTexture(loader.loadTexture("/res/fade"), new Vector2f(0f, 0f), new Vector2f(1, 1), 1);
		
		// DYNAMIC GUIS \\
		
		List<GuiTexture> buttons = new ArrayList<GuiTexture>();
		List<GuiTexture> sliders = new ArrayList<GuiTexture>();
		
		Button ��kButton = new Button(loader, "/res/�ik", new Vector2f(-.96f, .96f), new Vector2f(0.03f, 0.03f)) 
		{
			@Override
			public void click() {
				d�gmeKayna��.playAudio(d�gmeSesi);
				clickAnimation(0.003f);
				fade = false;
			}
			@Override
			public void isHovering() {
				isHoveringAnimation(0.006f);
			}
		};
		
		Button editButton = new Button(loader, "/res/editIcon", new Vector2f(-.96f + 0.1f, .96f), new Vector2f(0.03f, 0.03f)) 
		{
			@Override
			public void click() {
				d�gmeKayna��.playAudio(d�gmeSesi);
				clickAnimation(0.003f);
				editModu = !editModu;
			}
			@Override
			public void isHovering() {
				isHoveringAnimation(0.006f);
			}
		};
		
		Button m�zikKapat = new Button(loader, "/res/musicMuteIcon", new Vector2f(-.96f + 0.2f, .96f), new Vector2f(0.03f, 0.03f))
		{
			boolean m�zik;
			@Override
			public void click() {
				m�zik = !m�zik;
				d�gmeKayna��.playAudio(d�gmeSesi);
				clickAnimation(0.003f);
				if(!m�zik) {
					source2.stopAudio(buffer2);
				}
				else if(m�zik) {
					source2.loopAudio(buffer2);
				}
			}
			@Override
			public void isHovering() {
				isHoveringAnimation(0.006f);
			}
		};
		
		Slider slider = new Slider(loader, "/res/sliderArka", "/res/slider�n", new Vector2f(-.96f + 0.4f, .96f), new Vector2f(0.03f, 0.03f),
				new Vector2f(-.96f + 0.4f, .96f), new Vector2f(0.03f, 0.03f)) {
			
			@Override
			public void tut�ek() 
			{
				Adam.getPosition().x = Value;
			}
			
		};
		
		// 3D COLLISIONS \\
		//AABB collision = new AABB();
		
		// BULLET PHYSICS AYARLARI \\
		Bullet bullet = new Bullet();
		bullet.initPhysics();
		bullet.addGround();
				
		// GAME LOOP \\
		while (!Display.isCloseRequested()) 
		{
			
			bullet.addBox1(k�p);
			bullet.addBox2(k�p2);
			bullet.updatePhysics();
			
			long now = System.nanoTime();
			double elapsed = now - before;
			
			//collision.canAzalt(player, kedii, gui);
			
			if(menu) {
				��kButton.checkHover();
				editButton.checkHover();
				m�zikKapat.checkHover();
				slider.checkHover();
			}
			
			mousePicker.update();
			Vector3f terrainPoint = mousePicker.getCurrentTerrainPoint();
			if(terrainPoint != null) 
			{
				if(menu) {
					if(editModu) 
					{
						varliklar.add(editCircle);
						editCircle.setPosition(terrainPoint);
						if(Mouse.isButtonDown(1)) {
							�izimVarliklar.add(new Entity(sponza, terrainPoint, 0, 0, 0, 0.8f));
							//d�gmeKayna��.playAudio(yap�KoymaSesi);
						}
						else if(Mouse.isButtonDown(2)) {
							�izimVarliklar.clear();
							yap�Silme.playAudio(yap�SilmeSesi);
						}
						else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_Z)) {
							if(�izimVarliklar.size() > 0) {						
								�izimVarliklar.remove(�izimVarliklar.size() -1);
								yap�Silme.playAudio(yap�SilmeSesi);
							}
						}
					}
				}
				
				else if(!menu) {
					editModu = false;
					varliklar.remove(editCircle);
				}
			}
			
			float terrainHeight = terrain.getHeightOfTerrain(Adam.getPosition().getX(), Adam.getPosition().getZ());
			
			Adam.increaseRotation(0, 2, 0);
			Adam.setPosition(new Vector3f(camera.getPosition().getX() - 20f, terrainHeight, camera.getPosition().getZ() - 20f));
			
			while (Keyboard.next()) {
				
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					if (!Keyboard.getEventKeyState()) { // E�er Key released olursa
						
						menu = !menu;
						
						if(menu) {
							��kButton.show(buttons);
							editButton.show(buttons);
							m�zikKapat.show(buttons);
							slider.show(sliders);
							Mouse.setGrabbed(false);
						}
						else if(!menu) {
							��kButton.hide(buttons);
							editButton.hide(buttons);
							m�zikKapat.hide(buttons);
							slider.hide(sliders);
							Mouse.setGrabbed(true);
						}
				    }
				}
			}
			
			if(elapsed > ns) 
			{
				//update(); //TODO:
				ticks++;
				before += ns;
			}
			
			//render(); //TODO:
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) 
			{
				text.setText("FPS: " + frames + " TICKS: " + ticks);
				text.remove();
				frames = 0;
				timer += 1000;
			}
	
			DisplayManager.inputs();
			
			camera.move(terrain);
			
			renderer.processTerrain(terrain);
			renderer.processEntity(k�p); // TODO: stencil test kaldir
			renderer.processEntity(k�p2);
			
			for(Entity model1:varliklar) 
			{
				renderer.processEntity(model1);
			}
			
			for(Entity model2:�izimVarliklar) 
			{
				renderer.processEntity(model2);
			}
			
			// FADE (IN/OUT) EFFECT \\
			
			if(fade) {
				LOGO.alphaValue -= 0.020f;
				if(LOGO.alphaValue < 0) {
					LOGO.alphaValue = 0;
				}
			}
			else if(!fade) {
				LOGO.alphaValue += 0.020f;
				if(LOGO.alphaValue > 1) {
					LOGO.alphaValue = 1;
					if(LOGO.alphaValue == 1) {
						System.exit(0);
					}
				}
			}
			
			renderer.render(light, camera);
			guiRenderer.render(guis);
			guiRenderer.render(buttons);
			guiRenderer.render(sliders);
			TextMaster.render();
			logoRenderer.render(LOGO);
			
			DisplayManager.updateDisplay();
		}
		
		// TIDYNG \\
		
		bullet.cleanUpPhysics();
		loader.cleanUp();
		TextMaster.cleanUp();
		renderer.cleanUp();
		guiRenderer.cleanUp();
		logoRenderer.cleanUp();
		AudioMaster.cleanSound();
		source.cleanAudioSource();
		
		DisplayManager.closeDisplay();
	}
	
}
