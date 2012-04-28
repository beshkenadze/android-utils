package net.beshkenadze.android.utils.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MyLogger {
	public static String TAG = "MyLogger";
	private static Logger log = Logger.getLogger(TAG);
	public static Level LEVEL = Level.DEBUG;

	static {
		configure();
	}

	public static void configure() {
		final LogConfigurator logConfigurator = new LogConfigurator();
		logConfigurator.setRootLevel(LEVEL);
		// Set log level of a specific logger
		// logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setUseFileAppender(false);
		logConfigurator.configure();
	}

	public static String getCallableInfo(Throwable t) {
		StackTraceElement[] trace = t.getStackTrace();
		String className = trace[1].getClassName();
		String methodName = trace[1].getMethodName();
		int lineNumber = trace[1].getLineNumber();
		return className + ":" + methodName + ":" + lineNumber + " ";
	}

	public static void d(String message) {
		log.debug(getCallableInfo(new Throwable()) + message);
	}

	public static void i(String message) {
		log.info(getCallableInfo(new Throwable()) + message);
	}

	public static void e(String message) {
		log.error(getCallableInfo(new Throwable()) + message);
	}

	public static void debug(String message) {
		log.debug(getCallableInfo(new Throwable()) + message);
	}

	public static void info(String message) {
		log.info(getCallableInfo(new Throwable()) + message);
	}

	public static void error(String message) {
		log.error(getCallableInfo(new Throwable()) + message);
	}

}
