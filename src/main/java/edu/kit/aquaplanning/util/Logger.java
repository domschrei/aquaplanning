package edu.kit.aquaplanning.util;

import java.util.Locale;

public class Logger {

	public static final int INFO_VV = 4;
	public static final int INFO_V = 3;
	public static final int INFO = 2;
	public static final int WARN = 1;
	public static final int ERROR = 0;
	public static final int ESSENTIAL = -1;

	private static int verbosity;
	private static long startTime = System.nanoTime();

	public static void init(int verbosityLevel) {
		Logger.verbosity = verbosityLevel;
	}

	public static void log(int verbosityLevel, String msg) {
		if (verbosityLevel <= verbosity) {
			long time = System.nanoTime() - startTime;
			System.out.println(String.format(Locale.ENGLISH, "[%.3f] ", time / 1e9f) + msg);
		}
	}
}
