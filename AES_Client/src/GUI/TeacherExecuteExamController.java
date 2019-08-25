package GUI;

import java.net.URL;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import Entity.Exam;
import Entity.ExecuteExam;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * TeacherExecuteExamController Class - Teacher chooses which Exam to Execute and gives it an execution code
 */
public class TeacherExecuteExamController implements ChatIF ,Initializable {

	private Connection conn;
	
	@FXML
	private ListView<String> examListView;
	@FXML
	private Label messagelbl,examlbl,changeExamlbl,changemessagelbl,lockmesslbl;
	@FXML
	private ComboBox<String> examidcmb,chngExamIDCmb,lockExamCmb;
	@FXML
	private TextField codetxt,chngNewDurationtxt;
	
	private ArrayList<ExecuteExam> executeExams=new ArrayList<ExecuteExam>();
	private ArrayList<Exam> exams=new ArrayList<Exam>();
	@FXML
	private TextArea notetxt;
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
	 * back button
	 */
	public void BackHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherController.class.getResource("/GUI/teacherPage.fxml"));

		Scene scene = new Scene(root);			 
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());

		primaryStage.setScene(scene);		
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * set connection and get table details
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn=new Connection(this);
		conn.setChatIF();
		sendTableRequest("exam", 6);
		sendTableRequest("executeexam", 7);
	}
	/**
	 * to add details to listView
	 */
	public void SetListView() {
		if(examListView.getItems()!=null)
			examListView.getItems().clear();
		for(ExecuteExam e:executeExams) {
			examListView.getItems().add("Exam ID : " + e.getExamId() + "\t\tExam Code : " + e.getCode() + "\t\tis Locked : " +
					e.getIsLocked() + "\t\tStart Time : " + e.getStartTime() + "\t\tEnd Time : " + e.getEndTime() + "\t\tExam Date : " + e.getDate() +
					"\t\tTeacher ID : " +e.getTeacherId());
		}
	}
	/**
	 * to add details to comboBox
	 */
	public void SetCmbExamId() {
		if(examidcmb.getItems()!=null)
			examidcmb.getItems().clear();
		for(Exam e:exams)
			if(e.getBeExecuted().equals("NotYet"))
				examidcmb.getItems().add(e.getExamID());
		examidcmb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				for(Exam e:exams)
					if(examidcmb.getValue()!=null)
						if(examidcmb.getValue().equals(e.getExamID()))
						{
							examlbl.setText("Exam ID : "+e.getExamID()+"\tDuration : "+e.getDuration()+"\tTeacher Name : "+e.getTeacherName()+"\nTeacher Note : "+
									e.getNoteForTeacher());
							break;
						}
			}
			
		});
	}
	/**
	 * to add details to another comboBox
	 */
	public void SetChngId() {
		if(chngExamIDCmb.getItems()!=null)
			chngExamIDCmb.getItems().clear();
		for(ExecuteExam e:executeExams)
			if(!e.getIsLocked().equals("Locked"))
				chngExamIDCmb.getItems().add(e.getExamId());
		chngExamIDCmb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				for(Exam e:exams)
					if(chngExamIDCmb.getValue()!=null)
						if(chngExamIDCmb.getValue().equals(e.getExamID()))
						{
							changeExamlbl.setText("Exam ID : "+e.getExamID()+"\tDuration : "+e.getDuration()+"\nTeacher Name : "+e.getTeacherName());
							break;
						}
			}
			
		});
	}
	/**
	 * to add details to another comboBox
	 */
	public void SetlockId() {
		if(lockExamCmb.getItems()!=null)
			lockExamCmb.getItems().clear();
		for(ExecuteExam e:executeExams)
			if(e.getIsLocked().equals("Unlocked"))
				lockExamCmb.getItems().add(e.getExamId());
	}
	/**
	 * send request to server database to add new duration to table and wait to principal to confirm
	 */
	public void SendRequestButtonHandler(ActionEvent event) throws Exception{
		if(chngExamIDCmb.getValue()!=null) 
		{
			changemessagelbl.setText("");
			try {
				if(!chngNewDurationtxt.getText().equals("")) 
				{
					Time t=Time.valueOf(chngNewDurationtxt.getText().toString());
					changemessagelbl.setText("");
					chngNewDurationtxt.getStyleClass().clear();
					chngNewDurationtxt.getStyleClass().addAll("text-field", "text-input");
					if(!notetxt.getText().equals(""))
					{
						changemessagelbl.setText("");
						notetxt.getStyleClass().clear();
						notetxt.getStyleClass().addAll("text-area", "text-input");
						String[] mess=new String[] {chngExamIDCmb.getValue().toString(),notetxt.getText(),t.toString(),"NotConfirmed"};
						conn.SendRequest(mess, 16);
					}
					else
					{
						changemessagelbl.setText("please Enter a note");
						notetxt.getStyleClass().add("error");
					}
					
				}
				else 
				{
					changemessagelbl.setText("please Enter Duration (hh:mm:ss)");
					chngNewDurationtxt.getStyleClass().add("error");
				}
				
			}catch(Exception e) {
				changemessagelbl.setText("please Enter a correct Duration like hh:mm:ss");
				chngNewDurationtxt.getStyleClass().add("error");
			}
		}
		else {
			changemessagelbl.setText("please Enter Exam ID");
		}
	}
	/**
	 * send to server to lock executed exam
	 */
	public void LockButtonHandler(ActionEvent event) throws Exception{
		if(lockExamCmb.getValue()!=null)
		{
			lockmesslbl.setText("");
			for(ExecuteExam e:executeExams)
				if(e.getExamId().equals(lockExamCmb.getValue()))
						e.setIsLocked("Locked");
			conn.SendRequest(lockExamCmb.getValue().toString(), 17);
		}
		else {
			lockmesslbl.setText("please Enter Exam ID");
		}
	}
	/**
	 * to execute exam (set new code (check if doesn't exist))
	 */
	public void executeButtonHandler(ActionEvent event) throws Exception{
		if(examidcmb.getValue()!=null)
		{
			if(!codetxt.getText().equals(""))
			{
				if(codetxt.getText().length() == 4) {
					messagelbl.setText("");
					codetxt.getStyleClass().clear();
					codetxt.getStyleClass().addAll("text-field", "text-input");
					conn.SendRequest(codetxt.getText().toString(), 14);
				}
				else {
					messagelbl.setText("Code size must be 4 digits");
					codetxt.getStyleClass().add("error");
				}
			}
			else {
				messagelbl.setText("Code field is Empty");
				codetxt.getStyleClass().add("error");
			}
		}
		else {
			messagelbl.setText("please Enter Exam ID");
		}
			
	}
	/**
	 * executeexam - get table details and display UI
	 * exam -get table details and display UI
	 * Can Execute - check if can execute and display message in UI
	 * Insert - check if add execute exam
	 * Change - check if add note to confirm new duration
	 * Locked - check if exam locked 
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		/**
		 * message[0] - TableName
		 * message[1] - String ArrayList of Table
		 */
		Object[] obj = (Object[])message;
		switch((String)obj[0]) 
		{
		case "executeexam":
			ArrayList<String> arr = (ArrayList<String>)obj[1];
			for(int i = 0 ; i < arr.size() ; i+=7)
				executeExams.add(new ExecuteExam(arr.get(i),arr.get( i+1 ),arr.get( i+2 ),arr.get( i+3 ) ,arr.get( i+4 ) , arr.get( i+5 ),arr.get( i+6 )));
			SetListView();
			SetlockId();
			SetChngId();
			break;
		case "exam":
			arr = (ArrayList<String>)obj[1];
			for(int i = 0 ; i < arr.size() ; i+=6)
				exams.add(new Exam( arr.get(i),Time.valueOf(arr.get( i+1 )),arr.get( i+2 ),arr.get( i+3 ),arr.get( i+4 ),arr.get(i+5)));
			SetCmbExamId();
			break;
		case "Can Execute":
			if(!(boolean)obj[1]) {
				for(Exam e:exams)
					if(examidcmb.getValue().equals(e.getExamID()))
					{
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() {
								  messagelbl.setText("");
									codetxt.getStyleClass().clear();
									codetxt.getStyleClass().addAll("text-field", "text-input");
							  }
						});	
						DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
						Date date = new Date();
						
						String[] mess=new String[7];
						mess[0]=e.getExamID();
						mess[1]=codetxt.getText().toString();
						mess[2]="Unlocked";
						mess[3]=dateFormat.format(date).toString();
						mess[4]="";
						dateFormat=new SimpleDateFormat("yyyy/MM/dd");
						mess[5]=dateFormat.format(date).toString();
						mess[6]=TeacherController.teacher.getpID();
						executeExams.add(new ExecuteExam(mess[0], mess[1], mess[2], mess[3], mess[4], mess[5], mess[6]));
						e.setBeExecuted("Executed");
						conn.SendRequest(mess, 15);
					}
			}
			else {
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  messagelbl.setText("Exam Code exist!");
							codetxt.getStyleClass().add("error");
					  }
				});		
			}
			break;
		case "Insert":
			boolean addCheck = (boolean)obj[1];
			if(addCheck == true) 
			{
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							messagelbl.setText("Successfully Added :)");
							SetListView();
							SetCmbExamId();
							SetChngId();
							SetlockId();
					  }
				});		
			}
			break;
		case "Change":
			addCheck = (boolean)obj[1];
			if(addCheck == true) 
			{
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  changemessagelbl.setText("Successfully Sent :)");
					  }
				});		
			}
			break;
		case "Locked":
			addCheck = (boolean)obj[1];
			if(addCheck == true) 
			{
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  lockmesslbl.setText("Successfully Locked:)");
						  lockExamCmb.getItems().remove(lockExamCmb.getSelectionModel().getSelectedIndex());
						  lockExamCmb.getSelectionModel().clearSelection();
						  SetListView();
						  SetChngId();
					  }
				});		
			}
			break;
			
		}
	}

}
