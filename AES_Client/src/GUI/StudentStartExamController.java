package GUI;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import Entity.Exam;
import Entity.Question;
import Entity.QuestionInExam;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * StudentStartExamController Class - Start solving Exam Menu
 */
public class StudentStartExamController implements Initializable,ChatIF  {

	private Connection connection;
	@FXML
	private Button startbtn,manualbtn,compbtn;
	@FXML
	private TextField codetxt,studentidtxt;
	@FXML
	private Label messagelbl;
	public Exam exam;
	/**
	 * back button
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
	 * check if code correct and exist in server database if yes enable to put id and choose to start manual or computerize exam 
	 */
	public void StartHandle(ActionEvent event) throws Exception {
		if(!codetxt.getText().equals("")) 
		{
			messagelbl.setText("");
			codetxt.getStyleClass().clear();
			codetxt.getStyleClass().addAll("text-field", "text-input");
			String[] message=new String[] {codetxt.getText().toString(),StudentController.student.getpID()};
			connection.SendRequest(message, 18);
		}
		else
		{
			messagelbl.setText("Please Enter Exam Code");
			codetxt.getStyleClass().add("error");
		}
	}	
	/**
	 * manual button create DOCX file and save it in PC and go manual frame
	 */
	public void ManualHandle(ActionEvent event) throws Exception {
		if(!studentidtxt.getText().equals(""))
		{
			messagelbl.setText("");
			studentidtxt.getStyleClass().clear();
			studentidtxt.getStyleClass().addAll("text-field", "text-input");
			if(StudentController.student.getpID().equals(studentidtxt.getText().toString())) 
			{
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						XWPFDocument document = null;
						FileOutputStream fileOutputStream = null;
						try {
							document = new XWPFDocument();
							FileChooser fileChooser = new FileChooser();
				            //Set extension filter
				            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOCX files (*.docx)", "*.docx");
				            fileChooser.getExtensionFilters().add(extFilter);
				            Stage primaryStage = new Stage();
				             //Show save file dialog
				             File file =fileChooser.showSaveDialog(primaryStage);
							fileOutputStream = new FileOutputStream(file);
							XWPFParagraph paragraph = document.createParagraph();
					        XWPFRun run = paragraph.createRun();
					        run.setText("Exam Id : " + exam.getExamID());
					        run.addBreak();
					        run.setText("Teacher Name : "+exam.getTeacherName());
					        run.addBreak();
					        run.setText("Duration : "+exam.getDuration());
					        run.addBreak();
					        run.setText("Notes : "+exam.getNoteForStudent());
					        run.addBreak();
					        run.setText("Questions : ");
					        run.addBreak();
							for(QuestionInExam q:exam.getQuestions())
							{
								run.setText(q.getQuestionNum()+" - "+q.getText());
								run.addBreak();
								run.addBreak();
								run.setText("1 : "+q.getAns1());
								run.addBreak();
								run.setText("2 : "+q.getAns2());
								run.addBreak();
								run.setText("3 : "+q.getAns3());
								run.addBreak();
								run.setText("4 : "+q.getAns4());
								run.addBreak();
								run.addBreak();
							}
							document.write(fileOutputStream);
							document.close();
							connection.SendRequest(exam.getExamID(), 20);
							ManualExamController.exam=exam;

							((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
							primaryStage = new Stage();
							AnchorPane root = (AnchorPane) FXMLLoader.load(ComputerizedExamController.class.getResource("/GUI/manualExamPage.fxml"));
					
							Scene scene = new Scene(root);
							scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
							primaryStage.setScene(scene);	
							primaryStage.setResizable(false);
							primaryStage.initStyle(StageStyle.UNDECORATED);

							primaryStage.show();
							
							
						}catch(IOException e) {
							System.out.println(e.getMessage());
						}
				        
					}
				});
				
			}else
			{
				messagelbl.setText("Wrong ID,please Enter Your ID");
				studentidtxt.getStyleClass().add("error");
			}
		}
		else {
			messagelbl.setText("Please Enter Your ID");
			studentidtxt.getStyleClass().add("error");
			}
	}
	/**
	 * check if id correct if yes send exam to next page 
	 * and go to computerize exam page and start exam 
	 */
	public void CompHandle(ActionEvent event) throws Exception {
		if(!studentidtxt.getText().equals(""))
		{
			messagelbl.setText("");
			studentidtxt.getStyleClass().clear();
			studentidtxt.getStyleClass().addAll("text-field", "text-input");
			if(StudentController.student.getpID().equals(studentidtxt.getText().toString())) 
			{
				ComputerizedExamController.exam=this.exam;
				connection.SendRequest(exam.getExamID(), 20);
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader.load(ComputerizedExamController.class.getResource("/GUI/computerizedExamPage.fxml"));
		
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
				primaryStage.setScene(scene);	
				primaryStage.setResizable(false);
				primaryStage.initStyle(StageStyle.UNDECORATED);

				primaryStage.show();
			}
			else
			{
				messagelbl.setText("Wrong ID,please Enter Your ID");
				studentidtxt.getStyleClass().add("error");
			}
		}
		else {
			messagelbl.setText("Please Enter Your ID");
			studentidtxt.getStyleClass().add("error");
		}
	}
	/**
	 * case 1 - get message from server if code exist in server database if true set text id enable to get id and chose the way to solve exam
	 * case 2 - get exam details from database (questions and time notes ...)
	 * 
	 */
	@Override
	public void display(Object message) {
		Object[] obj = (Object[])message;
		switch((int)obj[0]) {
		case 1:
			boolean codeCheck = (boolean)obj[1];
			if(codeCheck == false) 
			{				
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  messagelbl.setText("Exam Code dosent Exists");
						  codetxt.getStyleClass().add("error");
					  }
				});
			}
			else {
				manualbtn.setDisable(false);
				compbtn.setDisable(false);
				codetxt.setDisable(true);
				startbtn.setDisable(true);
				studentidtxt.setDisable(false);
				connection.SendRequest(codetxt.getText(), 19);
			}
			break;
		case 2:
			ArrayList<String> arr=(ArrayList<String>)obj[1];
			exam=new Exam(arr.get(0), Time.valueOf(arr.get(1)), arr.get(2), arr.get(3), arr.get(4));
			String name=arr.get(2);
			ArrayList<QuestionInExam> questions=new ArrayList<QuestionInExam>();
			for(int i=5;!arr.get(i).equals("--");i+=9)
				questions.add(new QuestionInExam(arr.get(i), arr.get(i+3),name , arr.get(i+4), arr.get(i+5)
						, arr.get(i+6), arr.get(i+7), arr.get(i+8), arr.get(i+1), Double.parseDouble(arr.get(i+2))));
			exam.setQuestions(questions);
			break;
		}
	}
	/**
	 * set connection with server
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		connection=new Connection(this);
		connection.setChatIF();
	}

}
