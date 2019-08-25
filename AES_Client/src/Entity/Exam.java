package Entity;

import java.sql.Time;
import java.util.ArrayList;
/**
 * Exam Class - To save Exam Details when working with Database
 */
public class Exam {
	private String examID;
	private Time duration;
	private String teacherName;
	private String noteForStudent="";
	private String noteForTeacher="";
	private String beExecuted="NotYet";
	private ArrayList<QuestionInExam> questions=new ArrayList<QuestionInExam>();
	public Exam(String examID, Time duration, String teacherName, String noteForStudent, String noteForTeacher) {
		this.examID = examID;
		this.duration = duration;
		this.teacherName = teacherName;
		this.noteForStudent = noteForStudent;
		this.noteForTeacher = noteForTeacher;
	}
	
	public Exam(String examID, Time duration, String teacherName, String noteForStudent, String noteForTeacher,
			String beExecuted) {
		this.examID = examID;
		this.duration = duration;
		this.teacherName = teacherName;
		this.noteForStudent = noteForStudent;
		this.noteForTeacher = noteForTeacher;
		this.beExecuted = beExecuted;
	}

	public void setBeExecuted(String beExecuted) {
		this.beExecuted = beExecuted;
	}

	public String getBeExecuted() {
		return beExecuted;
	}
	public String getExamID() {
		return examID;
	}
	public Time getDuration() {
		return duration;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public String getNoteForStudent() {
		return noteForStudent;
	}
	public String getNoteForTeacher() {
		return noteForTeacher;
	}
	public ArrayList<QuestionInExam> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<QuestionInExam> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		
		String str= "Exam ID : " + examID + "\tDuration : " + duration + "\tTeacher Name : " + teacherName
				+ "\nNote For Student : " + noteForStudent + "\t Note For Teacher : " + noteForTeacher + "\nQuestions : \n";
		for(QuestionInExam q:questions)
		{
			str+=q.getQuestionNum()+" : "+q.getText()+"\n1:"+q.getAns1()+"\t\t2:"+q.getAns2()+
					"\n3:"+q.getAns3()+"\t\t4:"+q.getAns4()+"\nCorrect Answer : "+q.getCorrectAnswer()+"\nPoints : "+q.getPoint()+"\n";
		}
		return str;
		
	}
	
	

	
}
