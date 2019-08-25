package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import Client.ChatIF;
import Client.Connection;
import Entity.Question;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * QuestionController Class - Teacher can create, edit and see questions
 */
public class QuestionController implements Initializable ,ChatIF {
	
	@FXML
	private ListView<String> questionslst;
	@FXML
	private ComboBox<String> subjCombo = null; 
	@FXML
	private TextField questionIDtxt;
	@FXML
	private TextArea questionTexttxt, answer1txt, answer2txt, answer3txt, answer4txt;
	@FXML
	private TextArea editquestionTexttxt,editcorrectAnstxt,editanswer1txt, editanswer2txt, editanswer3txt, editanswer4txt;
	@FXML
	private Label messagelbl,editmessagelbl;
	@FXML
	private ComboBox<String> correctansCmb = null,editcorrectansCmb= null;
	@FXML
	private ComboBox<String> questionsidCombo = null;
	private Connection conn=new Connection(this); 

	private ArrayList<Question> quesList;
	
	/**
	 * start connection with server
	 * and setup the combo box
	 * and send request to server to get some details 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn.setChatIF();
		conn.SendRequest(TeacherController.teacher.getpName(), 2);
		subjCombo.getItems().clear();	
		correctansCmb.getItems().clear();
		correctansCmb.getItems().add("1");
		correctansCmb.getItems().add("2");
		correctansCmb.getItems().add("3");
		correctansCmb.getItems().add("4");
		editcorrectansCmb.getItems().clear();
		editcorrectansCmb.getItems().add("1");
		editcorrectansCmb.getItems().add("2");
		editcorrectansCmb.getItems().add("3");
		editcorrectansCmb.getItems().add("4");
		conn.SendRequest("Get Subjects", 5);
	}
	/**
	 *back button handle to back to previous frame 
	 * 
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
	 * This method checks that every input is correct, if yes, it sends request to server to save.
	 */
	public void saveButtonHandler(ActionEvent event) throws Exception{
		editmessagelbl.setText("");
		if(questionsidCombo.getValue()!=null) {
			if( !editquestionTexttxt.getText().equals("") ) {
				editquestionTexttxt.getStyleClass().clear();
				editquestionTexttxt.getStyleClass().addAll("text-area", "text-input");
				if( !editanswer1txt.getText().equals("") ) {
					editanswer1txt.getStyleClass().clear();
					editanswer1txt.getStyleClass().addAll("text-area", "text-input");
					if( !editanswer2txt.getText().equals("") ) {
						editanswer2txt.getStyleClass().clear();
						editanswer2txt.getStyleClass().addAll("text-area", "text-input");
						if( !editanswer3txt.getText().equals("") ) {
							editanswer3txt.getStyleClass().clear();
							editanswer3txt.getStyleClass().addAll("text-area", "text-input");
							if( !editanswer4txt.getText().equals("") ) {
								editanswer4txt.getStyleClass().clear();
								editanswer4txt.getStyleClass().addAll("text-area", "text-input");
								if(editcorrectansCmb.getValue() != null) {
									String[] questionDet = new String[7];
									questionDet[0] = questionsidCombo.getValue();
									questionDet[1] = editquestionTexttxt.getText().toString();
									questionDet[2] = editanswer1txt.getText().toString();
									questionDet[3] = editanswer2txt.getText().toString();
									questionDet[4] = editanswer3txt.getText().toString();
									questionDet[5] = editanswer4txt.getText().toString();
									questionDet[6] = editcorrectansCmb.getValue();
									conn.SendRequest(questionDet, 8);
								} else editmessagelbl.setText("Enter Correct Answer");
							}else { editmessagelbl.setText("Fill the required fields");
								editanswer4txt.getStyleClass().add("error"); }
						} else { editmessagelbl.setText("Fill the required fields");
						editanswer3txt.getStyleClass().add("error"); }
					}else { editmessagelbl.setText("Fill the required fields");
					editanswer2txt.getStyleClass().add("error"); }
				} else { editmessagelbl.setText("Fill the required fields");
				editanswer1txt.getStyleClass().add("error"); }
			} else { editmessagelbl.setText("Fill the required fields");
			editquestionTexttxt.getStyleClass().add("error"); }
		}else {editmessagelbl.setText("Choose question ID"); }
	}

	
	/**
	 * This method checks that every input is correct, if yes, it sends request to server to add.
	 */
	public void addButtonHandler(ActionEvent event) throws Exception{
		messagelbl.setText("");
		String cmb = (String)subjCombo.getValue();
		if(cmb != null && !questionIDtxt.getText().toString().equals("")) {
			if(questionIDtxt.getText().length() == 3) {
				if(!questionIDtxt.getText().matches("[0-9]+")){
					messagelbl.setText("Make Sure You Entered Only Numbers");
					questionIDtxt.getStyleClass().add("error");		
				} else{
					questionIDtxt.getStyleClass().clear();
					questionIDtxt.getStyleClass().addAll("text-field", "text-input");
					if( !questionTexttxt.getText().equals("") ) {
						questionTexttxt.getStyleClass().clear();
						questionTexttxt.getStyleClass().addAll("text-area", "text-input");
						if( !answer1txt.getText().equals("") ) {
							answer1txt.getStyleClass().clear();
							answer1txt.getStyleClass().addAll("text-area", "text-input");
							if( !answer2txt.getText().equals("") ) {
								answer2txt.getStyleClass().clear();
								answer2txt.getStyleClass().addAll("text-area", "text-input");
								if( !answer3txt.getText().equals("") ) {
									answer3txt.getStyleClass().clear();
									answer3txt.getStyleClass().addAll("text-area", "text-input");
									if( !answer4txt.getText().equals("") ) {
										answer4txt.getStyleClass().clear();
										answer4txt.getStyleClass().addAll("text-area", "text-input");
										if(correctansCmb.getValue() != null) {
											String str = subjCombo.getValue().toString().replaceAll("[^0-9]", "") + questionIDtxt.getText().toString();					
											conn.SendRequest(str, 6);	
										} else messagelbl.setText("Enter Correct Answer");
									}else { messagelbl.setText("Fill the required fields");
										answer4txt.getStyleClass().add("error"); }
								} else { messagelbl.setText("Fill the required fields");
									answer3txt.getStyleClass().add("error"); }
							}else { messagelbl.setText("Fill the required fields");
								answer2txt.getStyleClass().add("error"); }
						} else { messagelbl.setText("Fill the required fields");
							answer1txt.getStyleClass().add("error"); }
					} else { messagelbl.setText("Fill the required fields");
						questionTexttxt.getStyleClass().add("error"); }
				}
			} else {
				messagelbl.setText("Make Sure You Entered Only 3 digits");
				questionIDtxt.getStyleClass().add("error");	
			}
		} else {
			messagelbl.setText("Subject Field and QuestionID Are Empty");
			questionIDtxt.getStyleClass().add("error");
		}
	}

