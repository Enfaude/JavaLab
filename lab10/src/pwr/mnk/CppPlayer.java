package pwr.mnk;

public class CppPlayer {
	
	static {
//		System.load("D:\\IntelliJWorkspace\\lab10\\src\\pwr\\mnk\\native.dll");
		System.loadLibrary("native");
	}
	
	static native int[] randomCpp(char[][] board);
}
