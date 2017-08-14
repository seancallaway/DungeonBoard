package us.calnet.dungeonboard;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class DungeonBoard extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button();
		btn.setText("Play sound");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					FileInputStream fis = new FileInputStream(new File("skullsound2.mp3"));
					BufferedInputStream bis = new BufferedInputStream(fis);
					Player player = new Player(bis);
					player.play();
				} catch (FileNotFoundException ex) {
					System.out.println(ex.getMessage());
				} catch (JavaLayerException ex) {
					System.out.println("Whoops!");
				}
			}
		});
		
		StackPane root = new StackPane();
		root.getChildren().add(btn);
		
		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("DungeonBoard");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// Check for existence of media directory
		File froot = new File(System.getProperty("user.home") + File.separator + "DungeonBoard");
		File fsub1 = new File(System.getProperty("user.home") + File.separator + "DungeonBoard" + File.separator + "Backgrounds");
		File fsub2 = new File(System.getProperty("user.home") + File.separator + "DungeonBoard" + File.separator + "Effects");
		
		if (!(froot.isDirectory() && fsub1.isDirectory() && fsub2.isDirectory())) {
			if (!(fsub1.mkdirs() && fsub2.mkdir())) {
				System.err.println("Media directories could not be created!");
				System.exit(1);
			}
		}
		
		launch(args);
	}
	
}
