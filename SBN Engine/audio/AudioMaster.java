package audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioMaster {

	private static List<Integer> buffers = new ArrayList<Integer>();
	
	public static void initSound() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	// Listener biziz :)
	public static void setListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static int loadSound(String fileName) {
		
		FileInputStream fin = null;
	    BufferedInputStream bin = null;
		
	    int buffer = AL10.alGenBuffers();
	    buffers.add(buffer);
	    
	    try
	    {
	        fin = new FileInputStream("Resources" + fileName);
	        bin = new BufferedInputStream(fin);
	    }
	    catch(FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    
	    WaveData waveFile = WaveData.create(bin);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
	    
		return buffer;
	}
	
	public static void cleanSound() {
		
		for(int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		
		AL.destroy();
	}
	
}
