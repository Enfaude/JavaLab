package pwr.java;

public class Message {
	
	private String text;
	private String recipient;
	private String sender;
	
	public Message() {
		
	}
	
	public Message(String text, String recipient, String sender) {
		this.text = text;
		this.recipient = recipient;
		this.sender = sender;
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

}
