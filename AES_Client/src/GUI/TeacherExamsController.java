package GUI;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import Client.ChatIF;
import Client.Connection;
import Entity.Exam;
import Entity.Question;
import Entity.QuestionInExam;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * TeacherExamsController Class - Deals with current teacher exams
 */
public class TeacherExamsController implements Initializable, ChatIF{
	
	@FXML
	private ListView<String> examsListView;
	@FXML
	private Label messagelbl,questionlbl,examlbl,pointslbl;
	@FXML
	private TextField examidtxt,questionnumtxt,pointstxt,durationtxt;
	@FXML
	private ComboBox<String> coursecmb, subjectcmb,questionidcmb;
	@FXML
	private Button checkidbtn,createbtn,addbtn,restbtn;
	@FXML
	private TextArea studentnotetxt,teachernotetxt;
	private int points=0;
	private Connection conn=new Connection(this);
	private ArrayList<Exam> exams=new ArrayList<Exam>();
	private ArrayList<Question> quesList;
	private ArrayList<QuestionInExam> examQuestions=new ArrayList<QuestionInExam>();
	@Override
	
	/**
	 * get details from server
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		conn.setChatIF();
		/*
		 * To get Existing Exams
		 */
		subjectcmb.getItems().clear();
		coursecmb.getItems().clear();
		conn.SendRequest("Get exams", 10);
		conn.SendRequest("Get all Subject",5);
		conn.SendRequest("Get all Courses", 11);
		conn.SendRequest(TeacherController.teacher.getpName(),2);
	}
	
	/**
	 * back button
	 */
	public void PreviousPageHandle(ActionEvent event) throws Exception {
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
	 * This method converts arraylist (String) to arraylist (Questions)
	 */
	public ArrayList<Question> CreateQuestions(Object mess){
		ArrayList<String> str=(ArrayList<String>)mess;
		if(str.size()!=0) {
			ArrayList<Question> quesList=new ArrayList<Question>();
			for(int i=0;i<str.size()/8;i++)
				quesList.add(new Question(str.get(i*8),str.get(i*8+1),str.get(i*8+2),str.get(i*8+3),str.get(i*8+4),str.get(i*8+5),str.get(i*8+6),str.get(i*8+7)));
			return quesList;
		}
		else
			return null;
	}
	/**
	 * rest button in add exam tab
	 */
	public void RestButtonHandle(ActionEvent event) throws Exception 
	{
		subjectcmb.setDisable(false);
		coursecmb.setDisable(false);
		examidtxt.setDisable(false);
		checkidbtn.setDisable(false);
		
		questionlbl.setDisable(true);
		examlbl.setDisable(true);
		pointslbl.setDisable(true);
		questionnumtxt.setDisable(true);
		pointstxt.setDisable(true);
		questionidcmb.setDisable(true);
		createbtn.setDisable(true);
		addbtn.setDisable(true);
		restbtn.setDisable(true);
		studentnotetxt.setDisable(true);
		teachernotetxt.setDisable(true);
		durationtxt.setDisable(true);
		
		durationtxt.setText("");
		
		subjectcmb.getSelectionModel().clearSelection();
		coursecmb.getSelectionModel().clearSelection();
		examidtxt.setText("");
		questionnumtxt.setText("");
		pointstxt.setText("");
		studentnotetxt.setText("");
		teachernotetxt.setText("");
		pointslbl.setText("0%");
		questionlbl.setText("");
		examlbl.setText("");
		points=0;
		messagelbl.setText("");
		
		questionidcmb.getItems().clear();
		for(Question q:quesList) 
			  questionidcmb.getItems().add(q.getId());	
		questionidcmb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
			{
				for(Question q:quesList)
					if(questionidcmb.getValue()!=null)
						if(questionidcmb.getValue().equals(q.getId()))
						{
							questionlbl.setText(q.toString());
							break;
						}		
			}
		});
	}
	/**
	 * check button : check if id exam doesn't exist(send request to server to check)
	 */
	public void CheckButtonHandle(ActionEvent event) throws Exception {
		messagelbl.setText("");
		String cmb1 = (String)subjectcmb.getValue();
		String cmb2 = (String)coursecmb.getValue();
		if(cmb1 != null && cmb2 != null && !examidtxt.getText().toString().equals(""))
		{
			if(examidtxt.getText().length() == 2) 
			{
				if(!examidtxt.getText().matches("[0-9]+"))
				{
					messagelbl.setText("Make Sure You Entered Only Numbers");
					examidtxt.getStyleClass().add("error");		
				} else
				{
					examidtxt.getStyleClass().clear();
					examidtxt.getStyleClass().addAll("text-field", "text-input");
					String str = subjectcmb.getValue().toString().replaceAll("[^0-9]", "")+
							coursecmb.getValue().toString().replaceAll("[^0-9]", "") + examidtxt.getText().toString();					
					conn.SendRequest(str, 12);	
				}
			}
			else 
			{
				messagelbl.setText("Make Sure You Entered Only 2 digits");
				examidtxt.getStyleClass().add("error");	
			}
		}
		else 
		{
			messagelbl.setText("Subject Field and Exam ID  and Course Field Are Empty");
			examidtxt.getStyleClass().add("error");
		}
	}
	/**
	 * add question to exam button
	 */
	public void AddQuestionButtonHandle(ActionEvent event) throws Exception 
	{
		if(questionidcmb.getItems()!=null)
		{
			if(!questionnumtxt.getText().equals("") ) 
			{
				messagelbl.setText("");
				questionnumtxt.getStyleClass().clear();
				questionnumtxt.getStyleClass().addAll("text-field", "text-input");
				if(!pointstxt.getText().equals(""))
				{
					try 
					{
						messagelbl.setText("");
						pointstxt.getStyleClass().clear();
						pointstxt.getStyleClass().addAll("text-field", "text-input");
						double point=Double.parseDouble(pointstxt.getText().toString());
						for(Question q:quesList)
							if(questionidcmb.getValue()!=null)
								if(questionidcmb.getValue().equals(q.getId()))
								{
									questionidcmb.getItems().remove(questionidcmb.getSelectionModel().getSelectedIndex());
									questionidcmb.getSelectionModel().clearSelection();
									examQuestions.add(new QuestionInExam(q.getId(), q.getText(), 
											q.getTeacherName(), q.getAns1(), q.getAns2(), q.getAns3(), q.getAns4(), 
											q.getCorrectAnswer(), questionnumtxt.getText().toString(), point));
									examlbl.setText(examlbl.getText()+"\n "+examQuestions.get(examQuestions.size()-1).getQuestionNum() +
											" : "+examQuestions.get(examQuestions.size()-1).getText());
									questionnumtxt.setText("");
									questionlbl.setText("");
									points+=point;
									pointstxt.setText("");
									pointslbl.setText(points+"%");
									if(points>=100) {
										createbtn.setDisable(false);
									}
									break;
								}
					}catch(NumberFormatException e) {
						messagelbl.setText("Please Enter only a numbers in Point field");
						pointstxt.getStyleClass().add("error");
					}
				}
				else
				{
					messagelbl.setText("Please Enter a Point");
					pointstxt.getStyleClass().add("error");
				}
			}
			else 
			{
				messagelbl.setText("Please Enter a Question Number");
				questionnumtxt.getStyleClass().add("error");
			}
		}
		else
			messagelbl.setText("Please Enter Question ID");
	}
	/**
	 * create exam button send all exam details to server to add
	 */
	public void CreateButtonHandle(ActionEvent event) throws Exception 
	{
		if(!durationtxt.getText().equals("")) 
		{
			try {
				Time dur=Time.valueOf(durationtxt.getText().toString());
				messagelbl.setText("");
				durationtxt.getStyleClass().clear();
				durationtxt.getStyleClass().addAll("text-field", "text-input");
				Object[] mess=new Object[2];
				String[] arr=new String[5];
				arr[0]=subjectcmb.getValue().toString().replaceAll("[^0-9]", "")+
						coursecmb.getValue().toString().replaceAll("[^0-9]", "") + examidtxt.getText().toString();
				arr[1]=dur+"";
				arr[2]=TeacherController.teacher.getpName();
				arr[3]=studentnotetxt.getText().toString();
				arr[4]=teachernotetxt.getText().toString();
				String[][] questions=new String[examQuestions.size()][];
				for(int i=0;i<examQuestions.size();i++)
				{
					questions[i]=new String[3];
					questions[i][0]=examQuestions.get(i).getId();
					questions[i][1]=examQuestions.get(i).getQuestionNum();
					questions[i][2]=examQuestions.get(i).getPoint()+"";
				}
				mess[0]=arr;
				mess[1]=questions;
				conn.SendRequest(mess, 13);
			}catch(Exception e) {
				messagelbl.setText("Please Enter only number in Duration Like hh:mm:ss");
				durationtxt.getStyleClass().add("error");
			}
		}
		else
		{
			messagelbl.setText("Please Enter a Duration");
			durationtxt.getStyleClass().add("error");
		}
	}
	/**
	 * add exams to list view
	 */
	public void setListView() {
		if(examsListView.getItems()!= null)
			examsListView.getItems().clear();
		for(int i=0;i<exams.size();i++)
			examsListView.getItems().add(exams.get(i).toString());
	}
	/**
	 * case 1 - to get question to add to combo box and use it to add it to exam
	 * case 2 - to add subject to combo box
	 * case 3 - to get exam and questions of exam (the end of question array have '--')   
	 * case 4 - to add courses to combo box
	 * case 5 - to get if exam id exist or not
	 * case 6 - to get if exam added to server database
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		Object[] obj = (Object[])message;
		switch((int)obj[0]) {
		case 1:
			quesList = CreateQuestions(obj[1]);
			Platform.runLater(new Runnable() {
				  @Override
				  public void run() {
					  questionidcmb.getItems().clear();
				  }
			});		
			if(quesList!=null) {
						for(Question q:quesList) {
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									  questionidcmb.getItems().add(q.getId());
		
								  }
							});		
						}
						questionidcmb.valueProperty().addListener(new ChangeListener<String>() {
		
							@Override
							public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) 
							{
								for(Question q:quesList)
									if(questionidcmb.getValue()!=null)
										if(questionidcmb.getValue().equals(q.getId()))
										{
											questionlbl.setText(q.toString());
											break;
										}		
							}
						});
			}
			break;
		case 2: 
			ArrayList<String> subjects = (ArrayList<String>)obj[1];
			subjectcmb.getItems().addAll(subjects);
			break;
		case 3: 
			ArrayList<String> arr=(ArrayList<String>)obj[1];
			for(int i=0;i<arr.size();i++) 
			{
				exams.add(new Exam(arr.get(i), Time.valueOf(arr.get(i+1)), arr.get(i+2), arr.get(i+3), arr.get(i+4)));
				String name=arr.get(i+2);
				ArrayList<QuestionInExam> questions=new ArrayList<QuestionInExam>();
				i+=5;
				for(;!arr.get(i).equals("--");i+=9)
					questions.add(new QuestionInExam(arr.get(i), arr.get(i+3),name , arr.get(i+4), arr.get(i+5)
							, arr.get(i+6), arr.get(i+7), arr.get(i+8), arr.get(i+1), Double.parseDouble(arr.get(i+2))));
				exams.get(exams.size()-1).setQuestions(questions);
			}
			Platform.runLater(new Runnable() {
				  @Override
				  public void run() {
						setListView();
				  }
			});	
			break;
		case 4:
			ArrayList<String> courses = (ArrayList<String>)obj[1];
			coursecmb.getItems().addAll(courses);
			break;
		case 5: 
			boolean idCheck = (boolean)obj[1];
			if(idCheck == true) {				
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  	messagelbl.setText("Exam ID Already Exists");
							examidtxt.getStyleClass().add("error");
					  }
				});				
			}
			else {				
				subjectcmb.setDisable(true);
				coursecmb.setDisable(true);
				examidtxt.setDisable(true);
				checkidbtn.setDisable(true);
				
				questionlbl.setDisable(false);
				durationtxt.setDisable(false);
				examlbl.setDisable(false);
				pointslbl.setDisable(false);
				questionnumtxt.setDisable(false);
				pointstxt.setDisable(false);
				questionidcmb.setDisable(false);
				addbtn.setDisable(false);
				restbtn.setDisable(false);
				studentnotetxt.setDisable(false);
				teachernotetxt.setDisable(false);
				Platform.runLater(new Runnable() 
				{
					  @Override
					  public void run() 
					  {
						  examlbl.setText("Exam:  Id:"+subjectcmb.getValue().toString().replaceAll("[^0-9]", "")+
									coursecmb.getValue().toString().replaceAll("[^0-9]", "") + examidtxt.getText().toString());
					  }
				});		
				
			}
			break;
		case 6:
			boolean addCheck = (boolean)obj[1];
			if(addCheck == true) 
			{
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  
							messagelbl.setText("Successfully Added :)");
							conn.SendRequest("Get exams", 10);
					  }
				});
			}
			else {
				Platform.runLater(new Runnable() 
				{
					  @Override
					  public void run() 
					  {
						  messagelbl.setText("Can not Add :(");
					  }
				});	
			}
			break;
		}
			
	}


}
