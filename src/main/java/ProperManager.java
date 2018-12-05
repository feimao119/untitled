import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ProperManager {
	
	private static final ResourceBundle RESOURCE_BUNDLE =ResourceBundle.getBundle("d:\\elog\\conf.properties");
	
	/**
	 * 读取默认的config.properties文件
	 * @param key 
	 * @return value
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			throw new RuntimeException( "! config : "+ key + '!');
		}
	} 
}
