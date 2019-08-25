package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import Entity.Teacher;
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
 * TeacherController Class - Teacher Menu ( Exams, Grades, Questions, Execute Exam )
 */
public class TeacherController implements Initializable,ChatIF  {
	public static Teacher teacher;
	private Connection connection;
	@FXML
	private Label namelbl;
	
	/**
	 * Show on screen : Welcome 'Teacher Name '
	 */
	public void setTeacherName() {
		String str="Welcome , "+TeacherController.teacher.getpName();
		namelbl.setText(str);
	} 
	/**
	 * Open question page
	 */
	public void QuestionsHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
	
		AnchorPane root = (AnchorPane) FXMLLoader.load(QuestionController.class.getResource("/GUI/questionPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);


		primaryStage.show();
	}
	
	/**
	 * Open Teacher Exams Page
	 */
	public void ExamsHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherExamsController.class.getResource("/GUI/teacherExamsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);


		primaryStage.show();
	}
	
	/**
	 * Open Grades Pages of specific teacher exams
	 */
	public void GradeHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherExamsController.class.getResource("/GUI/gradesPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);


		primaryStage.show();
	}
	
	/**
	 * Open Execute Exam Page
	 */
	public void ExecuteExamHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherExecuteExamController.class.getResource("/GUI/TeacherExecuteExamPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);


		primaryStage.show();
	}
	
	/**
	 * Log teacher out and return to LogIn Page
	 */
	public void LogOutHandle(ActionEvent event) throws Exception {
		connection.SendRequest(teacher.getUserName(), 4);
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
	 * Connect to server using current client
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setTeacherName();	
		connection=new Connection(this);
		connection.setChatIF();
	}

	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}

}
