package pwr.java;


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class Messenger {
	AppInfo appInfo;
	List<Message> inbox = new ArrayList<>();
	ServerSocket inboxSocket;
	Socket sendingSocket;
	InboxThread inboxThread;
	static final String UNICAST = "unicast";
	static final String BROADCAST = "broadcast";
	static final String RECIPIENT = "recipient";
	static final String CAST_TYPE = "castType";
	static final String SENDER = "sender";
	static final String MESSAGE_TEXT = "message";
	static final String REACHED_NODES = "reachedNodes";
	
	
	public Messenger(AppInfo appInfo) {
		this.appInfo = appInfo;
		hostInbox();
		inboxThread = new InboxThread(this);
		inboxThread.start();
	}

	public boolean sendMessage(Message message) throws SOAPException, IOException {
		SOAPMessage soapMessage = createSOAPMessage(message);
		return sendMessage(soapMessage);
	}
	
	public boolean sendMessage(SOAPMessage soapMessage) throws SOAPException, IOException {
		if (soapMessage != null) {
			if (connectSender()) {
				try {
			        PrintStream out = new PrintStream(sendingSocket.getOutputStream(), true);
					soapMessage.writeTo(out);
					out.close();
					return true;
				} catch (IOException e) {
					sendMessage(soapMessage);
					e.printStackTrace();
					throw e;
				} catch (SOAPException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}			
		return false;
		
	}

	public void checkMessage(SOAPMessage soapMessage) throws SOAPException, IOException {
		try {
			SOAPHeader header = soapMessage.getSOAPHeader();
			String recipient = header.getAttribute(RECIPIENT);
			String castType = header.getAttribute(CAST_TYPE);
			if (recipient.toLowerCase().equals(appInfo.getName().toLowerCase())) {
				System.out.println("Received new message adressed to this client");
				Message message = decodeSOAPMessage(soapMessage);
				if (message != null) {
					inbox.add(message);
					System.out.println("You have new message in inbox: ");
					System.out.println(message);
				}
			} else if (castType.equals(BROADCAST)) {
				Message message = decodeSOAPMessage(soapMessage);
				if (message != null) {
					message.addReachedNode(appInfo);
					if (message.completeBroadcast()) {
						System.out.println("Received message is a broadcast message and this is its last node to reach.");
					} else {
						System.out.println("Received message is a broadcast message. It has been saved and the copy was sent further");
						sendMessage(message);
					}
					inbox.add(message);
					System.out.println("You have new broadcast message in inbox: ");
					System.out.println(message);
				}
			} else {
				System.out.println("Received message was adressed to another client, so it was sent further");
				sendMessage(soapMessage);
			}
		} catch (SOAPException | IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Message decodeSOAPMessage(SOAPMessage soapMessage) {
		try {
			Message msg;
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			String recipient = soapHeader.getAttribute(RECIPIENT);
			String sender = soapHeader.getAttribute(SENDER);
			SOAPBody soapBody = soapMessage.getSOAPBody();
			String msgText = soapBody.getAttribute(MESSAGE_TEXT);
			String castType = soapHeader.getAttribute(CAST_TYPE);
			if (castType.equals(UNICAST)) {
				msg = new Message(msgText, recipient, sender, false);
			} else {
				Set<String> reachedNodes = new HashSet<>();
				String nodes = soapHeader.getAttribute(REACHED_NODES);
				char[] nodesArray = nodes.toCharArray();
				for (char c : nodesArray) {
					String n = "" + c;
					reachedNodes.add(n);
				}
				msg = new Message(msgText, recipient, sender, true, reachedNodes);
			}
			return msg;
		} catch (SOAPException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SOAPMessage createSOAPMessage(Message message) {
		try {
			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
			SOAPFactory sf = SOAPFactory.newInstance();
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			Name recipientName = sf.createName(RECIPIENT);
			soapHeader.addAttribute(recipientName, message.getRecipient());
			Name senderName = sf.createName(SENDER);
			soapHeader.addAttribute(senderName, message.getSender());
			Name castTypeName = sf.createName(CAST_TYPE);
			if (message.isBroadcast()) {
				soapHeader.addAttribute(castTypeName, BROADCAST);
				Name reachedNodesName = sf.createName(REACHED_NODES);
				Set<String> reachedNodes = message.getReachedNodes();
				String nodes = "";
				for (String node : reachedNodes) {
					nodes = nodes.concat(node);
				}
				soapHeader.addAttribute(reachedNodesName, nodes);
			} else {
				soapHeader.addAttribute(castTypeName, UNICAST);
			}
			SOAPBody soapBody = soapMessage.getSOAPBody();
			Name bodyName = sf.createName(MESSAGE_TEXT);
			soapBody.addAttribute(bodyName, message.getText());

			return soapMessage;
		} catch (SOAPException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean connectSender() {
		try {
			sendingSocket = new Socket("localhost", appInfo.getNextNodePort());
			return true;
		} catch (IOException e) {
			System.out.println("Next node is unavailable");		
			return false;
		}
	}
	
	public void hostInbox() {
		try {
			inboxSocket = new ServerSocket(appInfo.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public List<Message> getInbox() {
		return inbox;
	}
	
	public void addIncomingMessage(Message msg) {
		inbox.add(msg);
	}

}
