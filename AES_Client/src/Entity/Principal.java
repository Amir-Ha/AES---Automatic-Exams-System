package Entity;

import java.util.ArrayList;
/**
 * Principal Class - To Save Principal's Information
 */
public class Principal extends User{

	public Principal(String prUserName, String prUserPass, String prName, String prID,String status) {
		super(prUserName, prUserPass, prName, prID,status);
		
	}
	public boolean confirmDurationUpdate(String examID) {
		return true;
	}
	public ArrayList<String> getExamDetails(String examID){
		return null;
	}
	public ArrayList<Double> getTeacherGrades(String teacherID){
		return null;
	}
	public ArrayList<Double> getCourseGrades(String courseID){
		return null;
	}
	public ArrayList<Double> getStudentGrades(String studentID){
		return null;
	}
}
