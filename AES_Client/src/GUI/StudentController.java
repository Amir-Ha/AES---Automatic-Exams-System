package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import Entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * StudentController Class - Student can see grades and solve exams
 */
public class StudentController implements Initializable,ChatIF  {
	
	public static Student student;
	private Connection connection;
	@FXML
	private Label namelbl;
	public void setStudentName() {
		String str="Welcome , "+StudentController.student.getpName();
		namelbl.setText(str);
	} 
	/**
	 * Student Buttons :
	 */
	
	public void SolveExamHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(StudentStartExamController.class.getResource("/GUI/startExamPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED); 

		primaryStage.show();
	}
	
	public void GradesHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(StudentStartExamController.class.getResource("/GUI/studentGradesPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED); 

		primaryStage.show();
	}
	public void LogOutHandle(ActionEvent event) throws Exception {
		connection.SendRequest(student.getUserName(), 4);
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(LoginController.class.getResource("/GUI/loginPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);

		primaryStage.show();
	}
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setStudentName();	
		connection=new Connection(this);
		connection.setChatIF();
	}

}
