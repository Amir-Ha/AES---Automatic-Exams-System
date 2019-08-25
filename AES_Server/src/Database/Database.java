package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Database {
	private static Connection conn;
	/**connection to data base*/
	public void connectToDB() {
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/aes-final","root","Braude"/*"SamiMac1"*/);
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
     			System.out.println("SQLException: " + ex.getMessage());
     			System.out.println("SQLState: " + ex.getSQLState());
     			System.out.println("VendorError: " + ex.getErrorCode());
            }
	}
	/**
	 * Returns user from Database- Student/Teacher/Principal
	 */
	public Object GetUser(String username) {
		Statement stmt;
		String[] user=null;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userName=\""+username+"\";");
	 		if(rs.next()) {
				user=new String[6];
				for(int i=0;i<6;i++)
					user[i]=rs.getString(i+1);
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return user;
	}
	/**
	 * Return all questions of a teacher
	 */
	public Object GetQuestions(String teacherName) {
		Statement stmt;
		ArrayList<String> questions = null;
		try 
		{
			questions=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM question WHERE teacherName=\""+teacherName+"\";");
	 		while(rs.next()) {
	 			for(int i=1;i<=8;i++)
	 				questions.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return questions;
	}
	/**
	 * Get all Subjects from Database
	 */
	public Object GetSubjects() {
		Statement stmt;
		ArrayList<String> subjects=null;
		try 
		{
			subjects=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM subject ;");
	 		while(rs.next()) {
	 			subjects.add(rs.getString(2)+" - "+rs.getString(1));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return subjects;
	}
	/**
	 * Get all Courses from Database
	 */
	public Object GetCourses() {
		Statement stmt;
		ArrayList<String> courses=null;
		try 
		{
			courses=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM course ;");
	 		while(rs.next()) {
	 			courses.add(rs.getString(2)+" - "+rs.getString(1));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return courses;
	}
	/**
	 * Check if question ID exists in the database
	 */
	public boolean ContainIdQuestion(String id) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM question WHERE idQuestion=\""+id+"\";");
	 		if(rs.next()) {
				rs.close();
	 			return true;
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	/**
	 * Check if exam code  exists in the database
	 */
	public boolean ContainCode(String code) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM executeexam WHERE codeExam=\""+code+"\";");
	 		if(rs.next()) {
				rs.close();
	 			return true;
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	/**
	 * Check if exam ID exists in the database
	 */
	public boolean ContainIdExam(String id) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM exam WHERE idExam=\""+id+"\";");
	 		if(rs.next()) {
				rs.close();
	 			return true;
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	/**
	 * Receive a question as String Array, insert it into Question table in Database
	 */
	public boolean InsertQuestion(String[] question) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO question " + "VALUES ('"+ question[0] +"', '"+question[1]+"', '"+question[2]+"', '" +question[3]+"', '"+question[4]+"', '" +question[5]+ "', '"+question[6]+"', '"+question[7]+"')");
			return true;
		} catch (SQLException e) {	return false;}
	}
	/**
	 * Receive a User and Status[online/offline] -> update user's status
	 */
	public boolean UpdateUserStatus(String status,String username) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE user SET status=\""+status+"\" WHERE userName=\""+username+"\"");
			return true;
		}catch (SQLException e) {return false;}
	}
	/**
	 * Update Question Details
	 */
	public boolean UpdateQuestion(String[] question) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE question SET text=\""+question[1]+"\", answer1=\""+question[2]+"\", answer2=\""+
					question[3]+"\", answer3=\""+question[4]+"\", answer4=\""+ question[5]+ "\", correctAnswer=\""+question[6]+"\" WHERE idQuestion=\""+question[0]+"\"");
			return true;
		}catch (SQLException e) {return false;}
	}
	/**
	 * Receive tableName and num of columns, get the table from Database, return it as ArrayList
	 */
	public Object GetTableDetails(String tableName, int colSize) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= colSize ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 *  return exams details as ArrayList
	 */
	public Object GetTeacherExams() {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM exam ;");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 5; i++)
	 				table.add(rs.getString(i));
	 			for(String s:GetExamQuestionsDetails(rs.getString(1)))
	 				table.add(s);
	 			table.add("--");
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 *  Receive exam code and return exam details as ArrayList for student
	 */
	public Object GetExam(String code) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM executeexam WHERE codeExam=\""+code+"\" and isLocked=\"Unlocked\";");
	 		if(rs.next()) {
	 			String id=rs.getString(1);
	 			rs = stmt.executeQuery("SELECT * FROM exam WHERE idExam=\""+id+"\" ;");
	 			if(rs.next()) {
	 				for(int i = 1 ; i <= 5; i++)
		 				table.add(rs.getString(i));
	 				for(String s:GetExamQuestionsDetails(id))
		 				table.add(s);
	 				table.add("--");
	 			}
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * Receive Question ID , return questions details as ArrayList
	 */
	private ArrayList<String> GetQuestion(String id) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT text,answer1,answer2,answer3,answer4,correctAnswer FROM question WHERE idQuestion=\""+id+ "\" ;");
	 		if(rs.next()) {
	 			for(int i = 1 ; i <= 6 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * Receive Exam ID , return all question details as ArrayList
	 */
	private ArrayList<String> GetExamQuestionsDetails(String id) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idQuestion,questionNum,points FROM questioninexam WHERE idExam=\""+id+ "\" ;");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 3 ; i++)
	 				table.add(rs.getString(i));
	 			for(String s:GetQuestion(rs.getString(1)))
	 					table.add(s);
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * Receive a exam as String Array, insert it into exam table in Database
	 */
	public boolean InsertExam(String[] exam,String[][] questions) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO exam " + "VALUES ('"+ exam[0] +"', '"+exam[1]+"', '"+exam[2]+"', '" +exam[3]+"', '" +exam[4]+"', 'NotYet')");
			for(int i=0;i<questions.length;i++)
				stmt.executeUpdate("INSERT INTO questioninexam " + "VALUES ('"+ questions[i][0] +"', '"+exam[0]+"', '"+questions[i][1]+"', '" +questions[i][2]+"')");
			return true;
		} catch (SQLException e) { return false; }
	}
	
	/**
	 * Receive a ExecuteExam as String Array, insert it into ExecuteExam table in Database
	 */
	public boolean InsertExecuteExam(String[] e) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO executeexam " + "VALUES ('"+ e[0] +"', '"+e[1]+"', '"+e[2]+"', '" +e[3]+"', '"+e[4]+"', '" +e[5]+ "', '"+e[6]+"')");
			stmt.executeUpdate("UPDATE exam SET beExecuted=\"Executed\" WHERE idExam=\""+e[0]+"\"");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Receive a Note as String Array, insert it into note table in Database
	 */
	public boolean InsertNote(String[] n) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO notes " + "VALUES ('"+ n[0] +"', '"+n[1]+"', '"+n[2]+"', '" +n[3]+"')");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Update execute exam to locked
	 */
	public boolean 	UpdateLockExam(String id) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE executeexam SET isLocked=\"Locked\" WHERE idExam=\""+id+"\"");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Check if exam Code exists in the database
	 */
	public boolean ContainExamCode(String[] code) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM executeexam WHERE codeExam=\""+code[0]+"\" and isLocked=\"Unlocked\";");
	 		if(rs.next()) 
	 		{
				rs = stmt.executeQuery("SELECT * FROM examsolution WHERE idExam=\""+rs.getString(1)+"\" and idStudent=\""+code[1]+"\";");
				if(!rs.next()) 
				{
					rs.close();
		 			return true;
				}
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	/**
	 * Check if exam Locked in the database
	 */
	public boolean CheckExamLocked(String id) {
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM executeexam WHERE idExam=\""+id+"\" and isLocked=\"Locked\";");
	 		if(rs.next()) {
				rs.close();
	 			return true;
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	/**
	 * Receive a student solution as String Array, insert it into table in Database
	 */
	public boolean InsertStudentSol(String[] n) 
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO examsolution VALUES ('"+ n[0] +"', '"+n[1]+"', '"+n[2]+"', '" +n[3]+"', '" +n[4]+"', '" +n[5]+"', '" +n[6]+"')");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Update teacher note for student in examsolution
	 */
	public boolean 	UpdateExamNote(String id,String examId,String newGrade,String note) 
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE examsolution SET examNotes=\""+note+"\" , grade=\""+newGrade+"\" , isGradeConfirmed=\"Confirmed\" WHERE idExam=\""+examId+"\" and idStudent=\""+id+"\";");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Update Confirm for new duration in note
	 */
	public boolean 	UpdateConfirmed(String examId) 
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE notes SET isConfirmed=\"Confirmed\" WHERE idExam=\""+examId+"\" ;");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Receive a wrong answer (cheats) as String Array, insert it into table in Database
	 */
	public boolean InsertWrongAnswer(String[] n) 
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO wronganswers VALUES ('"+ n[0] +"', '"+n[1]+"', '"+n[2]+"', '" +n[3]+"')");
			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Receive a Statistic  as String Array, insert it into table in Database
	 */
	public boolean InsertStatistic(String... n) 
	{
		Statement stmt;
		try {
			stmt = conn.createStatement();
			double avg=0;
			ArrayList<Double> grades=new ArrayList<Double>();
			ResultSet rs = stmt.executeQuery("SELECT grade FROM examsolution WHERE idExam=\""+n[0]+"\" ;");
			while(rs.next()) {
				grades.add(rs.getDouble(1));
				avg+=rs.getDouble(1);
			}
			rs.close();
			avg/=grades.size();
			Collections.sort(grades);
			double med=grades.get(grades.size()/2);
			  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
			stmt.executeUpdate("INSERT INTO statistics VALUES ('"+ n[0] +"', '"+n[1]+"', '"+n[2]+"', '" +n[3]+"', '" +n[4]+"', '" +avg+"', '" +med+"')");
			stmt.executeUpdate("UPDATE executeexam SET endTime=\""+dateFormat.format(date).toString() +"\" WHERE idExam=\""+n[0]+"\" ;");

			return true;
		} catch (SQLException ex) {	return false;}
	}
	/**
	 * Receive Student ID , return all Grades;
	 */
	private ArrayList<String> GetStudentGrades(String id) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM examsolution WHERE idStudent=\""+id+ "\" ;");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 7 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * ReTuRn all Grades of students for certain teacher's exams
	 */
	public ArrayList<String> GetExamsGrades(String name) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM examsolution where idExam in (select idExam from exam where teacherName=\""+name+"\")");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 7 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * ReTuRn Cheaters of certain teacher's exams
	 */
	public ArrayList<String> GetCheaters(String name) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM wronganswers where idExam in (select idExam from exam where teacherName=\""+name+"\")");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 4 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * ReTuRn Statistics of certain teacher's exams
	 */
	public ArrayList<String> GetStatistics(String name) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM statistics where idExam in (select idExam from exam where teacherName=\""+name+"\")");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 7 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * ReTuRn confirmed grades of student
	 */
	public ArrayList<String> GetConfirmedGardes(String id) {
		Statement stmt;
		ArrayList<String> table = null;
		try 
		{
			table=new ArrayList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\"");
	 		while(rs.next()) {
	 			for(int i = 1 ; i <= 7 ; i++)
	 				table.add(rs.getString(i));
	 		}
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return table;
	}
	/**
	 * ReTuRn statistics for bar chart by student
	 */
	public ArrayList<Double> GetStatisticBySud(String id) {
		Statement stmt;
		ArrayList<Double> statis = null;
		try 
		{
			statis=new ArrayList<Double>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade<=54;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade>54 and grade<=65;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade>65 and grade<=75;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade>75 and grade<=85;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade>85 and grade<=95;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" and grade>95 and grade<=100;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT avg(grade) FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" ;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		ArrayList<Double> med=new ArrayList<Double>();
	 		rs = stmt.executeQuery("SELECT grade FROM examsolution where idStudent=\""+id+"\" and isGradeConfirmed=\"Confirmed\" order by grade;");
	 		while(rs.next()) {
	 			med.add(rs.getDouble(1));
	 		}
	 		if(med.size()>0)
	 			statis.add(med.get(med.size()/2));
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return statis;
	}
	/**
	 * ReTuRn statistics for bar chart by course
	 */
	public ArrayList<Double> GetStatisticByCourse(String num) {
		Statement stmt;
		ArrayList<Double> statis = null;
		try 
		{
			statis=new ArrayList<Double>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade<=54;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade>54 and grade<=65;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade>65 and grade<=75;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade>75 and grade<=85;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade>85 and grade<=95;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" and grade>95 and grade<=100;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT avg(grade) FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" ;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		ArrayList<Double> med=new ArrayList<Double>();
	 		rs = stmt.executeQuery("SELECT grade FROM examsolution where idExam  like \"__"+num+"__\" and isGradeConfirmed=\"Confirmed\" order by grade;");
	 		while(rs.next()) {
	 			med.add(rs.getDouble(1));
	 		}
	 		if(med.size()>0)
	 			statis.add(med.get(med.size()/2));
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return statis;
	}
	/**
	 * ReTuRn statistics for bar chart by teacher Name
	 */
	public ArrayList<Double> GetStatisticByTeacher(String name) {
		Statement stmt;
		ArrayList<Double> statis = null;
		try 
		{
			statis=new ArrayList<Double>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade<=54;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade>54 and grade<=65;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade>65 and grade<=75;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade>75 and grade<=85;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade>85 and grade<=95;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT Count(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" and grade>95 and grade<=100;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		rs = stmt.executeQuery("SELECT avg(grade) FROM examsolution where idExam  in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" ;");
	 		if(rs.next()) {
	 			statis.add(rs.getDouble(1));
	 		}
	 		ArrayList<Double> med=new ArrayList<Double>();
	 		rs = stmt.executeQuery("SELECT grade FROM examsolution where idExam in (select idExam from exam where teacherName=\""+name+"\") and isGradeConfirmed=\"Confirmed\" order by grade;");
	 		while(rs.next()) {
	 			med.add(rs.getDouble(1));
	 		}
	 		if(med.size()>0)
	 			statis.add(med.get(med.size()/2));
			rs.close();
		} catch (SQLException e) {e.printStackTrace();}
		return statis;
	}
	
}

