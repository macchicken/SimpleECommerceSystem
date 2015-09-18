package commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

	private static Logger AUDIT;
	private static Logger ERROR;
	private static Logger TRACER;
	
	private static class LogUtilsHolder{
		private static final LogUtils INSTANCE=new LogUtils();
	}
	
	private LogUtils(){
		AUDIT = LogManager.getLogger("com.simleecs.tracelog");
		ERROR = LogManager.getLogger("com.simleecs.tracelog");
		TRACER = LogManager.getLogger("com.simleecs.tracelog");
	}
	
	public static LogUtils getInstance(){
		return LogUtilsHolder.INSTANCE;
	}
	
	
	public void audit(String message){
		AUDIT.info(message);
	}
	
	public void error(String message){
		ERROR.error(message);
	}
	
	public void trace(String message){
		TRACER.debug(message);
	}

}
