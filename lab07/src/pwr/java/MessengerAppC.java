package pwr.java;

public class MessengerAppC {

	public static void main(String[] args) {
		AppInfo appInfo = new AppInfo("C", "A", 8883, 8881);
		Messenger messenger = new Messenger(appInfo);
		AppMenu menu = new AppMenu(appInfo, messenger);
		menu.menu();
	}
	
}
