package pwr.java;

public class MessengerAppC {

	public static void main(String[] args) {
		AppInfo appInfo = new AppInfo("C", "D", 8883, 8884);
		Messenger messenger = new Messenger(appInfo);
		AppMenu menu = new AppMenu(appInfo, messenger);
		menu.menu();
	}
	
}
