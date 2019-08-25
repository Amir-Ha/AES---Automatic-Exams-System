package Entity;
/**
 * 
 * Answer Class - to save Answers when getting them from Database
 *
 */
public class Answer {
	private String questionText;
	private String answer;
	private boolean isItCorrect;
	private double point;
	public Answer(String questionText,String answer, boolean isItCorrect,double point) {
		this.questionText=questionText;
		this.answer = answer;
		this.isItCorrect = isItCorrect;
		this.point=point;
	}
	public String getAnswer() {
		return answer;
	}
	public boolean isItCorrect() {
		return isItCorrect;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	public String getQuestionText() {
		return questionText;
	}
	
	
}
