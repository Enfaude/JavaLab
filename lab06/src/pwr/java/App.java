package pwr.java;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.sql.ResultSetMetaData;


public class App {
	static Connection conn;
	static String selectClientsSQL = "select * from MySQL80Lab06.clients";
	static String selectTransactionsSQL = "select * from MySQL80Lab06.transactions";
	static String updateClientNameSQL = "update MySQL80Lab06.clients set name = ? where id = ?";
	static String updateClientPhoneSQL = "update MySQL80Lab06.clients set phone = ? where id = ?";
	static String updateTransactionClientSQL = "update MySQL80Lab06.transactions set client = ? where id = ?";
	static String updateTransactionBillSQL = "update MySQL80Lab06.transactions set bill = ? where id = ?";
	static String selectTransactionsWithClientsDataSQL = "select t.id, c.name, t. bill, c.phone from MySQL80Lab06.transactions t join MySQL80Lab06.clients c on t.client = c.id";
	static String insertTransactionSQL = "INSERT INTO `mysql80lab06`.`transactions` (`id`, `client`, `bill`) VALUES ('0', ?, ?);";
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
				System.out.println("0. Close application");
				
				String table = "";
				Scanner sc = new Scanner(System.in);
				int choice = forceIntegerInput();
				switch (choice) {
					case 1: 
						printTableNames();
						System.out.println("Which table do you want to view?");
						table = sc.nextLine();
						table = table.trim();
						selectData(table);
						break;
					case 2: 
						selectView();
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
	
	static void selectData(String table) throws SQLException{
		Statement stmt = conn.createStatement();		
		ResultSet rs = null;
		if (table.toLowerCase().equals("transactions")) {
			rs = stmt.executeQuery(selectTransactionsSQL);
		} else if (table.toLowerCase().equals("clients")) {
			rs = stmt.executeQuery(selectClientsSQL);
		} else {
	        System.out.println("There is no such table");
		}
		printColumnNames(rs);
		printResult(rs);
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

		System.out.println("Enter transaction's client ID");
		int client = sc.nextInt();
		stmt.setInt(1, client);
		
		System.out.println("Enter new transaction's bill");
		int bill= sc.nextInt();
		stmt.setInt(2, bill);
		
		stmt.executeUpdate();
		System.out.println("Data added successfully!");
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
	
	static void selectView() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectTransactionsWithClientsDataSQL);
		printColumnNames(rs);
		printResult(rs);
	}

}
