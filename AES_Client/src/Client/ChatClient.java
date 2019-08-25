// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package Client;

import ocsf.client.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    openConnection();
  }

  public void setClientUI(ChatIF clientUI) {
		this.clientUI = clientUI;
	}
  
  //Instance methods ************************************************
    





/**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    this.clientUI.display(msg);
  }

  /**
   * This method handles all data coming from the UI            
   *
   * if @param type = 23 Send Word File to Server, if @param type != 23 put in obj[0] = type and in obj[1] = message then send to server
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(Object message,int type)
  {  
	  try {
		  Object[] obj=new Object[3];
		  switch(type) {
		  case 23:
			  
			  String[] str=(String[])message;
			  MyFile msg= new MyFile(str[0]);
			  //String LocalfilePath=().getPath();
			  try
			  {
				  obj[0]=type;
				  File newFile = new File(str[0]);
				  byte [] mybytearray  = new byte [(int)newFile.length()];
				  FileInputStream fis = new FileInputStream(newFile);
				  BufferedInputStream bis = new BufferedInputStream(fis);
				  msg.initArray(mybytearray.length);
				  msg.setSize(mybytearray.length);
				  bis.read(msg.getMybytearray(),0,mybytearray.length);
				  obj[1]=msg;
				  obj[2]=str[1];
				  sendToServer(obj);
			  }
			  catch (Exception e) 
			  {
				  System.out.println("Error send (Files)msg) to Server" +e.getMessage());
			  }
			  break;
		  default:
			  obj[0]=type;
			  obj[1]=message;
			  
			  sendToServer(obj);
			  break;
		  }
	  }catch (Exception e) {
				System.out.println("Error send msg to Server - "+e.getMessage());
	  }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
