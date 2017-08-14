package us.calnet.dungeonboard;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class DungeonBoard extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		File backgroundFolder = new File(System.getProperty("user.home") + File.separator + "DungeonBoard" + File.separator + "Backgrounds");
		File[] backgroundFiles = backgroundFolder.listFiles();
		ObservableList<String> backgroundFilenames = FXCollections.observableArrayList();	
		for (int i = 0; i < backgroundFiles.length; i++) {
			backgroundFilenames.add(backgroundFiles[i].getName());
		}
		ComboBox backgroundComboBox = new ComboBox(backgroundFilenames);
		if (backgroundComboBox.getItems().isEmpty()) {
			backgroundComboBox.getItems().add("No files found!");
			backgroundComboBox.setValue("No files found!");
		}
		
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
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.getChildren().addAll(backgroundComboBox, btn);
		grid.add(hbox, 0, 0);
		
		FlowPane fp = new FlowPane();
		fp.setPadding(new Insets(5, 0, 5, 0));
		fp.setVgap(4);
		fp.setHgap(4);
		fp.setPrefWrapLength(250);
		
		File effectsFolder = new File(System.getProperty("user.home") + File.separator + "DungeonBoard" + File.separator + "Effects");
		File[] effectsFiles = effectsFolder.listFiles();	
		if (effectsFiles.length == 0) {
			Text noFiles = new Text("No effect files found!");
			noFiles.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			fp.getChildren().add(noFiles);
		} else {
			System.out.println("Number of effect files: " + effectsFiles.length);
			Button effectButtons[] = new Button[effectsFiles.length];
			for (int i = 0; i < effectsFiles.length; i++) {
				try {
					final String path = effectsFiles[i].getPath();
					effectButtons[i] = new Button();
					effectButtons[i].setText(effectsFiles[i].getName());
					System.out.println(effectButtons[i].getText());
					effectButtons[i].setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							try {
								FileInputStream fis = new FileInputStream(new File(path));
								BufferedInputStream bis = new BufferedInputStream(fis);
								Player player = new Player(bis);
								player.play();
								bis.close();
								fis.close();
							} catch (FileNotFoundException ex) {
								System.err.println("[ERROR] File: " + ex.getLocalizedMessage());
							} catch (JavaLayerException ex) {
								System.err.println("[ERROR] JLayer: " + ex.getLocalizedMessage());
							} catch (IOException ex) {
								System.err.println("[ERROR] File IO: " + ex.getLocalizedMessage());
							}
						}
					});
					
					fp.getChildren().add(effectButtons[i]);
				} catch (Exception ex) {
					System.err.println(ex.toString());
				}
			}
		}
		
		grid.add(fp, 0, 1);
		
		Scene scene = new Scene(grid, 300, 250);
		
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
