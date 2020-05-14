package pwr.java;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TransactionManager {
	
	public TransactionManager() {
	}
	
	void marshallTransaction(Transaction transaction) {
		if (transaction != null) {
			try {
				JAXBContext ctx = JAXBContext.newInstance(Transaction.class);
		 		Marshaller marshaller = ctx.createMarshaller();
		 		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 		File expDir = new File("export");
		 		expDir.mkdir();
		 		File xmlFile = new File(expDir.getName() + "\\transaction" + transaction.getId() + ".xml");
		 		marshaller.marshal(transaction, xmlFile);
		 		System.out.println("Successfully marshalled transaction object to file: " + xmlFile.getAbsolutePath());
			} catch (JAXBException ex) {
				System.out.println("There was an error during marshalling");
				ex.printStackTrace();
			}
		}
	}
	
	void marshallTransactions(TransactionsList transactions) {
		if (transactions != null) {
			try {
				JAXBContext ctx = JAXBContext.newInstance(TransactionsList.class);
		 		Marshaller marshaller = ctx.createMarshaller();
		 		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 		File expDir = new File("export");
		 		expDir.mkdir();
		 		File xmlFile = new File(expDir.getName() + "\\transactions.xml");
		 		marshaller.marshal(transactions, xmlFile);
		 		System.out.println("Successfully marshalled transactions list to file: " + xmlFile.getAbsolutePath());
			} catch (JAXBException ex) {
				System.out.println("There was an error during marshalling");
				ex.printStackTrace();
			}
		}
	}
	
	Transaction unmarshallTransaction() {
		System.out.println("Select file to import");
		File file = openFile();
		Transaction transaction = null;
		if (file != null) {
			try {
				JAXBContext ctx = JAXBContext.newInstance(Transaction.class);
		 		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		 		transaction = (Transaction) unmarshaller.unmarshal(file);
		 		System.out.println("Successfully unmarshalled transaction object from file");
		 		System.out.println(transaction);
			} catch (JAXBException ex) {
				System.out.println("There was an error during unmarshalling");
				ex.printStackTrace();
			}
		}
		return transaction;
	}
	
	TransactionsList unmarshallTransactions() {
		System.out.println("Select file to import");
		File file = openFile();
		TransactionsList transactions = null;
		if (file != null) {
			try {
				JAXBContext ctx = JAXBContext.newInstance(TransactionsList.class);
		 		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		 		transactions = (TransactionsList) unmarshaller.unmarshal(file);
		 		System.out.println("Successfully unmarshalled transaction object from file");
			} catch (JAXBException ex) {
				System.out.println("There was an error during unmarshalling");
				ex.printStackTrace();
			}
		} else {
			System.out.println("No file file selected!");
		}
		return transactions;
	}
	
	List<Transaction> getTransactions(ResultSet rs) throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<>();

		if (rs != null) {
			while(rs.next()) {
				Transaction transaction = new Transaction(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				transactions.add(transaction);
				System.out.println(transaction);  
			}
		}
		
		return transactions;
	}
	
	public static File openFile() {
		File file = null;		
		JFileChooser chooser = new JFileChooser();		
		chooser.setDialogTitle("Load XML File");		
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {		
		    file = chooser.getSelectedFile();	
		}
		
		return file;
	}
	
	

}
