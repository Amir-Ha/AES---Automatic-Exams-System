package GUI;

import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.ChatIF;
import Client.Connection;
import Entity.Answer;
import Entity.Exam;
import Entity.ExamSoultion;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * ComputerizedExamController Class - Class for a Computerized Exam, Student can solve the exam and submit it. The exam and the duration are presented on the screen
 */
public class ComputerizedExamController implements ChatIF,Initializable{ 
	
	public static Exam exam;
	@FXML
	private Label timelbl,examlbl,notelbl;
	@FXML
	private ListView<Question> examlst=new ListView<Question>();
	@FXML
	private Button submitbtn;
	private int second=exam.getDuration().getHours()*3600+exam.getDuration().getMinutes()*60+exam.getDuration().getSeconds();
	private Connection conn;
	ExecutorService application;
	private boolean FinishExam=true;
	public static ExamSoultion sol;
	
	/**
	 * connect with server ,add question of exam and start thread to count the time of exam
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn=new Connection(this);
		conn.setChatIF();
		sol=new ExamSoultion(exam.getExamID());
		for(int i=0;i<exam.getQuestions().size();i++) {
			String[] ans=new String[] {exam.getQuestions().get(i).getAns1(),exam.getQuestions().get(i).getAns2(),exam.getQuestions().get(i).getAns3()
					,exam.getQuestions().get(i).getAns4()};
			examlst.getItems().add(new Question(exam.getQuestions().get(i).getText(), ans,i));	
		}
		examlbl.setText("Exam : "+exam.getExamID()+"\nDuration : "+exam.getDuration());
		if(!exam.getNoteForStudent().equals(""))
			notelbl.setText("Note for student : "+exam.getNoteForStudent());
		else
			notelbl.setText("");
		TimerThread t=new TimerThread();
		application =Executors.newCachedThreadPool();
		application.execute(t);
		application.shutdown();
	}
	/**
	 * submit button method to submit student answers and send it to server.
	 */
	public void SubmitHandle(ActionEvent event) throws Exception {
		EndExam();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(StudentController.class.getResource("/GUI/studentPage.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		primaryStage.setScene(scene);	
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.setResizable(false);

		primaryStage.show();
		
	}
	/**
	 * this method to save all student answers and calculate the points of exam  and if there a wrong answer send to server to check if copy cheats;
	 */
	public void EndExam() {
		String answers="";
		double points=0;
		for (Map.Entry<String,Answer> entry: sol.getExamAnswers().entrySet())
		{
			String str="Question : "+entry.getValue().getQuestionText()+"\tStudent answer : "+entry.getValue().getAnswer()+
					((entry.getValue().isItCorrect())? "\tCorrect":"\tWrong")+"\n";
			answers+=str;
			if(!entry.getValue().isItCorrect()) {
				String[] wrongAnswer=new String[] {exam.getExamID(),StudentController.student.getpID(),entry.getKey(),str};
				conn.SendRequest(wrongAnswer, 22);
			}
			else
				points+=entry.getValue().getPoint();
		}
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		Object[] message=new Object[2];
		message[0]=FinishExam;
		////////
		String[] examSol=new String[8];
		examSol[0]=StudentController.student.getpID();
		examSol[1]=exam.getExamID();
		examSol[2]=dateFormat.format(date).toString();
		examSol[3]=answers;
		examSol[4]=points+"";
		examSol[5]="NotConfirmed";
		examSol[6]="";
		examSol[7]=exam.getDuration().toString();
		
		////////////
		message[1]=examSol;
		conn.SendRequest(message, 21);
	}
	/**
	 * to update the time of exam
	 */
	private void SetTime() {
		  timelbl.setText(second/3600+":"+((second/60)%60)+":"+(second%60));
	}
	/**
	 * this method to get message from server and handle it.
	 * ExamLocked - to lock exam (teacher locked exam ), Set FinishExam - false, Make sure Student Can't Answer
	 * NewDuration - to add new time of exam (Principal confirm new Duration) 
	 */
	@Override
	public void display(Object message) {
		Object[] obj = (Object[])message;
		switch((String)obj[0]) {
		case "ExamLocked":
			FinishExam=false;
			examlst.setDisable(true);
			break;
		case "NewDuration":
			Time t=Time.valueOf((String)obj[1]);
			second+=t.getHours()*3600+t.getMinutes()*60+t.getSeconds();
			break;
		}
		
	}
	/**
	 * 
	 * This class to create listView cell with Question type that have label and combo box for question text and answers  
	 *and if user select value of combobox his answer will save in global array. 
	 */
	public static class Question extends HBox {
		int index;
        Label label = new Label();
        ComboBox<String> cmp=new ComboBox<>();
        
        Question(String text, String[] answers ,int index) {
             super();
             
             this.index=index;
             label.setText(text);
             label.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(label, Priority.ALWAYS);
             for(int i=0;i<4;i++)
            	 cmp.getItems().add(answers[i]);
             
             cmp.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					int answerNumber=cmp.getSelectionModel().getSelectedIndex()+1;
					Answer a=new Answer(exam.getQuestions().get(index).getQuestionNum()+" : "+exam.getQuestions().get(index).getText(),cmp.getValue(), 
							((answerNumber==Integer.parseInt(exam.getQuestions().get(index).getCorrectAnswer())? true:false)),
							exam.getQuestions().get(index).getPoint());
					sol.AddAnswer(exam.getQuestions().get(index).getId(), a);
				}
			});
             this.getChildren().addAll(label, cmp);
        }
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
			FinishExam=false;
			examlst.setDisable(true);
			}
		
	}
}
