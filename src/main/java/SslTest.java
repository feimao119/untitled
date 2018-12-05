import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;
import utils.SslUtils;

public class SslTest {

    public static String getRequest(String url,int timeOut){
        try {

            URL u = new URL(url);
            if("https".equalsIgnoreCase(u.getProtocol())){
                SslUtils.ignoreSsl();
            }
            URLConnection conn = u.openConnection();
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);
            return IOUtils.toString(conn.getInputStream(),"UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String getRequest(String url){
        return getRequest(url,30000);
    }

    public static String postRequest(String urlAddress,String args,int timeOut) throws Exception{
        URL url = new URL(urlAddress);
        if("https".equalsIgnoreCase(url.getProtocol())){
            SslUtils.ignoreSsl();
        }
        URLConnection u = url.openConnection();
        u.setDoInput(true);
        u.setDoOutput(true);
        u.setConnectTimeout(timeOut);
        u.setReadTimeout(timeOut);
        OutputStreamWriter osw = new OutputStreamWriter(u.getOutputStream(), "UTF-8");
        osw.write(args);
        osw.flush();
        osw.close();
        u.getOutputStream();
        return IOUtils.toString(u.getInputStream());
    }

    public static void main(String[] args) {
        try {
           // SslTest st = new SslTest();
            String a = getRequest("https://live.leisu.com/", 3000);
           // String a = getRequest("https://www.baidu.com/", 3000);
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
