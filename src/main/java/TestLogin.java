import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-15
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class TestLogin {
    public static void main(String[] args){
      // String ss = HttpClientUtils.get("https://www.e6bet.net/touzhu/FT_Browser/FT_Roll.aspx");
     //  System.out.println(ss);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("money","100");
        parameters.put("ChipIn_Acc","feimao119");
        parameters.put("ChipIn_MatchID","1513292");
        parameters.put("ChipIn_PL","1.210");
        parameters.put("ChipIn_PK","*0");
        parameters.put("ChipIn_Name","西班牙盃");
        parameters.put("Match_Date","10-16");
        parameters.put("ChipIn_Master","圖德拉諾");
        parameters.put("ChipIn_Guest","FC卡塔捷拿");
        parameters.put("ChipIn_Type","3");
        parameters.put("ChipIn_dType","111");
        parameters.put("User_Money","4087.01");
        parameters.put("User_Max","20000");
        parameters.put("User_Min","10");
        parameters.put("Count_Max","400000");
        parameters.put("SumChipIn_Money","0.00");
        parameters.put("ChipInedName","");
        parameters.put("Match_NowScore","0:0");
        parameters.put("Match_CoverDate","10/16/2013 10:30:00 PM");
        parameters.put("Chipin_replace","true");

        String s2 = HttpClientUtils.postQuietly("https://www.e6bet.net/Member/ChipIn/Bet_ChipIn/ChipIn_GQQcrq.aspx?id=1513292&Type=H&521112",parameters);
        System.out.println(s2);
      // getFT_Roll();
    }


    public static void getFT_Roll(){
        try {
            String dataUrl = "https://www.e6bet.net/touzhu/FT_Browser/FT_Roll.aspx?p=1&t=163&name=";

            HttpClient httpClient = new HttpClient();

            //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
            GetMethod postMethod = new GetMethod(dataUrl);

            postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            postMethod.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
            postMethod.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
            postMethod.setRequestHeader("Connection","keep-alive");
            postMethod.setRequestHeader("Cookie","ASP.NET_SessionId=pdhxdu55jglfa245rz1bkn55; __utma=1.779128577.1374854966.1381920022.1381927322.147; __utmb=1.108.10.1381927322; __utmc=1; __utmz=1.1374854966.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
            postMethod.setRequestHeader("Host","www.e6bet.net");
            postMethod.setRequestHeader("Referer","https://www.e6bet.net/Member/ChipIn/Bet_ChipIn/ChipIn_GQQcrq.aspx?id=1513286&Type=H&521345");
            postMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
          //  postMethod.setRequestHeader("Content-Type","text/html; charset=utf-8");


            httpClient.executeMethod(postMethod);

            //打印出返回数据，检验一下是否成功
            String text =  IOUtils.toString(postMethod.getResponseBodyAsStream(), "utf-8");
            System.out.println(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get(){
        try {
        String dataUrl = "http://111.1.56.27:8080/cpAction!listCp.action";

        HttpClient httpClient = new HttpClient();

        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(dataUrl);

        postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        postMethod.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
        postMethod.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
        postMethod.setRequestHeader("Connection","keep-alive");
        postMethod.setRequestHeader("Cookie","JSESSIONID=aaa_33cmNqroZb8Dte-gu");
        postMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");
        postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        postMethod.setRequestHeader("X-Requested-With","XMLHttpRequest");

        httpClient.executeMethod(postMethod);

        //打印出返回数据，检验一下是否成功
        String text = postMethod.getResponseBodyAsString();
        System.out.println(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void login(){
        //登陆 Url
        String loginUrl = "http://111.1.56.27:8080/loginAction!login.action";

        //需登陆后访问的 Url
        String dataUrl = "http://111.1.56.27:8080/cpAction!listCp.action";

        HttpClient httpClient = new HttpClient();

        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        //设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了
        NameValuePair[] data = {
                new NameValuePair("userAcc", "chenzuxue"),
                new NameValuePair("userPwd", "chenzuxue"),
                new NameValuePair("viliData", "anyany")
        };
        postMethod.setRequestBody(data);

        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);

            //获得登陆后的 Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }

            //进行登陆后的操作
            GetMethod getMethod = new GetMethod(dataUrl);

            //每次访问需授权的网址时需带上前面的 cookie 作为通行证
            getMethod.setRequestHeader("cookie",tmpcookies);

            //你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
            //例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
            //   postMethod.setRequestHeader("Referer", "http://unmi.cc");
            //   postMethod.setRequestHeader("User-Agent","Unmi Spot");

            httpClient.executeMethod(getMethod);

            //打印出返回数据，检验一下是否成功
            String text = getMethod.getResponseBodyAsString();
            System.out.println(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
