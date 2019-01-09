package edu.kit.aquaplanning.util;

public class Logger {

	public static final int INFO_VV = 4;
	public static final int INFO_V = 3;
	public static final int INFO = 2;
	public static final int WARN = 1;
	public static final int ERROR = 0;
	public static final int ESSENTIAL = -1;
	
	private static int verbosity;
	
	public static void init(int verbosityLevel) {
		Logger.verbosity = verbosityLevel;
	}
	
	public static void log(int verbosityLevel, String msg) {
		if (verbosityLevel <= verbosity) {
			System.out.println(msg);
		}
	}
}
