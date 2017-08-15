package us.calnet.dungeonboard;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class Player implements BasicPlayerListener, Runnable {

	private String m_path;
	private double m_gain;
	private boolean m_playing;
	private volatile boolean m_running;
	private BasicController m_control;
	private BasicPlayer m_player;
	private Thread m_thread;
	
	public Player(String path, int volume) {
		m_path = path;
		if (volume > 100) {
			m_gain = 1.0;
		}
		else if (volume < 0) {
			m_gain = 0.0;
		}
		else {
			m_gain = volume / 100;
		}
		m_playing = false;
		m_running = false;
		m_thread = null;
	}
	
	private void go() {
		m_playing = false;
		m_player = new BasicPlayer();
		m_control = (BasicController) m_player;
		m_player.addBasicPlayerListener(this);
		
		try {
			try {
				m_control.open(new File(m_path));
			} catch (Exception ex) {
				System.err.println("[ERROR] Player: " + ex.getMessage());
			}
			
			m_control.play();
			//m_control.setGain(m_gain);
			//m_control.setPan(0.0);
		} catch (BasicPlayerException ex) {
			System.err.println("[ERROR] Player: " + ex.getMessage());
		}
		
	}
	
	public boolean isPlaying() {
		if (m_player != null && m_player.getStatus() == BasicPlayer.PLAYING){
			return true;
		}	
		return false;
	}
	
	@Override
	public void opened(Object arg0, Map arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while (m_running) {
			//System.out.println("Status: " + isPlaying());
			if (m_playing) {
				System.out.println("Going!");
				go();
			}
		}
	}
	
	public synchronized void start() {
		m_running = true;
		m_thread = new Thread(this);
		m_thread.start();
	}
	
	public synchronized void close() {
		m_running = false;
		try {
			m_thread.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	public void pause() {
		m_playing = false;
		try {
			m_control.stop();
		} catch (BasicPlayerException ex) {
			System.err.println(ex.toString());
		}
	}
	
	public void play() {
		System.out.println("Playing!");
		m_playing = true;
	}
	
}
