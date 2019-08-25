package Entity;
/**
 * Question Class - Save Question Details, ID, text, teacherName, 4 answers and correct answer
 */
public class Question {
	private String id,text,teacherName,ans1,ans2,ans3,ans4,correctAnswer;
	
	public Question(String id, String text, String teacherName, String ans1, String ans2, String ans3, String ans4,
			String correctAnswer) {
		this.id = id;
		this.text = text;
		this.teacherName = teacherName;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.correctAnswer = correctAnswer;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getAns1() {
		return ans1;
	}

	public String getAns2() {
		return ans2;
	}

	public String getAns3() {
		return ans3;
	}

	public String getAns4() {
		return ans4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	@Override
	public String toString() {
		return "Question id:" + id + "\n  text: " + text + "\n   1: " + ans1 + "   2: "
				+ ans2 + "\n   3: " + ans3 + "   4: " + ans4 + "\n   Correct Answer is " + correctAnswer ;
	}


}
