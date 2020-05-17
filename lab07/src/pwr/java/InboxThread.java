package pwr.java;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class InboxThread extends Thread {
	Messenger messenger;

	public InboxThread (Messenger messenger) {
		this.messenger = messenger;
	}
	
	@Override
	public void run() {
		System.out.println("Starting inbox socket...");
		Socket inputSocket;
		while(true) {
			try {
				inputSocket = messenger.inboxSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
				String input = in.readLine();
				InputStream is = new ByteArrayInputStream(input.getBytes());
				SOAPMessage msg = MessageFactory.newInstance().createMessage(null, is);
				messenger.checkMessage(msg);
				System.out.println();
			} catch (IOException | SOAPException e) {
				System.out.println("Error in inbox thread");
			}		
		}
	}
}
