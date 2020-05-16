package pwr.java;

public class MessengerAppA {

	public static void main(String[] args) {
		AppInfo appInfo = new AppInfo("A", "B", 8881, 8882);
		Messenger messenger = new Messenger(appInfo);
		AppMenu menu = new AppMenu(appInfo, messenger);
		menu.menu();
	}
	
}
