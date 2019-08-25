package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * StudentGradesController Class - Present the grades of the student
 */
public class StudentGradesController implements ChatIF,Initializable {
	private Connection conn=new Connection(this);
	@FXML
	private ListView<Table> gradeslst=new ListView<Table>();
	/**
	 * set connection with server
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn.setChatIF();
		conn.SendRequest(StudentController.student.getpID(), 29);
	}
	/**
	 * back button
	 */
	public void BackHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(TeacherController.class.getResource("/GUI/studentPage.fxml"));

		Scene scene = new Scene(root);			 
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());

		primaryStage.setScene(scene);		
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * get student grade from server and display in UF
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		/**
		 * message[0] - TableName
		 * message[1] - String ArrayList of Table  
		 */
		Object[] obj = (Object[])message;
		/**
		 * insert table content into ListView that matches TableName
		 */
		if(((String)obj[0]).equals("StudentGrades")) {
			ArrayList<String> arr = (ArrayList<String>)obj[1];
			gradeslst.getItems().clear();
			for(int i = 0 ; i < arr.size() ; i+=7)
			{
				gradeslst.getItems().add(new Table("Student ID : " + arr.get(i) ,"Exam ID : " + arr.get( i+1 ) ,"Actual Time : " +
						arr.get( i+2 ) , "Exam Answer : " + arr.get( i+3 ) ,"Grade : " + arr.get( i+4 ) , "Exam Note : " + arr.get( i+6 )));
			}
		}
	}
	/**
	 * for listView cell to set labels in ListView
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
	            HBox.setMargin(column[i],new  Insets(0,20,0,0));
	            this.getChildren().add(column[i]);
			}
		}
	}
}
