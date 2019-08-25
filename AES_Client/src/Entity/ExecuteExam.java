package Entity;
/**
 * 
 * ExecuteExam Class - to save details of each exam when teacher wants to execute it
 *
 */
public class ExecuteExam {
	
	private String examId;
	private String code;
	private String startTime,endTime;
	private String isLocked;
	private String date;
	private String teacherId;
	public ExecuteExam(String examId, String code,String isLocked, String startTime, String endTime, String date,
			String teacherId) {
		this.examId = examId;
		this.code = code;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isLocked = isLocked;
		this.date = date;
		this.teacherId = teacherId;
	}
	
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getExamId() {
		return examId;
	}
	public String getCode() {
		return code;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public String getDate() {
		return date;
	}
	public String getTeacherId() {
		return teacherId;
	}
	
	
	

}
