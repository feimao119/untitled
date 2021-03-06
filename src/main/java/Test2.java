/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test2 {

    public static void main(String[] args){
        //登陆 Url
        String loginUrl = "http://111.1.56.27:8080/loginAction!login.action";

        //需登陆后访问的 Url
        String dataUrl = "http://111.1.56.27:8080/cpAction!listCp.action";

        HttpClient httpClient = new HttpClient();

        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        //设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了
        NameValuePair[] data = {
                new NameValuePair("user.loginName", "chenzuxue"),
                new NameValuePair("user.password", "chenzuxue"),
                new NameValuePair("code", "anyany")
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