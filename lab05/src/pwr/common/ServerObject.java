package pwr.common;

import java.io.Serializable;

public class ServerObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111266648636550671L;
	String name;
	int port;
	String description;
	String serverIp;
	
	public ServerObject(String newName, int newPort, String newDescription) {
		this.name = newName;
		this.port = newPort;
		this.description = newDescription;
		this.serverIp = "localhost";
	}
	
	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getServerIp() {
		return serverIp;
	}
}
