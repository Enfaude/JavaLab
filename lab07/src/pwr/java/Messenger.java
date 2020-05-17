package pwr.java;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	public Messenger(AppInfo appInfo) {
		this.appInfo = appInfo;
		hostInbox();
	}

	public boolean sendMessage(Message message) throws SOAPException, IOException {
		SOAPMessage soapMessage = createSOAPMessage(message);
		return sendMessage(soapMessage);
	}
	
	public boolean sendMessage(SOAPMessage soapMessage) throws SOAPException, IOException {
		if (soapMessage != null) {
			connectSender();
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
		} else {
			return false;
		}
	}

	public void checkMessage(SOAPMessage soapMessage) throws SOAPException, IOException {
		try {
			SOAPHeader header = soapMessage.getSOAPHeader();
			String recipient = header.getAttribute("recipient");
			String castType = header.getAttribute("castType");
			if (recipient.equals(appInfo.getName())) {
				System.out.println("Received new message adressed to this client");
				Message message = decodeSOAPMessage(soapMessage);
				if (message != null) {
					inbox.add(message);
					System.out.println("You have new message in inbox: ");
					System.out.println(message);
				}
			} else if (castType.equals("broadcast")) {
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
			String recipient = soapHeader.getAttribute("recipient");
			String sender = soapHeader.getAttribute("sender");
			SOAPBody soapBody = soapMessage.getSOAPBody();
			String msgText = soapBody.getAttribute("message");
			String castType = soapHeader.getAttribute("castType");
			if (castType.equals("unicast")) {
				msg = new Message(msgText, recipient, sender, false);
			} else {
				Set<String> reachedNodes = new HashSet<>();
				String nodes = soapHeader.getAttribute("reachedNodes");
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
			Name recipientName = sf.createName("recipient");
			soapHeader.addAttribute(recipientName, message.getRecipient());
			Name senderName = sf.createName("sender");
			soapHeader.addAttribute(senderName, message.getSender());
			Name castTypeName = sf.createName("castType");
			if (message.isBroadcast()) {
				soapHeader.addAttribute(castTypeName, "broadcast");
				Name reachedNodesName = sf.createName("reachedNodes");
				Set<String> reachedNodes = message.getReachedNodes();
				String nodes = "";
				for (String node : reachedNodes) {
					nodes = nodes.concat(node);
				}
				soapHeader.addAttribute(reachedNodesName, nodes);
			} else {
				soapHeader.addAttribute(castTypeName, "unicast");
			}
			SOAPBody soapBody = soapMessage.getSOAPBody();
			Name bodyName = sf.createName("message");
			soapBody.addAttribute(bodyName, message.getText());

			return soapMessage;
		} catch (SOAPException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void connectSender() {
		try {
			sendingSocket = new Socket("localhost", appInfo.getNextNodePort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void hostInbox() {
		try {
			inboxSocket = new ServerSocket(appInfo.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SOAPMessage refreshInbox() throws IOException, SOAPException {
		Socket inputSocket;
		System.out.println("Refreshing inbox...");
		try {
			inputSocket = inboxSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
			String input = in.readLine();
			InputStream is = new ByteArrayInputStream(input.getBytes());
			SOAPMessage msg = MessageFactory.newInstance().createMessage(null, is);
			checkMessage(msg);
			System.out.println("...refreshed successfully");
			return msg;
		} catch (IOException | SOAPException e) {
			System.out.println("...it failed!");
			e.printStackTrace();
			throw e;
		}		
	}
	
	public List<Message> getInbox() {
		return inbox;
	}

}
