package pwr.java;

public class MessengerAppB {

	public static void main(String[] args) {
		AppInfo appInfo = new AppInfo("B", "C", 8882, 8883);
		Messenger messenger = new Messenger(appInfo);
		AppMenu menu = new AppMenu(appInfo, messenger);
		menu.menu();
	}
	
}
