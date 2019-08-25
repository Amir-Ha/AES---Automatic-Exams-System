package Client;

import java.io.IOException;

public class Connection {

	 /**
	   * The default port to connect on.
	   */
	final public static int DEFAULT_PORT = 7777;
	private ChatIF chatIF;
	
	public static String host="localhost"; 
	private static ChatClient client;
	
	
	
	public Connection(ChatIF chatIF) {
		this.chatIF = chatIF;
	}
	/**
	 * This method is used to connect to server.
	 * 
	 */
	public boolean Connect() {
		 try 
		    {
		      client= new ChatClient(host, DEFAULT_PORT);
		      setChatIF();
				return true;
		    } 
		    catch(IOException exception) 
		    {
		    	return false;
		    }
	}
	/**
	 * Set this client to the current Client
	 */
	public void setChatIF() {
		client.setClientUI(chatIF);
	}
	
	public void Disconnect() {client.quit();}
	/**
	 * Send request to server with required mission
	 * @param message - contains details for the mission
	 * @param type - contain type of the mission
	 */
	public boolean SendRequest(Object message,int type) 
	  {
	    try
	    {
	        client.handleMessageFromClientUI(message,type);
	        return true;
	    } 
	    catch (Exception ex) 
	    {
	    	return false;
	    }
	  }
}
