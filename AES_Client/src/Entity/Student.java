package Entity;

import java.util.ArrayList;
/**
 * Student Class - Save Student information when getting a student from DataBase
 *
 */
public class Student extends User{
	
	public Student(String stUserName, String stUserPass, String stName, String stID,String status) {
		super(stUserName, stUserPass, stName, stID,status);
	}
	public ArrayList<String> viewGrades(){
		
		return null;
	}
	public Exam getExamCopy() {
		
		return null;
	}
}