	/**
	 * 1 - get all questions for teacher and set questionIdsComboBox
	 * 2 - get subjects and put them into Combobox
	 * 3 - checks if ID exists
	 * 4 - checks if question added
	 * 5 - check if question saved
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		Object[] ob=(Object[])message;
		switch((int)ob[0]) {
		case 1:
			quesList = CreateQuestions(ob[1]);
			Platform.runLater(new Runnable() {
				  @Override
				  public void run() {
					  questionslst.getItems().clear();
					  questionsidCombo.getItems().clear();
				  }
			});		
			if(quesList!=null) {
						for(Question q:quesList) {
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
										questionslst.getItems().add(q.toString());
										questionsidCombo.getItems().add(q.getId());
		
								  }
							});		
						}
						questionsidCombo.valueProperty().addListener(new ChangeListener<String>() {
		
							@Override
							public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
								for(Question q:quesList)
									if(questionsidCombo.getValue()!=null)
										if(questionsidCombo.getValue().equals(q.getId())){
													  editquestionTexttxt.setText(q.getText());
														editanswer1txt.setText(q.getAns1());
														editanswer2txt.setText(q.getAns2());
														editanswer3txt.setText(q.getAns3());
														editanswer4txt.setText(q.getAns4());
														editcorrectansCmb.getSelectionModel().select(q.getCorrectAnswer());
														break;
												  }		
									}
						});
			}
			else
				questionslst.getItems().add("Table is empty!");
			break;
		case 2:
			ArrayList<String> subjects = (ArrayList<String>)ob[1];
			subjCombo.getItems().addAll(subjects);
			break;
		case 3:			
			boolean idCheck = (boolean)ob[1];
			if(idCheck == true) {				
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  	messagelbl.setText("QuestionID Already Exists");
							questionIDtxt.getStyleClass().add("error");
					  }
				});				
			}
			else {				
				String[] questionDet = new String[8];
				questionDet[0] = subjCombo.getValue().toString().replaceAll("[^0-9]", "") + questionIDtxt.getText().toString();
				questionDet[1] = questionTexttxt.getText().toString();
				questionDet[2] = TeacherController.teacher.getpName();
				questionDet[3] = answer1txt.getText().toString();
				questionDet[4] = answer2txt.getText().toString();
				questionDet[5] = answer3txt.getText().toString();
				questionDet[6] = answer4txt.getText().toString();
				questionDet[7] = correctansCmb.getValue();
				conn.SendRequest(questionDet, 7);
			}
			break;
		case 4:
			boolean addCheck = (boolean)ob[1];
			if(addCheck == true) {
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							messagelbl.setText("Successfully Added :)");
					  }
				});		
				conn.SendRequest(TeacherController.teacher.getpName(), 2);
			}
			break;
		case 5:
			boolean saveCheck = (boolean)ob[1];
			if(saveCheck == true) {
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
						  
							editmessagelbl.setText("Successfully Added :)");
					  }
				});		
				conn.SendRequest(TeacherController.teacher.getpName(), 2);
			}
			break;
		}
			
	}
	
}
