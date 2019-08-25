package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * DurationController Class - The teacher can change the Exam Duration, Confirmation is sent to the principal
 */
public class DurationController implements ChatIF,Initializable {
	private Connection conn=new Connection(this);

	@FXML
	private ListView<Note> durlst=new ListView<Note>();;
	private int index=0;
	/**
	 * Back button method to back to previous frame
	 */
	public void PreviousPageHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(PrincipalController.class.getResource("/GUI/principalPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);		
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * Send New Duration Confirmation to server
	 * @param id of exam
	 * @param newDuration new exam time
	 */
	public void SendConfirm(String id,String newDuration) {
		String[] mess=new String[] {id,newDuration};
		conn.SendRequest(mess, 24);
	}
	/**
	 * this class for listView cell type to add labels and button to confirm
	 *
	 */
	public  class Note extends HBox {
        Label examId = new Label();
        Label note = new Label();
        Label newDuration = new Label();
        Label isConfirmed = new Label();
        Button conf=new Button("Confirm");
        
        Note(int i,String...text) {
             super();
             examId.setText("Exam ID : "+text[0]+"\t");
             examId.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(examId, Priority.ALWAYS);
             
             note.setText("Note : "+text[1]+"\t");
             note.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(note, Priority.ALWAYS);
             
             newDuration.setText("New Duration : "+text[2]+"\t");
             newDuration.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(newDuration, Priority.ALWAYS);
             
             isConfirmed.setText("is Confirmed : "+text[3]+"\t");
             isConfirmed.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(isConfirmed, Priority.ALWAYS);
             conf.getStyleClass().add("buttonlst");
             if(!text[3].equals("NotConfirmed"))
             {
            	 conf.setDisable(true);
             }
            conf.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					SendConfirm(text[0],text[2]);
					index=i;
				}
			});
             this.getChildren().addAll(examId,note,newDuration,isConfirmed, conf);
        }
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
	 * set connection with the server
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		conn.setChatIF();
		sendTableRequest("notes", 4);	

	}
	/**
	 * get message from server and handle it
	 * notes - notes table details
	 *  confirmed - get message if confirmation added or not
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
		switch((String)obj[0]) {
		case "notes":
			ArrayList<String> arr = (ArrayList<String>)obj[1];
			durlst.getItems().clear();
			for(int i = 0 ; i < arr.size() ; i+=4)
			{
				durlst.getItems().add(new Note(i,arr.get(i),arr.get(i+1),arr.get(i+2)
						,arr.get(i+3)));
			}
			break;
		case "Confirmed":
			if((boolean)obj[1]) {
				durlst.getItems().get(index).conf.setDisable(true);
				sendTableRequest("notes", 4);	
			}
			break;
		}
	}
}
