package GUI;


import java.io.IOException;

import Server.EchoServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ServerController {

	@FXML
	private Label statuslbl;
	@FXML
	private Button stopbtn,startserverbtn;
	final public static int DEFAULT_PORT = 7777;
	private EchoServer sv;
	
	/**
	 * Start the Server frame, show server page which was created in scene builder 
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/serverPage.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/GUI/server.css").toExternalForm());
		primaryStage.setTitle("AES Server");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		}
	/**
	   * This method is responsible for the creation of the server instance.
	   *The port number which is listened to is 7777.
	   */
	public void StartListeningHandle(ActionEvent event) throws Exception{
	    sv = new EchoServer(DEFAULT_PORT);
	    try 
	    {
	    	setStatus("Server listening for connections on port "+DEFAULT_PORT);
			stopbtn.setDisable(false);
			startserverbtn.setDisable(true);
	    	sv.listen(); //Start listening for connections
	      
	    } 
	    catch (Exception ex) 
	    {
	    	setStatus("ERROR - Could not listen for clients!");
	    }
	}
	/**
	   * This method is responsible for stopping the server instance.
	   */
	public void StopListeningHandle(ActionEvent event) throws Exception{
		
		try {
			setStatus("Server has stopped");
			sv.close();
			stopbtn.setDisable(true);
			startserverbtn.setDisable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
	    	setStatus("ERROR - Could not Stop Listening!");
		}
		
	}
/** 
 * this method updates the status label.
 * @param message (string), this message is printed on the status label.
 */
	public  void setStatus(String message) {
		statuslbl.setText(message);
	}
		
}
