package pwr.java;

public class Client {
	
	public int id;
	public String name; 
	public String phone;
	
	public Client() {
		
	}
	
	public Client(int newId, String newName, String newPhone) {
		this.id = newId;
		this.name = newName;
		this.phone = newPhone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
