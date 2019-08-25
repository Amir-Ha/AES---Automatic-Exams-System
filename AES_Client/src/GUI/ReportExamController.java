package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import Client.ChatIF;
import Client.Connection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * ReportExamController Class - Principal can see Bar Charts for exams in different ways
 */
public class ReportExamController implements ChatIF,Initializable {
	private Connection conn=new Connection(this);
	public static String report;
	@FXML
	private Label avglbl,reportpagelbl;
	@FXML
	private BarChart<?,?> barchart;
	@FXML
	private ComboBox<String> repcmb;

	/**
	 *back button handle to back to previous frame 
	 * 
	 */
	public void PreviousPageHandle(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();

		AnchorPane root = (AnchorPane) FXMLLoader.load(ReportsController.class.getResource("/GUI/reportsPage.fxml"));

		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/GUI/allStyleSheet.css").toExternalForm());
		
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
	 * Student Exams- send to get user details
	 * Course Exam - send to get courses details
	 * Teacher Exams- send to get user details
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		conn.setChatIF();
		reportpagelbl.setText(report);
		repcmb.getItems().clear();
		switch(report) {
		case "Student Exams":
			sendTableRequest("user", 6);
			break;
		case "Course Exams":
			sendTableRequest("course", 2);
			break;
		case "Teacher Exams":
			sendTableRequest("user", 6);
			break;
		}
		
	}
	/**
	 * user- to add user to combo box 
	 * course - to add courses to combo box
	 * StatisticBySud - get details for bar chart
	 * StatisticByTeacher -get details for bar chart 
	 * StatisticByCourse - get details for bar chart
	 */
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		/**
		 * message[0] - TableName
		 * message[1] - String ArrayList of Table
		 */
		Object[] obj = (Object[])message;
		switch((String)obj[0]) {
		case "user":
			ArrayList<String> arr = (ArrayList<String>)obj[1];
			if(report.equals("Student Exams")) {
				for(int i = 0 ; i < arr.size() ; i+=6)
				{
					if(arr.get(i+2).equals("Student"))
						repcmb.getItems().add(arr.get(i+4)+"-"+arr.get(i+3));
					
				}
			}
			else {
				for(int i = 0 ; i < arr.size() ; i+=6)
				{
					if(arr.get(i+2).equals("Teacher"))
						repcmb.getItems().add(arr.get(i+4)+"-"+arr.get(i+3));
				}
			}
			repcmb.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(repcmb.getValue()!=null) 
						if(report.equals("Student Exams"))
							conn.SendRequest(repcmb.getValue().substring(0, 9), 30);
						else
							conn.SendRequest(repcmb.getValue().substring(10), 32);
				}
			});
			break;
		case "course":
			arr = (ArrayList<String>)obj[1];
			for(int i = 0 ; i < arr.size() ; i+=2)
			{
				repcmb.getItems().add(arr.get(i) + "-" + arr.get( i+1 ));
			}
			repcmb.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(repcmb.getValue()!=null)
						conn.SendRequest(repcmb.getValue().substring(0, 2), 31);
						
				}
			});
			break;
		case "StatisticBySud":
			ArrayList<Double> stas=(ArrayList<Double>)obj[1];
			
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 avglbl.setText("");
							barchart.getData().clear();
						if(stas.size()==8)
						{
					       
							XYChart.Series series1 = new XYChart.Series();
							series1.setName("Exam Counter");
					        series1.getData().add(new XYChart.Data<>("0-54", stas.get(0)));
					        series1.getData().add(new XYChart.Data<>("55-65", stas.get(1)));
					        series1.getData().add(new XYChart.Data<>("66-75", stas.get(2)));
					        series1.getData().add(new XYChart.Data<>("76-85", stas.get(3)));
					        series1.getData().add(new XYChart.Data<>("86-95", stas.get(4)));
					        series1.getData().add(new XYChart.Data<>("96-100", stas.get(5)));
					        barchart.getData().addAll(series1);
					        
					        avglbl.setText("Average : "+stas.get(6)+"\nMedian : "+stas.get(7));
						}	
					}
				});
			break;
		case "StatisticByTeacher":
			stas=(ArrayList<Double>)obj[1];
			
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
				        avglbl.setText("");
						barchart.getData().clear();
						if(stas.size()==8)
						{
							XYChart.Series series1 = new XYChart.Series();
							series1.setName("Student Exams Counter");
					        series1.getData().add(new XYChart.Data<>("0-54", stas.get(0)));
					        series1.getData().add(new XYChart.Data<>("55-65", stas.get(1)));
					        series1.getData().add(new XYChart.Data<>("66-75", stas.get(2)));
					        series1.getData().add(new XYChart.Data<>("76-85", stas.get(3)));
					        series1.getData().add(new XYChart.Data<>("86-95", stas.get(4)));
					        series1.getData().add(new XYChart.Data<>("96-100", stas.get(5)));
					        barchart.getData().addAll(series1);
					        
					        avglbl.setText("Average : "+stas.get(6)+"\nMedian : "+stas.get(7));
						}
					}
				});
			
			break;
		case "StatisticByCourse":
			stas=(ArrayList<Double>)obj[1];
			
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
				        avglbl.setText("");
						barchart.getData().clear();
						if(stas.size()==8)
						{
							XYChart.Series series1 = new XYChart.Series();
							series1.setName("Student Exams Counter");
					        series1.getData().add(new XYChart.Data<>("0-54", stas.get(0)));
					        series1.getData().add(new XYChart.Data<>("55-65", stas.get(1)));
					        series1.getData().add(new XYChart.Data<>("66-75", stas.get(2)));
					        series1.getData().add(new XYChart.Data<>("76-85", stas.get(3)));
					        series1.getData().add(new XYChart.Data<>("86-95", stas.get(4)));
					        series1.getData().add(new XYChart.Data<>("96-100", stas.get(5)));
					        barchart.getData().addAll(series1);
					        
					        avglbl.setText("Average : "+stas.get(6)+"\nMedian : "+stas.get(7));
						}
					}
				});
			
			break;
		}

	}
}
