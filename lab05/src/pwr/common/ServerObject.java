package pwr.common;

import java.io.Serializable;

public class ServerObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2202648449147859423L;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ServerObject object = (ServerObject) obj;
        return port == object.getPort()
                && (name == object.getName() 
                     || (name != null && name.equals(object.getName())))
                && (serverIp == object.getServerIp()
                     || (serverIp != null && serverIp.equals(object.getServerIp())));
    
	}
	
	
}
