package pwr.java;

public class MessengerAppD {

	public static void main(String[] args) {
		AppInfo appInfo = new AppInfo("D", "A", 8884, 8881);
		Messenger messenger = new Messenger(appInfo);
		AppMenu menu = new AppMenu(appInfo, messenger);
		menu.menu();
	}
	
}
