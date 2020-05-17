package pwr.java;

import java.util.HashSet;
import java.util.Set;

public class Message {
		
	private String text;
	private String recipient;
	private String sender;
	private boolean isBroadcast;
	private boolean isBroadcastComplete;
	private Set<String> reachedNodes;
	private int nodesCount = 4;
	
	
	public Message() {
		
	}
	
	public Message(String text, String recipient, String sender, boolean isBroadcast) {
		this.text = text;
		this.recipient = recipient;
		this.sender = sender;
		this.setBroadcast(isBroadcast);
		this.reachedNodes = new HashSet<>();
		this.isBroadcastComplete = false;
	}
	
	public Message(String text, String recipient, String sender, boolean isBroadcast, Set<String> reachedNodes) {
		this.text = text;
		this.recipient = recipient;
		this.sender = sender;
		this.setBroadcast(isBroadcast);
		this.reachedNodes = reachedNodes;
		this.isBroadcastComplete = false;
	}
	
	public String getText() {
		return text;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "Message [from=" + sender + ", to=" + recipient + ", text=" + text + "]";
	}

	public boolean isBroadcast() {
		return isBroadcast;
	}

	public void setBroadcast(boolean isBroadcast) {
		this.isBroadcast = isBroadcast;
	}	
	
	public void addReachedNode(AppInfo node) {
		if (!reachedNodes.contains(node.getName())) {
			reachedNodes.add(node.getName());
		}
	}
	
	public boolean completeBroadcast() {
		if (reachedNodes.size() == nodesCount) {
			this.isBroadcastComplete = true;
		}
		return isBroadcastComplete;
	}
	
	public Set<String> getReachedNodes() {
		return reachedNodes;
	}
	
	public void setReachedNodes(Set<String> reachedNodes) {
		this.reachedNodes = reachedNodes;
	}
	
}
