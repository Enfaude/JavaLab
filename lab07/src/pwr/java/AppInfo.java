package pwr.java;

public class AppInfo {
	private String name;
	private String nextNodeName;
	private int port;
	private int nextNodePort;
	
	public AppInfo(String name, String recipient, int port, int recipientPort) {
		this.name = name;
		this.nextNodeName = recipient;
		this.port = port;
		this.setRecipientPort(recipientPort);
	}
	
	public String getName() {
		return name;
	}
	
	public String getNextNodeName() {
		return nextNodeName;
	}
	
	public int getPort() {
		return port;
	}

	public int getNextNodePort() {
		return nextNodePort;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRecipient(String recipient) {
		this.nextNodeName = recipient;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public void setRecipientPort(int recipientPort) {
		this.nextNodePort = recipientPort;
	}	
	
}
