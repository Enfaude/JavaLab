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
import java.util.List;

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

	public boolean sendMessage(Message message) {
		SOAPMessage soapMessage = createSOAPMessage(message);
		return sendMessage(soapMessage);
	}
	
	public boolean sendMessage(SOAPMessage soapMessage) {
		connectSender();
		try {
	        PrintStream out = new PrintStream(sendingSocket.getOutputStream(), true);
			soapMessage.writeTo(out);
			out.close();
			return true;
		} catch (IOException e) {
			sendMessage(soapMessage);
			e.printStackTrace();
			return false;
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

	public void checkMessage(SOAPMessage soapMessage) throws SOAPException {
		try {
			SOAPHeader header = soapMessage.getSOAPHeader();
			String recipient = header.getAttribute("recipient");
			if (recipient.equals(appInfo.getName())) {
				System.out.println("Received new message adressed to this client");
				Message message = decodeSOAPMessage(soapMessage);
				if (message != null) {
					inbox.add(message);
					System.out.println("You have new message in inbox: ");
					System.out.println(message);
				}
			} else {
				System.out.println("Received message was adressed to another client, so it was sent further");
				sendMessage(soapMessage);
			}
		} catch (SOAPException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Message decodeSOAPMessage(SOAPMessage soapMessage) {
		try {
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			String recipient = soapHeader.getAttribute("recipient");
			String sender = soapHeader.getAttribute("sender");
			SOAPBody soapBody = soapMessage.getSOAPBody();
			String msgText = soapBody.getAttribute("text");
			Message msg = new Message(msgText, recipient, sender);
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
