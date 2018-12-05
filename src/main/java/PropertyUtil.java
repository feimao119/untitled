import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PropertyUtil {

    private static final Log log = LogFactory.getLog(PropertyUtil.class);

    //private static final Category log = Logger.getInstance(PropertyUtil.class);


    private Properties properties = new Properties();

    private String propertyFile = null;

    private long lastmodify;

    public PropertyUtil(String propertyFile) {
        this.propertyFile = propertyFile;
        this.loadFile();
    }

    private synchronized boolean loadFile() {
        boolean flag = false;
        File file = new File(this.propertyFile);
        if (!file.exists() || !file.isFile()) {
            log.error("property file [" + propertyFile + "] not exists");
            return flag;
        }
        FileInputStream fis = null;
        try {
            lastmodify = file.lastModified();
            fis = new FileInputStream(file);
            properties.clear();
            properties.load(fis);
            flag = true;
        } catch (IOException e) {
            log.error("加载[" + propertyFile + "]失败.", e);
        } catch (Exception e) {
            log.error("加载[" + propertyFile + "]失败.", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {

            }
        }
        return flag;
    }

    /**
     * 检测配置文件的最后修改时间，如有改动则重新加载配置文件
     *
     * @return boolean
     */
    private boolean checkFile() {
        boolean flag = false;
        File file = new File(this.propertyFile);
        if (!file.exists() || !file.isFile()) {
            return flag;
        }
        if (this.lastmodify != file.lastModified()) {
            flag = this.loadFile();
            log.debug("重新加载 [" + this.propertyFile + "] " + flag);
        } else {
            flag = true;
        }
        return flag;
    }

    public String getString(String key) {
        checkFile();
        String value = null;
        if (key != null) {
            value = properties.getProperty(key);
        }
        if (value != null) {
            try {
                value = new String(value.getBytes("ISO-8859-1"));
            } catch (Exception e) {
                log.error(this.propertyFile + " get by key=" + key, e);
            }
        }
        return value;
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        checkFile();
        String value = this.getString(key);
        return value == null ? defaultValue : value;
    }

    /**
     * get value of array from property file if not find value return array of
     * length is 0. (default a property tokens by ',' EX a,b,c)
     *
     * @param key
     * @return if not find array of length is 0
     */
    public String[] getStringArray(String key) {
        return getStringArray(key, ",");
    }

    /**
     * get value of array from property file if not find value return array of
     * length is 0.
     *
     * @param key
     * @param split
     * @return if not find array of length is 0
     */
    public String[] getStringArray(String key, String split) {
        String[] strArray = new String[0];
        checkFile();
        String value = this.getString(key);
        if (value != null && split != null) {
            strArray = value.split(split);
        }
        return strArray;
    }

    public Set getKeySet() {
        checkFile();
        return this.properties.keySet();
    }

    public static void main(String[] args){
        PropertyUtil prop = new PropertyUtil("d:/EbookFeePolicy.conf");
        System.out.println(prop.getString("feedesc"));
        System.out.println(prop.getString("unicomcontent"));
        System.out.println(prop.getString("wapfeeurl"));
    }
}
