package GUI;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import Entity.Principal;
import Entity.Student;
import Entity.Teacher;
import Entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * LoginController Class - User can log in and continue to their Specific UI
 */
public class LoginController implements Initializable,ChatIF {

	@FXML
	private TextField usernametxt;
	@FXML
	private TextField passwordtxt;
	@FXML
	private Label userlbl;
	@FXML
	private Label passlbl;
	
	private User user;
	private Connection connection;
	private ActionEvent event;
	

	/**
	 * This method handles the login button, checks if username/password boxes are filled, if yes, send request to server. 
	 */
	public void loginHandle(ActionEvent event) throws Exception {
		if(usernametxt.getText().toString().equals("")) {
			usernametxt.getStyleClass().add("error");
			userlbl.setText("Enter a Username !");
		}
		else if(passwordtxt.getText().toString().equals("")) {
			usernametxt.getStyleClass().clear();
			usernametxt.getStyleClass().addAll("text-field", "text-input");
			userlbl.setText("");
			
			passwordtxt.getStyleClass().add("error");
			passlbl.setText("Enter a password !");
		}
		else {
			connection.SendRequest(usernametxt.getText().toString(),1);
			this.event=event; 
		}
	}
	
	/**check if username and password exist,
	*also password matches the username
	*/
	public String Login() {
			if(user.getStatus().equals("offline")) {
				if(passwordtxt.getText().toString().equals(this.user.getUserPass())) {
					//which type user is:
					if(this.user instanceof Teacher)
						return "Teacher Login";
					else if(this.user instanceof Student)
						return "Student Login";
					else 
						return "Principal Login";
				}
				else
					return "WRONG Password";
			}
			else return "User Online";
	}
	/**
	 * this method starts the next frame
	 * @param fxml this string have the fxml file name
	 */
	private void StartFrame(AnchorPane root) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
			primaryStage.setScene(scene);		
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);

			primaryStage.show();

	}
	/**
	 * this method create the user by his type
	 * @param userString have the user details
	 */
	public User CreateUser(String[] userString) {
		User user=null;
		switch(userString[2]) {
		case "Teacher":
			user=new Teacher(userString[0],userString[1],userString[3],userString[4],userString[5]);
			break;
		case "Student":
			user=new Student(userString[0],userString[1],userString[3],userString[4],userString[5]);
			break;
		case "Principal":
			user=new Principal(userString[0],userString[1],userString[3],userString[4],userString[5]);
			break;
		}
		return user; 
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		connection=new Connection(this);
		connection.setChatIF();
	}
	/**
	 * get user from server and check if everything is correct and login
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		if(message!=null){
			user=CreateUser((String[])message); 
			String status=Login();
			switch(status) {
			case "Teacher Login":
				connection.SendRequest(user.getUserName(), 3);
				Teacher t=(Teacher)user;
				TeacherController.teacher=t;
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
								try {
									AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherController.class.getResource("/GUI/teacherPage.fxml"));
									StartFrame(root);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						  }
						});
				break;
			case "Student Login":
				connection.SendRequest(user.getUserName(), 3);
				Student s=(Student)user;
				StudentController.student=s;
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							try {
								AnchorPane root = (AnchorPane) FXMLLoader.load(StudentController.class.getResource("/GUI/studentPage.fxml"));
								StartFrame(root);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  }
					});
				break;
			case "Principal Login":
				connection.SendRequest(user.getUserName(), 3);
				Principal p=(Principal)user;
				PrincipalController.principal=p;
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							try {
								AnchorPane root = (AnchorPane) FXMLLoader.load(PrincipalController.class.getResource("/GUI/principalPage.fxml"));
								StartFrame(root);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  }
					});
				break;
			case "WRONG Password":
				// change UI inside UI thread only. To be able to modify UI from another thread
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  passwordtxt.getStyleClass().add("error");
							passlbl.setText("Wrong password !");
	
					  }
					});
				break;
			case "User Online":
				// change UI inside UI thread only. To be able to modify UI from another thread
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							usernametxt.getStyleClass().add("error");
							userlbl.setText("User already Online !");
	
					  }
					});
				break;
			}
		}
		else//User dosent exist !
		{
			// change UI inside UI thread only. To be able to modify UI from another thread
			Platform.runLater(new Runnable() {
				  @Override
				  public void run() {
					  usernametxt.getStyleClass().add("error");
						passwordtxt.getStyleClass().add("error");
						userlbl.setText("User dosent exist !");
				  }
				});
		}
	}

}
