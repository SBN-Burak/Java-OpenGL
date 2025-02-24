package audio;

import org.lwjgl.openal.AL10;

public class Source {

	private int sourceID;
	
	public Source() {
		sourceID = AL10.alGenSources();
		AL10.alSourcef(sourceID, AL10.AL_GAIN, 1);
		AL10.alSourcef(sourceID, AL10.AL_PITCH, 1);
		AL10.alSource3f(sourceID, AL10.AL_POSITION, 0, 0, 0);
	}
	
	public void playAudio(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceID);
	}
	
	public void loopAudio(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, 1);
		AL10.alSourcePlay(sourceID);
	}
	
	public void stopAudio(int buffer) {
		AL10.alSourceStop(sourceID);
	}
	
	public void cleanAudioSource() {
		AL10.alDeleteSources(sourceID);
	}
	
}
