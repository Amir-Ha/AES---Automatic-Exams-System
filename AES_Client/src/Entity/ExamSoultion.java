package Entity;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * ExamSolution Class - To save the solution of a specific student for a specific Exam
 * including HashMap for each question and it's Answers ( Student Answers )
 */
public class ExamSoultion {
	private Map<String,Answer> examAnswers=new HashMap<String,Answer>();//key -> question id ,entry -> Answer;
	private String examId;
	public ExamSoultion(String examId) {
		this.examId = examId;
	}
	public void AddAnswer(String id,Answer ans) {
		this.examAnswers.put(id, ans);
	}
	public Map<String, Answer> getExamAnswers() {
		return examAnswers;
	}
	public String getExamId() {
		return examId;
	}
	
	
}
