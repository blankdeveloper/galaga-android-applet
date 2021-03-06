package com.gamefreezer.galaga;

import java.io.InputStream;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyProperties {

    private final Properties properties = new Properties();
    private AbstractColor colorDecoder;

    // TODO this is ugly - either pass a full set of Abstract tools or none, and
    // be consistent
    public MyProperties(InputStream in, AbstractColor colorDecoder) {
	this.colorDecoder = colorDecoder;
	readFromInputStream(in);
    }

    public MyProperties(InputStream in) {
	readFromInputStream(in);
    }

    private void readFromInputStream(InputStream in) {
	try {
	    properties.load(in);
	    in.close();
	} catch (Exception e) {
	    throw new IllegalArgumentException("Cannot read input stream " + e);
	}
    }

    // provide wrappers to all public Properties methods I currently use

    public int size() {
	return properties.size();
    }

    public boolean containsKey(String key) {
	return properties.containsKey(key);
    }

    public Properties getProperties() {
	return properties;
    }

    // provide extended property methods here

    public int getInt(String key, int defaultValue) {
	return Integer.parseInt(properties.getProperty(key, Integer
		.toString(defaultValue)));
    }

    public int getInt(String key) {
	if (properties.containsKey(key)) {
	    return Integer.parseInt(properties.getProperty(key));
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public float getFloat(String key) {
	if (properties.containsKey(key)) {
	    return Float.parseFloat(properties.getProperty(key));
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public String getString(String key) {
	if (properties.containsKey(key)) {
	    return properties.getProperty(key);
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public String getString(String key, String defaultValue) {
	return properties.getProperty(key, defaultValue);
    }

    // return array of space delimited strings
    public String[] getStringArray(String key) {
	if (properties.containsKey(key)) {
	    return (properties.getProperty(key)).split(" ");
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public String[] getStringArray(String key, String defaultValue) {
	if (properties.containsKey(key)) {
	    return getStringArray(key);
	}
	return defaultValue.split(" ");
    }

    // return array of ints from space delimited string of ints
    public int[] getIntArray(String key) {
	if (properties.containsKey(key)) {
	    return stringToIntArray(properties.getProperty(key));
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public int[] getIntArray(String key, String defaultValue) {
	if (properties.containsKey(key)) {
	    return getIntArray(key);
	}
	return stringToIntArray(defaultValue);
    }

    private int[] stringToIntArray(String line) {
	String[] entries = line.split(" ");
	int[] theInts = new int[entries.length];
	for (int i = 0; i < entries.length; i++) {
	    try {
		theInts[i] = Integer.parseInt(entries[i]);
	    } catch (NumberFormatException e) {
		assert false : "Bad number! line: " + line + "entries[i]: "
			+ entries[i] + " entries.length: " + entries.length;
	    }
	}
	return theInts;
    }

    public AbstractColor getColor(String key) {
	if (properties.containsKey(key)) {
	    return colorDecoder.decode(properties.getProperty(key));
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public SortedMap<Integer, Integer> getSortedMap(String key) {
	if (properties.containsKey(key)) {
	    return parseSortedMap(properties.getProperty(key));
	}
	throw new IllegalArgumentException("Key doesn't exist: " + key);
    }

    public SortedMap<Integer, Integer> getSortedMap(String key,
	    SortedMap<Integer, Integer> defaultValue) {
	if (properties.containsKey(key)) {
	    return parseSortedMap(properties.getProperty(key));
	}
	return defaultValue;
    }

    public SortedMap<Integer, Integer> parseSortedMap(String line) {
	// if not there, return default speeds here
	SortedMap<Integer, Integer> smap = new TreeMap<Integer, Integer>();
	if (line.equals("")) {
	    assert false : "cannot parse line: " + line;
	    return smap;
	}
	String[] entries = line.split(" ");
	for (String entry : entries) {
	    String[] kv = entry.split(":");
	    int key = Integer.parseInt(kv[0]);
	    int value = Integer.parseInt(kv[1]);
	    smap.put(key, value);
	}
	fillMap(smap);
	return smap;
    }

    public void fillMap(SortedMap<Integer, Integer> smap) {
	int val = 1;
	for (int x = 100; x > 0; --x) {
	    if (smap.containsKey(x)) {
		val = smap.get(x);
	    } else {
		smap.put(x, val);
	    }
	}
    }

    @Override
    public String toString() {
	return properties.toString();
    }
}
