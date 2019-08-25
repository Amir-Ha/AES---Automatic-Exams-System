package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * ReportsController Class - Principal Chooses which Report to see
 */
public class ReportsController {
	/**
	 * back button
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
	 * teacher report button 
	 */
	public void TeacherExamsPageHandle(ActionEvent event) throws Exception {
		ReportExamController.report="Teacher Exams";
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(ReportExamController.class.getResource("/GUI/reportExamsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * 
	 * courses report button 
	 */
	public void CoursesExamsPageHandle(ActionEvent event) throws Exception {
		ReportExamController.report="Course Exams";
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(ReportExamController.class.getResource("/GUI/reportExamsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
	/**
	 * students report button 
	 */
	public void StudentExamsPageHandle(ActionEvent event) throws Exception {
		ReportExamController.report="Student Exams";
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(ReportExamController.class.getResource("/GUI/reportExamsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
		primaryStage.setScene(scene);	
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.show();
	}
}
