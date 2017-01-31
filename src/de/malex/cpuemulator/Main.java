/**
 * Copyright 2016-2017 Alexandr Mitiaev
 * 
 * This file is part of CPU Emulator.
 * 
 * CPU Emulator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CPU Emulator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CPU Emulator. If not, see <http://www.gnu.org/licenses/>.
 */
package de.malex.cpuemulator;
	
import de.malex.cpuemulator.constants.Messages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
	
/**
 * Application entry point class
 */
public class Main extends Application {
	
	/**
	 * Starting of the main-form
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
			Scene scene = new Scene(root, 900, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle(Messages.APP_TITLE);
		} catch(Exception e) {
			showError(Messages.ALERT_START_ERROR,
					   e.getMessage());
		}
	}
	
	/**
	 * Display dialog box with a given message
	 * 
	 * @param alertType Type of the dialog
	 * @param title Title of the dialog
	 * @param header Header text
	 * @param content Content text
	 * 
	 * @see AlertType
	 */
	public static void showAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	/**
	 * Display dialog box with a error
	 * 
	 * @param header Header text
	 * @param content Content text
	 */
	public static void showError(String header, String content) {
		showAlert(AlertType.ERROR, Messages.ALERT_ERROR_TITLE, header, content);
	}
	
	/**
	 * Main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
