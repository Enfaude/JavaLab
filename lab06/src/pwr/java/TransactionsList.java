package pwr.java;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transactionsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionsList {
	
	@XmlElement(name = "transaction")
	List<Transaction> trList;
		
	public List<Transaction> getTransactionsList() {
		return trList;
	}
	
	public void setTransationsList(List<Transaction> trList) {
		this.trList = trList;
	}

}
