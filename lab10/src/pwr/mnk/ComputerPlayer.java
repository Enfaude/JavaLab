package pwr.mnk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ComputerPlayer {
	static String currentAlg = "";
	static String scriptsDirPath = ".\\scripts\\";
	static ArrayList<String> foundScripts = new ArrayList<>();
	static ArrayList<String> cppMethods = new ArrayList<>();
	static String cppRandom = "randomCpp";
	
	static {
		cppMethods.add(cppRandom);
	}
	
	static int[] playTurn(char[][] board) {
		if (currentAlg.endsWith(".js")) {
			try {
				ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
				engine.eval(new FileReader(scriptsDirPath+currentAlg));
				Invocable invocable = (Invocable) engine;		
				Object result = invocable.invokeFunction("solve", board);			
				int[] coords = (int[])result;
				return coords;
			} catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
				System.out.println("An error occured during loading algorithm's script, press '1' to try again, anything else to close the application.");
			    int choice = GameApp.forceIntegerInput();
			    if (choice == 1) {
			    	playTurn(board);
			    } else {
			    	System.exit(3);
			    }
				return null;
			}
		} else if (currentAlg.equals(cppRandom)) {
			int[] coords = CppPlayer.randomCpp(board);
			return coords;
		}
		return null;
	}
		
	static boolean scanAlgs() {
        File scriptsDir = new File(scriptsDirPath);
        foundScripts.clear();
        
        FilenameFilter jsFiles = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".js")) {
					return true;
				} else {
					return false;
				}
			}
		};
		
		File[] scripts = scriptsDir.listFiles(jsFiles);
		
		if (scripts.length == 0) {
		    System.out.println("There are no .js files in the scripts directory");
		} else {
		    System.out.println("Found scripts:");
		    for (File script : scripts) {
		        System.out.println(script.getName());
		        foundScripts.add(script.getName());
		    }
		}
		for (String alg : cppMethods) {
	        System.out.println(alg);
		}
		foundScripts.addAll(cppMethods);
        
		if (foundScripts.size() > 0) {
			return true;
		} else {
		    System.out.println("There are no .js or .cpp algorythms in memory, fix that and enter '1' to try again, anything else to close the application.");
		    int choice = GameApp.forceIntegerInput();
		    if (choice == 1) {
		    	scanAlgs();
		    } else {
		    	System.exit(4);
		    }
		    return false;
		}
	}
	
	static void selectAlg() {
	    System.out.println("Select AI script with a number");

		for (int i = 0; i < foundScripts.size(); i++) {			
			System.out.println(i + ". " + foundScripts.get(i));			
		}
		int choice = GameApp.forceIntegerInput();
		try {
			currentAlg = foundScripts.get(choice);
		} catch (IndexOutOfBoundsException e) {
		    System.out.println("Error! You've selected unavailable index of the list! Try again.");
		    selectAlg();
		}
	}
	
}
