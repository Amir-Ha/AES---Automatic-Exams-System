package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * GradesController Class - Teacher sees all submitted exam grades, can confirm them and add notes
 */
public class GradesController implements ChatIF,Initializable {
	private Connection conn=new Connection(this);
	@FXML
	private ListView<Grades> gradeslst=new ListView<Grades>();
	@FXML 
	private ListView<Table> cheatslst=new ListView<Table>(),statlst=new ListView<Table>();
	private int index=0;
	
	/**
	 * send request to server to get student grades and cheaters and statistic of exams
	 */
	@Override 
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub 
		conn.setChatIF();
		conn.SendRequest(TeacherController.teacher.getpName(), 26);
		conn.SendRequest(TeacherController.teacher.getpName(), 27);
		conn.SendRequest(TeacherController.teacher.getpName(), 28);

	}
	/**
	 * back button handle to back to previous frame 
	 */
	public void PreviousPageHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(PrincipalController.class.getResource("/GUI/teacherPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);		 
		primaryStage.initStyle(StageStyle.UNDECORATED); 

		primaryStage.show();
	}
	/**
	 * get message from server and handle it
	 * examsoltion - get grades table and display for user
	 * wronganswers - get cheaters table and display it
	 * statistics- get statistics table..
	 */
	@Override
	public void display(Object message) {
		/**
		 * message[0] - TableName
		 * message[1] - String ArrayList of Table  
		 */
		Object[] obj = (Object[])message;
		/**
		 * insert table content into ListView that matches TableName
		 */
		switch((String)obj[0]) { 
		case "examsolution":
			ArrayList<String> arr = (ArrayList<String>)obj[1];
			gradeslst.getItems().clear();
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i = 0 ; i < arr.size() ; i+=7)
					{
						gradeslst.getItems().add(new Grades(arr.get(i),arr.get( i+1 ),arr.get( i+2 ),arr.get( i+3 ),arr.get( i+4 ),
								arr.get( i+5 ),arr.get( i+6 )));
					}
				}
			});
			
			break;
		case "wronganswers":
			arr = (ArrayList<String>)obj[1];
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i = 0 ; i < arr.size() ; i+=4)
					{
						cheatslst.getItems().add(new Table("Exam ID : " + arr.get(i),"Student ID : " + arr.get( i+1 ) ,"Question ID : " +
								arr.get( i+2 ) , "Student Answer : " + arr.get( i+3 )));
					}
				}
			});
			
			break;
		case "statistics":
			arr = (ArrayList<String>)obj[1];
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i = 0 ; i < arr.size() ; i+=7)
					{
						statlst.getItems().add( new Table("Exam ID : " + arr.get(i) ,"Execution Date : " + arr.get( i+1 ) ,"Duration : " +
								arr.get( i+2 ), "Students Number : " + arr.get( i+3 ),  "Students Completed Exam Number : " + arr.get( i+4 ),
								"Average : " + arr.get( i+5 ), "Median : " + arr.get( i+6 )));
					}
				}
			});
			
			break;
		}
	} 
	/**
	 * class for listView cell to add labels .
	 *
	 */
	public  class Table extends HBox {
		Label[] column;
		Table(String...text){
			super();
			column=new Label[text.length];
			for(int i=0;i<text.length;i++) 
			{
				column[i]=new Label();
				column[i].setText(text[i]);
				column[i].setMaxWidth(Double.MAX_VALUE);
	            HBox.setHgrow(column[i], Priority.ALWAYS);
	            this.getChildren().add(column[i]);
			}
		}
	}
	/**
	 * class for listView cell to add labels and text Filed and area and button to confirm and add note and update grade
	 *
	 */
	public  class Grades extends HBox {
		Label idStudent = new Label();
        Label idExam = new Label();
        Label time = new Label();
        Label answers = new Label();
        Label confirm = new Label();
        TextField grade=new TextField();
        TextArea notes=new TextArea();
        Button conf=new Button("Confirm");
        Grades(String...text) {
            super();
            idStudent.setText("Student ID : "+text[0]+"\t");
            idStudent.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(idStudent, Priority.ALWAYS);

            idExam.setText("Exam ID : "+text[1]+"\t");
            idExam.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(idExam, Priority.ALWAYS);
            
            time.setText("Finish Time : "+text[2]+"\t");
            time.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(time, Priority.ALWAYS);
            
            answers.setText("Answers : "+text[3]+"\t");
            answers.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(answers, Priority.ALWAYS);
            
            grade.setText(text[4]);
            grade.setMaxWidth(50);
            HBox.setMargin(grade,new Insets(0,10,0,0));
            
            notes.setPromptText("Teacher Notes");
            notes.setMaxWidth(150);
            notes.setMaxHeight(50);
            HBox.setMargin(notes,new Insets(0,10,0,0));
            
            if(text[5].equals("Confirmed"))
            {
            	conf.setDisable(true);
            	grade.setDisable(true);
            	notes.setDisable(true);
            }
            conf.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(!grade.getText().equals(""))
					{
						if(!notes.getText().equals("")) 
						{
							try {
								notes.getStyleClass().clear();
								notes.getStyleClass().addAll("text-area", "text-input");
								grade.getStyleClass().clear();
								grade.getStyleClass().addAll("text-field", "text-input");

								double newGrade=Double.parseDouble(grade.getText());
								conn.SendRequest(new String[] {text[0],text[1],newGrade+"",notes.getText()} ,25);
								conf.setDisable(true);
								notes.setDisable(true);
								grade.setDisable(true);
							}catch(NumberFormatException e) {
								grade.getStyleClass().add("error");
							}
						}
						else
							notes.getStyleClass().add("error");
					}
					else
					{
						grade.getStyleClass().add("error");
					}
						
				}
			});
            this.getChildren().addAll(idStudent,idExam,time,answers);
            this.getChildren().addAll(grade,notes,conf);
        }
	}
}
