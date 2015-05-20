package commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.mysql.jdbc.StringUtils;


public class CalculateSetting {

	private static Map<String,String> settings=new HashMap<String,String>();

	private static class SettingHolder{
		private static final CalculateSetting INSTANCE=new CalculateSetting();
	}
	
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
	
	public String getCost(String city){
		if (StringUtils.isNullOrEmpty(city)){return null;}
		city=city.toLowerCase();
		return settings.get(city);
	}
	
}
