package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * SystemDetailsController Class - Principal sees all system details
 */
public class SystemDetailsController implements ChatIF, Initializable {
	private Connection conn=new Connection(this);
	@FXML
	private ListView<String> userlst, questionlst, subjectlst, courselst,
						     examlst, questioninexamlst, executeexamlst,
							 noteslst, examsolutionlst, wronganswerslst, statisticslst;
	/**
	 * back button
	 */
	public void PreviousPageHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(PrincipalController.class.getResource("/GUI/principalPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		primaryStage.setResizable(false);

		primaryStage.setScene(scene);		
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * send message to Server, ask for a table
	 */
	private void sendTableRequest(String tblname,int size) {
		Object[] mess=new Object[2]; 
		mess[0]=tblname;
		mess[1]=size;
		conn.SendRequest(mess, 9);
	}
	/**
	 * ASK FOR ALL TABLE FROM SERVER DATABASE (table name , column size)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conn.setChatIF();
		// TODO Auto-generated method stub
		sendTableRequest("user", 6);
		sendTableRequest("question", 8);
		sendTableRequest("subject", 2);
		sendTableRequest("course", 2);
		sendTableRequest("exam", 6);
		sendTableRequest("questioninexam", 4);
		sendTableRequest("executeexam", 7);
		sendTableRequest("notes", 4);
		sendTableRequest("examsolution", 7);
		sendTableRequest("wronganswers", 4);
		sendTableRequest("statistics", 7);
		
	}
	/**
	 * display table in UI
	 */
	@Override
	public void display(Object message) {
		/**
		 * message[0] - TableName
		 * message[1] - String ArrayList of Table
		 */
		Object[] obj = (Object[])message;
		ArrayList<String> arr = (ArrayList<String>)obj[1];
		/**
		 * insert table content into ListView that matches TableName
		 */
		switch((String)obj[0]) {
		case "user":
			for(int i = 0 ; i < arr.size() ; i+=6)
			{
				userlst.getItems().add("Name : " + arr.get(i) + "\t\tPassword : " + arr.get( i+1 ) + "\t\tType : " +
						arr.get( i+2 ) + "\t\tFull Name : " + arr.get( i+3 ) + "\t\tID : " + arr.get( i+4 ) + "\t\tStatus : " +
						arr.get( i+5 ));
			}
			break;
		case "question":
			for(int i = 0 ; i < arr.size() ; i+=8)
			{
				questionlst.getItems().add("Question ID : " + arr.get(i) + "\t\tText : " + arr.get( i+1 ) + "\t\tTeacher Name : " +
						arr.get( i+2 ) + "\t\tAnswer 1 : " + arr.get( i+3 ) + "\t\tAnswer 2 : " + arr.get( i+4 ) + "\t\tAnswer 3 : " +
						arr.get( i+5 ) + "\t\tAnswer 4 : " + arr.get( i+6 ) + "\t\tCorrect Answer : " + arr.get( i+7 ));
			}
			break;
		case "subject":
			for(int i = 0 ; i < arr.size() ; i+=2)
			{
				subjectlst.getItems().add("Subject ID : " + arr.get(i) + "\t\tSubject Name : " + arr.get( i+1 ));
			}
			break;
		case "course":
			for(int i = 0 ; i < arr.size() ; i+=2)
			{
				courselst.getItems().add("Course ID : " + arr.get(i) + "\t\tCourse Name : " + arr.get( i+1 ));
			}
			break;
		case "exam":
			for(int i = 0 ; i < arr.size() ; i+=6)
			{
				examlst.getItems().add("Exam ID : " + arr.get(i) + "\t\tDuration : " + arr.get( i+1 ) + "\t\tTeacher Name : " +
						arr.get( i+2 ) + "\t\tNotes For Students : " + arr.get( i+3 ) + "\t\tNote For Teacher : " + arr.get( i+4 ) +"\t\tBe Executed : "+arr.get(i+5));
			}
			break;
		case "questioninexam":
			for(int i = 0 ; i < arr.size() ; i+=4)
			{
				questioninexamlst.getItems().add("Question ID : " + arr.get(i) + "\t\tExam ID : " + arr.get( i+1 ) + "\t\tQuestion Number : " +
						arr.get( i+2 ) + "\t\tPoints : " + arr.get( i+3 ));
			}
			break;
		case "executeexam":
			for(int i = 0 ; i < arr.size() ; i+=7)
			{
				executeexamlst.getItems().add("Exam ID : " + arr.get(i) + "\t\tExam Code : " + arr.get( i+1 ) + "\t\tis Locked : " +
						arr.get( i+2 ) + "\t\tStart Time : " + arr.get( i+3 ) + "\t\tEnd Time : " + arr.get( i+4 ) + "\t\tExam Date : " + arr.get( i+5 ) +
						"\t\tTeacher ID : " + arr.get( i+6 ));
			}
			break;
		case "notes":
			for(int i = 0 ; i < arr.size() ; i+=4)
			{
				noteslst.getItems().add("Exam ID : " + arr.get(i) + "\t\tNote : " + arr.get( i+1 ) + "\t\tNew Duration : " +
						arr.get( i+2 ) + "\t\tConfirmed : " + arr.get( i+3 ));
			}
			break;
		case "examsolution":
			for(int i = 0 ; i < arr.size() ; i+=7)
			{
				examsolutionlst.getItems().add("Student ID : " + arr.get(i) + "\t\tExam ID : " + arr.get( i+1 ) + "\t\tActual Time : " +
						arr.get( i+2 ) + "\t\tExam Answer : " + arr.get( i+3 ) + "\t\tGrade : " + arr.get( i+4 ) + "\t\tGrade Confirmed : " +
						arr.get( i+5 ) + "\t\tExam Note : " + arr.get( i+6 ));
			}
			break;
		case "wronganswers":
			for(int i = 0 ; i < arr.size() ; i+=4)
			{
				wronganswerslst.getItems().add("Exam ID : " + arr.get(i) + "\t\tStudent ID : " + arr.get( i+1 ) + "\t\tQuestion ID : " +
						arr.get( i+2 ) + "\t\tStudent Answer : " + arr.get( i+3 ));
			}
			break;
		case "statistics":
			for(int i = 0 ; i < arr.size() ; i+=7)
			{
				statisticslst.getItems().add("Exam ID : " + arr.get(i) + "\t\tExecution Date : " + arr.get( i+1 ) + "\t\tDuration : " +
						arr.get( i+2 ) + "\t\tStudents Number : " + arr.get( i+3 ) + "\t\tStudents Completed Exam Number : " + arr.get( i+4 ) +
						"\t\tAverage : " + arr.get( i+5 ) + "\t\tMedian : " + arr.get( i+6 ));
			}
			break;
		}
	}


}
