package Server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Client.MyFile;
import Database.Database;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 7777;
  private Database db;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);    
    db=new Database();
    db.connectToDB();
  }


  
  //Instance methods ************************************************
  /**
   * type - to get message what happen in server (for developer)
   */
  private String[] type=new String[] {"",
		  "1 to get user by Username",
		  "2 to get all question for teacher name",
		  "3 to set user online",
		  "4 to set user offline",
		  "5 to get all subjects",
		  "6 to check if question id exist",
		  "7 to insert question ",
		  "8 to update question details",
		  "9 to send Table from database",
		  "10 to get all teacher exams",
		  "11 to get all courses", 
		  "12 to check if exam id exist",
		  "13 to add exam",
		  "14 to check cod if exist",
		  "15 to add execute exam and update exam to be executed",
		  "16 to insert note to change duration",
		  "17 to locked executed exam",
		  "18 to check if exam code exist",
		  "19 to get exam for student ",
		  "20 to save student client in exam in hashmap",
		  "21 to save student answer",
		  "22 to save wrong answer to check if cheating",
		  "23 to upload exam docx",
		  "24 to confirm new duration",
		  "25 to confirm grade and add notes to student",
		  "26 to get grades of certain teacher exams",
		  "27 to get cheaters of certain teacher exams",
		  "28 to get Statistics of certain teacher exams",
		  "29 to get student grades",
		  "30 to get Statistics by student",
		  "31 to get Satistics by Course",
		  "32 to get Statistics by Teacher"};
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   *
   * cases:
   * 1 to get user by UserName
   * 2 to get all question for teacher name
   * 3 to set user online
   * 4 to set user offline
   * 5 to get all subjects
   * 6 to check if question id exist
   * 7 to insert question 
   * 8 to update question details
   * 9 to send Table from database
   * 10 to get all exams
   * 11 to get all courses
   * 12 to check if exam id exist
   * 13 to add exam
   * 14 to check cod if exist
   * 15 to add execute exam and update exam to be executed
   * 16 to insert note to change duration
   * 17 to locked executed exam
   * 18 to check if exam code exist
   * 19 to get exam for student 
   * 20 to save student client in exam in hash map
   * 21 to save student answer
   * 22 to save wrong answer to check if cheating
   * 23 to upload exam DOCX
   * 24 to confirm new duration
   * "25 to confirm grade and add notes to student
   * 26 to get grades of certain teacher exams
   * 27 to get cheaters of certain teacher exams
   * 28 to get Statistics of certain teacher exams
   * 29 to get student grades
   * 30 to get Statistics by student
   * 31 to get Statistics by Course
   * 32 to get Statistics by Teacher
   */ 
  private Map<ConnectionToClient, String> studentInExam=new HashMap<ConnectionToClient,String> ();
  private Map <String, Integer> studentNumberInExam =new HashMap<String, Integer> ();
  private Map <String, Integer> studentNumberFinishExam =new HashMap<String, Integer> ();
  private Map <String,String[]> examCheater=new HashMap<String,String[]>();
  
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  try {
		  
		  Object[] obj = (Object[]) msg;
		  int type=(int)obj[0];
		  System.out.println("Type - "+this.type[type]);
		  
		  //arrays for case use...
		  Object[] messagetoClient=new Object[2];
		  Object[] messagefromClient;

		  switch(type) {
		  case 1:
			  client.sendToClient(db.GetUser((String)obj[1]));
			  break;
		  case 2:			  
			  messagetoClient[0]=1;
			  messagetoClient[1]=db.GetQuestions((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 3:
			  db.UpdateUserStatus("online", (String)obj[1]);
			  break;
		  case 4: 
			  db.UpdateUserStatus("offline", (String)obj[1]);
			  break;
		  case 5:
			  messagetoClient[0]=2;
			  messagetoClient[1]=db.GetSubjects();
			  client.sendToClient(messagetoClient);
			  break;
		  case 6:
			  messagetoClient[0]=3;
			  messagetoClient[1]=db.ContainIdQuestion((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 7:
			  messagetoClient[0]=4;
			  messagetoClient[1]=db.InsertQuestion((String[])obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 8:
			  messagetoClient[0] = 5;
			  messagetoClient[1] = db.UpdateQuestion((String[])obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 9:
			  messagefromClient = (Object[])obj[1];
			  messagetoClient[0] = messagefromClient[0];
			  messagetoClient[1] = db.GetTableDetails((String)messagefromClient[0], (int)messagefromClient[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 10:
			  messagetoClient[0]=3;
			  messagetoClient[1]=db.GetTeacherExams();
			  client.sendToClient(messagetoClient);
			  break;
		  case 11:
			  messagetoClient[0]=4;
			  messagetoClient[1]=db.GetCourses();
			  client.sendToClient(messagetoClient);
			  break;
		  case 12:
			  messagetoClient[0]=5;
			  messagetoClient[1]=db.ContainIdExam((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 13:
			  messagefromClient = (Object[])obj[1];
			  messagetoClient[0]=6;
			  messagetoClient[1]=db.InsertExam((String[])messagefromClient[0], (String[][])messagefromClient[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 14:
			  messagetoClient[0]="Can Execute";
			  messagetoClient[1]=db.ContainCode((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 15:
			  messagetoClient[0]="Insert";
			  messagetoClient[1]=db.InsertExecuteExam((String[])obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 16:
			  messagetoClient[0]="Change";
			  messagetoClient[1]=db.InsertNote((String[])obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 17:
			  messagetoClient[0]="Locked";
			  messagetoClient[1]=db.UpdateLockExam((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  for (Map.Entry<ConnectionToClient,String> entry: studentInExam.entrySet()) 
			  {
				  if(entry.getValue().equals((String)obj[1])) 
				  {
					  messagetoClient[0]="ExamLocked";
					  messagetoClient[1]="";
					  entry.getKey().sendToClient(messagetoClient);
					  studentInExam.remove(entry.getKey());
				  }
			  }
			  break;
		  case 18:
			  messagetoClient[0]=1;
			  messagetoClient[1]=db.ContainExamCode((String[])obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 19:
			  messagetoClient[0]=2;
			  messagetoClient[1]=db.GetExam((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 20:
			  studentInExam.put(client, (String)obj[1]);
			  if(studentNumberInExam.get((String)obj[1])==null) {
				  studentNumberInExam.put((String)obj[1], 0);
				  studentNumberFinishExam.put((String)obj[1], 0);
			  }
			  studentNumberInExam.put((String)obj[1], studentNumberInExam.get((String)obj[1])+1);
			  break;
		  case 21:
			  messagefromClient = (Object[])obj[1];
			  String[] str=(String[])messagefromClient[1];
			  if((boolean)messagefromClient[0]) 
				  studentNumberFinishExam.put(str[1], studentNumberFinishExam.get(str[1])+1);
			  if(studentNumberFinishExam.get(str[1])==studentNumberInExam.get(str[1])) { 
				  db.UpdateLockExam(str[1]);
			  }  
			  /////
			  messagetoClient[0]="SaveSol";
			  messagetoClient[1]=db.InsertStudentSol(str);
			  client.sendToClient(messagetoClient);
			  //////////
			  if(db.CheckExamLocked(str[1])) {
				  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				  Date date = new Date();
				  db.InsertStatistic(str[1],dateFormat.format(date).toString(),str[7],  studentNumberInExam.get(str[1])+"",
						  studentNumberFinishExam.get(str[1])+"");
			  }
			
			  break;
		  case 22:
			  str=(String[])obj[1];
			  if(examCheater.get(str[0]+str[2])!=null) 
			  {
				  db.InsertWrongAnswer(examCheater.get(str[0]+str[2]));
				  db.InsertWrongAnswer(str);
			  }
			  else
				  examCheater.put(str[0]+str[2], str);
			  break;
		  case 23:
			  studentNumberFinishExam.put((String)obj[2], studentNumberFinishExam.get((String)obj[2])+1);
			  int fileSize =((MyFile)obj[1]).getSize(); 
			  System.out.println("Message received: " + obj[1] + " from " + client);
			  System.out.println("length "+ fileSize);
			  
			  /////////////////////////////////////////////
			  String LocalfilePath="exam.docx";
				File newFile = new File (LocalfilePath);
				byte [] mybytearray  = new byte [fileSize];
				FileInputStream clientData = null;
				try {
					clientData = new FileInputStream(((MyFile)obj[1]).getFileName());
				
				FileOutputStream fos = null;
					fos = new FileOutputStream(newFile);

							
				((MyFile) obj[1]).initArray(mybytearray.length);
				((MyFile) obj[1]).setSize(mybytearray.length);
				
				int bytesRemaining = fileSize;
				int bytesRead = 0;
			
					clientData.read(mybytearray, 0, mybytearray.length);

					fos.write(mybytearray, 0, mybytearray.length);
					fos.flush();
					if (clientData.read() >= 0) {
					    throw new IllegalStateException("Unexpected bytes still on the input from the client");
					}
									
				System.out.println("Message received: " + obj[1] + " from " + client);
				System.out.println("File "+ ((MyFile) obj[1]).getFileName() +" of the length "+ fileSize + " is received");
				
					fos.close();
					clientData.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  break;
		  case 24:
			  messagefromClient = (Object[])obj[1];
			  messagetoClient[0]="Confirmed";
			  messagetoClient[1]=db.UpdateConfirmed((String) messagefromClient[0]);
			  client.sendToClient(messagetoClient);
			  for (Map.Entry<ConnectionToClient,String> entry: studentInExam.entrySet()) 
			  {
				  if(entry.getValue().equals((String) messagefromClient[0])) 
				  {
					  messagetoClient[0]="NewDuration";
					  messagetoClient[1]=messagefromClient[1];
					  entry.getKey().sendToClient(messagetoClient);
				  }
			  }
			  break;
		  case 25:
			  messagefromClient = (Object[])obj[1];
			  messagetoClient[0]="GradeConfirmed";
			  messagetoClient[1]=db.UpdateExamNote((String)messagefromClient[0], (String)messagefromClient[1]
					  , (String)messagefromClient[2],(String)messagefromClient[3]);
			  break;
		  case 26:
			  messagetoClient[0] = "examsolution";
			  messagetoClient[1] = db.GetExamsGrades((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 27:
			  messagetoClient[0] = "wronganswers";
			  messagetoClient[1] = db.GetCheaters((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 28:
			  messagetoClient[0] = "statistics";
			  messagetoClient[1] = db.GetStatistics((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 29:
			  messagetoClient[0] = "StudentGrades";
			  messagetoClient[1] = db.GetConfirmedGardes((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 30:
			  messagetoClient[0] = "StatisticBySud";
			  messagetoClient[1] = db.GetStatisticBySud((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 31:
			  messagetoClient[0] = "StatisticByCourse";
			  messagetoClient[1] = db.GetStatisticByCourse((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  case 32:
			  messagetoClient[0] = "StatisticByTeacher";
			  messagetoClient[1] = db.GetStatisticByTeacher((String)obj[1]);
			  client.sendToClient(messagetoClient);
			  break;
		  }
	  } catch (Exception e) {System.out.println(e.getMessage());} 
}

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  
}
//End of EchoServer class
