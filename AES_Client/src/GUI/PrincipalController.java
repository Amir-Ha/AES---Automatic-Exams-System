package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import Client.ChatIF;
import Client.Connection;
import Entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * PrincipalController Class - Principal Menu
 */
public class PrincipalController implements Initializable, ChatIF {
	public static Principal principal;
	private Connection connection;


	@FXML
	private Label namelbl;
	/**
	 * set welcome name in UI
	 */
	public void setPrincipalName() {
		String str="Welcome , "+PrincipalController.principal.getpName();
		namelbl.setText(str);
	}
	/**
	 * Handle back button to back to previous frame
	 */
	public void DetailsHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(SystemDetailsController.class.getResource("/GUI/systemdetailsPage.fxml"));
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * open new page for confirm new duration page
	 */
	public void DurationHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(DurationController.class.getResource("/GUI/durationchangerequestsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * open reports page
	 */
	public void ReportsHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(DurationController.class.getResource("/GUI/reportsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.setResizable(false);

		primaryStage.show();
	}
	/**
	 * to log out and send message to server that user offline
	 */
	public void LogOutHandle(ActionEvent event) throws Exception {
		connection.SendRequest(principal.getUserName(), 4);
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(LoginController.class.getResource("/GUI/loginPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);		
		primaryStage.setResizable(false);

		primaryStage.show();
	}
	/**
	 * set connection with server
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setPrincipalName();
		connection=new Connection(this);
		connection.setChatIF();
	}

	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
