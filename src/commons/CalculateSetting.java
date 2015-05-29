package commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.mysql.jdbc.StringUtils;

/**
 * Singleton bean of storing settings for calculation service
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class CalculateSetting {

	private static Map<String,String> settings=new HashMap<String,String>();

	private static class SettingHolder{
		private static final CalculateSetting INSTANCE=new CalculateSetting();
	}
	
	/**
	 * private constructor used in this Singlton
	 */
	private CalculateSetting(){
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/shippingcost.properties"));
			Iterator<Entry<Object, Object>>  it= properties.entrySet().iterator();
			while(it.hasNext()){
				Entry<Object, Object> t=it.next();
				String city=(String) t.getKey();
				String cost=(String) t.getValue();
				settings.put(city, cost);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static CalculateSetting getInstance(){
		return SettingHolder.INSTANCE;
	}
	
	/**
	 * Return a string of per item shipping cost and delivery cost of a city
	 * 
	 * @param city
	 * @return
	 */
	public String getCost(String city){
		if (StringUtils.isNullOrEmpty(city)){return null;}
		city=city.toLowerCase();
		return settings.get(city);
	}
	
}
