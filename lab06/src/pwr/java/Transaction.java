package pwr.java;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
	
	public int id;
	public int client;
	public int bill;
	
	public Transaction() {
		
	}		
	
	public Transaction(int newId, int newClient, int newBill) {
		this.id = newId;
		this.client = newClient;
		this.bill = newBill;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}
	
	@Override
 	public String toString() {
 		return "Transaction info: id = " + id 
 				+ ", clientId = " + client 
 				+ ", bill = " + bill;
 	}
}
