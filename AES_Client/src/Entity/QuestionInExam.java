package Entity;
/**
 * QuestionInExam Class - Save for each question in exam it's number in the exam( 1, 2, 3 .. ) and it's points
 */
public class QuestionInExam extends Question {
	private String questionNum;
	private double point;
	public QuestionInExam(String id, String text, String teacherName, String ans1, String ans2, String ans3,
			String ans4, String correctAnswer, String questionNum, double point) {
		super(id, text, teacherName, ans1, ans2, ans3, ans4, correctAnswer);
		this.questionNum = questionNum;
		this.point = point;
	}
	public String getQuestionNum() {
		return questionNum;
	}
	public double getPoint() {
		return point;
	}
	@Override
	public String toString() {
		return super.toString()+ "\n QuestionNum:" + questionNum + "\n point=" + point;
	}
	
}
