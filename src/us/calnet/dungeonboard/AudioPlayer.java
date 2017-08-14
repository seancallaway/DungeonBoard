package us.calnet.dungeonboard;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer extends Thread {

	private String path;
	private boolean loop;
	private Player player;
	
	public AudioPlayer(String path, boolean loop) {
		this.path = path;
		this.loop = loop;
	}
	
	public void run() {
		
		try {
			do {
				FileInputStream fis = new FileInputStream(path);
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);
				player.play();				
			} while (loop);
		} catch (Exception ex) {
			//TODO: Add better error handling.
			System.err.println(ex.toString());
		}
		
	}
	
	public void close() {
		loop = false;
		player.close();
		this.interrupt();
	}
}
