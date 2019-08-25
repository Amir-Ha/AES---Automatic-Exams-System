package GUI;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import Client.ChatIF;
import Client.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * ConnectionController Class - Gives the Client the possibility to connect to the Server by entering Host IP
 */
public class ConnectionController implements ChatIF{
	

	@FXML
	private Label hostlbl;
	@FXML
	private TextField hosttxt;
	
	/**Start the Connection frame*/
	public void start(Stage primaryStage) throws Exception {		
		AnchorPane root = (AnchorPane) FXMLLoader.load(ConnectionController.class.getResource("/GUI/connectionPage.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		primaryStage.setTitle("AES Connection");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();		
	}
	
	/** Take the Host IP from the TextField, Connect, close current frame, open LogIn frame
	* Show error message if TextField is empty
	* Show error message if could not connect
	*/
	public void connectHandle(ActionEvent event) throws Exception{
		//hosttxt.setText("localhost");
		if(hosttxt.getText().toString().equals(""))
		{
			hosttxt.getStyleClass().add("error");
			hostlbl.setText("Please enter Host IP");
		}
		else {
			Connection.host=hosttxt.getText().toString();
			Connection connection=new Connection(this);
			if(connection.Connect()) {
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader.load(LoginController.class.getResource("/GUI/loginPage.fxml"));
							
				Scene scene = new Scene(root);			
				scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
				
				primaryStage.setScene(scene);	

				primaryStage.setResizable(false);

				primaryStage.show();
			}
			else {
				hosttxt.getStyleClass().add("error");
				hostlbl.setText("Connection Error - Enter a Valid IP");
			}
		}
		
	}
	/**
	 * Show the status of the connection - Success or Error
	 */
	@Override
	public void display(Object message) {
		hostlbl.setText((String) message);
	}
}
