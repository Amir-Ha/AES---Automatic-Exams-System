package Entity;

import java.sql.Time;
/**
 * Teacher Class - Save teacher information when getting a teacher from database
 */
public class Teacher extends User {
	public Teacher(String teUserName, String teUserPass, String teName, String teID,String status) {
		super(teUserName, teUserPass, teName, teID,status);
	}
	public void createExam() {
		
	}
	public void createQuestion() {
		
	}
	
	public boolean lockExam( String examID) {
		return false;
	}
	public void updateExamDuration(Time newDuration, String examID) {
		
	}
	public boolean principalPermission(String explanation) {
		return false;
	}
	public void addNotes(String txt) {
		
	}
	public void updateExamGrade(String examID, double newGrade, String explain) {
		
	}
	public boolean confirmGrade() {
		return false;
	}
	public String getExamStatistics(String examID) {
		return "";
	}
	
}
