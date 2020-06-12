package pwr.mnk;

public class CppPlayer {
	
	static {
		System.loadLibrary("native");
	}
	
	static native int[] randomCpp(char[][] board);
}
