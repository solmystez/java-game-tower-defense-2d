package project_oop;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	private boolean started = false;
	private boolean paused = false;

	private float value = 0.0F;
	private Clip clip;
	private FloatControl volume;
	private long clipTime;

	public Sound() {
	}

	public void play() {
		URL musicURL = Sound.class.getResource("music.wav");

		if (!started) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(musicURL));
			} catch (LineUnavailableException | IOException | javax.sound.sampled.UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			clip.loop(-1);

			volume = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
			started = true;
		} else if (paused) {
			clip.setMicrosecondPosition(clipTime);
			clip.loop(-1);
			paused = false;
		}
	}

	public void pause() {
		if (started) {
			clipTime = clip.getMicrosecondPosition();
			clip.stop();
			paused = true;
		}
	}

	public void changeVolume(float value) {
		this.value += value;
		if ((this.value > -80.0F) && (this.value < 7.0F)) {
			volume.setValue(this.value);
		} else if (this.value < -80.0F) {
			this.value = -80.0F;
		} else if (this.value > 6.0F) {
			this.value = 6.0F;
		}

		System.out.println(this.value + "  Sound class");
	}
}
