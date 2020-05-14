package pwr.java;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class App {
	static Connection conn;
	static String selectClientsSQL = "select * from MySQL80Lab06.clients";
	static String selectTransactionsSQL = "select * from MySQL80Lab06.transactions";
	static String selectTransactionByIdSQL = "select * from MySQL80Lab06.transactions where id = ?";
	static String updateClientNameSQL = "update MySQL80Lab06.clients set name = ? where id = ?";
	static String updateClientPhoneSQL = "update MySQL80Lab06.clients set phone = ? where id = ?";
	static String updateTransactionClientSQL = "update MySQL80Lab06.transactions set client = ? where id = ?";
	static String updateTransactionBillSQL = "update MySQL80Lab06.transactions set bill = ? where id = ?";
	static String selectTransactionsWithClientsDataSQL = "select t.id, c.name, t. bill, c.phone from MySQL80Lab06.transactions t join MySQL80Lab06.clients c on t.client = c.id";
	static String insertTransactionSQL = "INSERT INTO `mysql80lab06`.`transactions` (`id`, `client`, `bill`) VALUES (?, ?, ?);";
	static String insertClientSQL = "INSERT INTO `mysql80lab06`.`clients` (`id`, `name`, `phone`) VALUES ('0', ?, ?);" ;
	

	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://localhost:3306/MySQL80Lab06?serverTimezone=UTC";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);  
			System.out.println("Connected!");
			menu();
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
        } 
	}
	
	static void menu() throws SQLException {
		try {
			while (true) {
				System.out.println("Menu");
				System.out.println("1. View selected table");
				System.out.println("2. View combined data");
				System.out.println("3. Add new record");
				System.out.println("4. Select and update record");
				System.out.println("5. Export selected transaction to XML file");
				System.out.println("6. Import transaction from XML file");
				System.out.println("7. Export all transactions to XML file");
				System.out.println("8. Import multiple transactions from XML file");
				System.out.println("0. Close application");
				
				String table = "";
				Scanner sc = new Scanner(System.in);
				ResultSet rs = null;
				TransactionManager tm = new TransactionManager();
				List<Transaction> list = null;
				TransactionsList trList = null;
				Transaction transaction = null;
				int choice = forceIntegerInput();
				switch (choice) {
					case 1: 
						printTableNames();
						System.out.println("Which table do you want to view?");
						table = sc.nextLine();
						table = table.trim();
						rs = selectData(table);
						printColumnNames(rs);
						printResult(rs);
						break;
					case 2: 
						rs = selectView();
						printColumnNames(rs);
						printResult(rs);
						break;
					case 3: 
						printTableNames();
						System.out.println("In which table do you want to add new record?");
						table = sc.nextLine();
						table = table.trim();
						insertData(table);
						break;
					case 4: 
						printTableNames();
						System.out.println("In which table do you want to edit record?");
						table = sc.nextLine();
						table = table.trim();
						updateData(table);
						break;
					case 5:
						System.out.println("Enter id of exported transaction");
						int trid = sc.nextInt();
						transaction = selectTransaction(trid);
						tm.marshallTransaction(transaction);
						break;
					case 6:
						transaction = tm.unmarshallTransaction();
						insertTransaction(transaction);
						break;	
					case 7:
						rs = selectData("transactions");
						list = tm.getTransactions(rs);
						trList = new TransactionsList();
						trList.setTransationsList(list);
						tm.marshallTransactions(trList);
						break;
					case 8:
						trList = tm.unmarshallTransactions();
						if (trList != null) {
							list = trList.getTransactionsList();
							int c = 0;
							for (Transaction tr : list) {
								insertTransaction(tr);
								c++;
							}
							System.out.println("Successfully imported " + c + " transactions");
						}
						break;
					case 0:
						System.out.println("Closing application");
						sc.close();
						try {
							conn.close();
							System.exit(0);
						} catch (SQLException e) {
							System.out.println("Couldn't close database connection properly");
							System.exit(-1);
						}
				}
			}
		} catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        menu();
        } 
	}
	
	static ResultSet selectData(String table) throws SQLException{
		Statement stmt = conn.createStatement();		
		ResultSet rs = null;
		if (table.toLowerCase().equals("transactions")) {
			rs = stmt.executeQuery(selectTransactionsSQL);
		} else if (table.toLowerCase().equals("clients")) {
			rs = stmt.executeQuery(selectClientsSQL);
		} else {
	        System.out.println("There is no such table");
		}
		return rs;
	}
	
	static void updateData(String table) throws SQLException {
		Statement stmt = conn.createStatement();		
		ResultSet rs = null;		
		if (table.toLowerCase().equals("clients")) {
			rs = stmt.executeQuery(selectClientsSQL);
			printColumnNames(rs);
			stmt.close();
			updateClient();
		} else if (table.toLowerCase().equals("transactions")) {
			rs = stmt.executeQuery(selectTransactionsSQL);
			printColumnNames(rs);
			stmt.close();
			updateTransaction();
		} else {
	        System.out.println("There is no such table");
		}		 
	}
	
	static boolean insertData(String table) throws SQLException {
		if (table.toLowerCase().equals("transactions")) {
			insertTransaction();
		} else if (table.toLowerCase().equals("clients")) {
			insertClient();
		} else {
	        System.out.println("There is no such table");
	        return false;
		}
		return true;			
	}
	
	static void printResult(ResultSet rs) throws SQLException {
		if (rs != null) {
			ResultSetMetaData rsmt = rs.getMetaData();
			int columnCount = rsmt.getColumnCount();
			while(rs.next()) {
				String outputLine = "";
				for (int i = 1; i <= columnCount; i++) {
					outputLine = outputLine.concat(" | " + rs.getString(i));
				}
				System.out.println(outputLine);  
			}
		}
	}
	
	static void printColumnNames(ResultSet rs) throws SQLException {
		if (rs != null) {
			ResultSetMetaData rsmt = rs.getMetaData();
			String columnNames = "";
			int columnCount = rsmt.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnNames = columnNames.concat(" | " + rsmt.getColumnName(i));
			}
			System.out.println(columnNames);
		}
	}
	
	static void printTableNames() throws SQLException {
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(conn.getCatalog(), null, "%", null);
		String tables = "Currently available tables: ";
		while (rs.next()) {
			tables = tables.concat(rs.getString(3).concat(", "));
		}
        System.out.println(tables);
	}
	
	static boolean updateClient() throws SQLException {
		PreparedStatement stmt = null;		
		System.out.println("Which field do you want to update?");
		Scanner sc = new Scanner(System.in);
		String fieldName = sc.nextLine();
		fieldName = fieldName.trim();
		
		if(fieldName.toLowerCase().equals("name")) {
			stmt = conn.prepareStatement(updateClientNameSQL);		
		} else if(fieldName.toLowerCase().equals("phone")) {
			stmt = conn.prepareStatement(updateClientPhoneSQL);
		} else if(fieldName.toLowerCase().equals("id")) {
			System.out.println("You cannot change this value!");
			return false;
		} else {
			System.out.println("There is no such field!");
			return false;
		}
		
		System.out.println("Enter new value of " + fieldName);
		String value = sc.nextLine();
		stmt.setString(1, value);
		
		System.out.println("Enter id of edited record ");
		int id = forceIntegerInput();
		stmt.setInt(2, id);
		
		stmt.executeUpdate();
		System.out.println("Record updated successfully");

		return true;
	}
	
	static boolean updateTransaction() throws SQLException {
		PreparedStatement stmt = null;		
		System.out.println("Which field do you want to update?");
		Scanner sc = new Scanner(System.in);
		String fieldName = sc.nextLine();
		fieldName = fieldName.trim();

		if(fieldName.toLowerCase().equals("client")) {
			stmt = conn.prepareStatement(updateTransactionClientSQL);		
		} else if(fieldName.toLowerCase().equals("bill")) {
			stmt = conn.prepareStatement(updateTransactionBillSQL);
		} else if(fieldName.toLowerCase().equals("id")) {
			System.out.println("You cannot change this value!");
			return false;
		} else {
			System.out.println("There is no such field!");
			return false;
		}
		
		System.out.println("Enter new value of " + fieldName);
		int value = forceIntegerInput();
		stmt.setInt(1, value);
		
		System.out.println("Enter id of edited record ");
		int id= forceIntegerInput();
		stmt.setInt(2, id);
		
		stmt.executeUpdate();
		System.out.println("Record updated successfully");

		return true;
	}
	
	static void insertClient() throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(insertClientSQL);
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter new client's name");
		String name = sc.nextLine();
		stmt.setString(1, name);
		
		System.out.println("Enter new client's phone number");
		String phone = sc.nextLine();
		stmt.setString(2, phone);
		
		stmt.executeUpdate();
		System.out.println("Data added successfully!");
	}
	
	static void insertTransaction() throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(insertTransactionSQL);
		Scanner sc = new Scanner(System.in);
		
		//id will be set as next auto_increment in database
		stmt.setInt(1, 0);

		System.out.println("Enter transaction's client ID");
		int client = sc.nextInt();
		stmt.setInt(2, client);
		
		System.out.println("Enter new transaction's bill");
		int bill = sc.nextInt();
		stmt.setInt(3, bill);
		
		stmt.executeUpdate();
		System.out.println("Data added successfully!");
	}
	
	static void insertTransaction(Transaction transaction) throws SQLException {
		if (transaction != null) {
			PreparedStatement stmt = conn.prepareStatement(insertTransactionSQL);
			stmt.setInt(1, transaction.getId());
			stmt.setInt(2, transaction.getClient());
			stmt.setInt(3, transaction.getBill());
			
			stmt.executeUpdate();
			System.out.println("Data added successfully!");
		} else {
			System.out.println("Error! Selected transaction is a null object!");
		}
	}
	
	static int forceIntegerInput() {
		Scanner sc = new Scanner(System.in);
		int value;
		try { 
			value = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Please enter correct numeric value");
			value = forceIntegerInput();
		}
		return value;
	}
	
	static ResultSet selectView() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectTransactionsWithClientsDataSQL);
		return rs;
	}
	
	static Transaction selectTransaction(int id) throws SQLException {
		Transaction transaction = null;
		PreparedStatement stmt = conn.prepareStatement(selectTransactionByIdSQL);		
		stmt.setInt(1, id);		
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			transaction = new Transaction(rs.getInt(1), rs.getInt(2), rs.getInt(3));
		}
		return transaction;
	}
	

}
