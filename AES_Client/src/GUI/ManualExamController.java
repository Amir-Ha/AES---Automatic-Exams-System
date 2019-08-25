package GUI;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.ChatIF;
import Client.Connection;
import Entity.Exam;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * ManualExamController Class - Student can save the Exam Word File to their own pc, see the duration time and submit the word file
 */
public class ManualExamController implements ChatIF,Initializable {

	public static Exam exam;
	private int second=exam.getDuration().getHours()*3600+exam.getDuration().getMinutes()*60+exam.getDuration().getSeconds();
	private Connection conn;
	ExecutorService application;
	@FXML
	private Label timelbl;
	@FXML
	private Button submitbtn,backbtn;
	/**
	 * set Connection with server
	 * and start thread to count the time
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn=new Connection(this); 
		conn.setChatIF();
		
		TimerThread t=new TimerThread();
		application =Executors.newCachedThreadPool();
		application.execute(t);
		application.shutdown();
	}
	/**
	 *back button handle to back to previous frame 
	 * 
	 */
	public void BackHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(StudentController.class.getResource("/GUI/studentPage.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());

		primaryStage.setScene(scene);		
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * submit button handle to submit DOCX file from PC and send it to server and back to student menu frame
	 */
	public void SubmitHandle(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
	        //Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOCX files (*.docx)", "*.docx");
	        fileChooser.getExtensionFilters().add(extFilter);
	        Stage primaryStage = new Stage();
	        File file = fileChooser.showOpenDialog(primaryStage);
	        conn.SendRequest(new String[] {file.getAbsolutePath(),exam.getExamID()}, 23);
			
	         ((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	          primaryStage = new Stage();
	 		AnchorPane root = (AnchorPane) FXMLLoader.load(StudentController.class.getResource("/GUI/studentPage.fxml"));

	 		Scene scene = new Scene(root);
	 		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());

	 		primaryStage.setScene(scene);		
	 		primaryStage.initStyle(StageStyle.UNDECORATED);

	 		primaryStage.show();
		}catch(Exception e) {}
	}
	/**
	 * this method to get message from server and handle it.
	 * ExamLocked - to lock exam (teacher locked exam ), Set FinishExam - false, Make sure Student Can't Answer
	 * NewDuration - to add new time of exam (Principal confirm new Duration) 
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		Object[] obj = (Object[])message;
		switch((String)obj[0]) {
		case "ExamLocked":
			submitbtn.setDisable(true);
			backbtn.setDisable(false);
			break;
		case "NewDuration":
			Time t=Time.valueOf((String)obj[1]);
			second+=t.getHours()*3600+t.getMinutes()*60+t.getSeconds();
			break;
		}
	}
	/**
	 * to update the time of exam
	 */
	private void SetTime() {
		  timelbl.setText(second/3600+":"+((second/60)%60)+":"+(second%60));
	}
	/**
	 * Thread class to update the time
	 *
	 */
	private class TimerThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while((second--)!=1) {
				try {
					Thread.sleep(1000);
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							  SetTime();
						  }
					});		
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			submitbtn.setDisable(true);
			backbtn.setDisable(false);
			}
	}

}
