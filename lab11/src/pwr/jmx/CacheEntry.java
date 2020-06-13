package pwr.jmx;

import java.util.List;

import pwr.java.IntElement;

public class CacheEntry {
	int seed;
	List<IntElement> results;
	
	public CacheEntry(int seed, List<IntElement> results) {
		this.seed = seed;
		this.results = results;
	}
	
	public int getSeed() {
		return this.seed;
	}
	
}
