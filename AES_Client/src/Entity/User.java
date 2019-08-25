package Entity;

/**
 * User Class - Basically saving information of a normal person and their UserName, Password for login
 */
public class User {
	private String pName;
	private String pID;
	private String userName;
	private String userPass;
	private String status;
	public User(String uName, String uPass, String n, String id,String status) {
		setpName(n);
		setpID(id);
		setUserName(uName);
		setUserPass(uPass);
		setStatus(status);
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpID() {
		return pID;
	}

	public void setpID(String pID) {
		this.pID = pID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
}
