
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
public class MsLogin {
    public static void main(String[] args){
        login();
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
        String loginUrl = "https://www.m88ms.com/common/login.aspx";

        //需登陆后访问的 Url
        String dataUrl = "https://www.m88ms.com/Main/Casino/";

        HttpClient httpClient = new HttpClient();

        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);


        postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        postMethod.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        postMethod.setRequestHeader("Cache-Control", "max-age=0");
        postMethod.setRequestHeader("Connection", "keep-alive");
        //postMethod.setRequestHeader("Content-Length", "3365");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        postMethod.setRequestHeader("Cookie","KeepOdds-31232766=no; OddsType_SPONUUS01445=4; iovc=3558655; IsBDLogin=false; LangKey=cs; switchViewSkinType_M88=0; DispVer=3; MiniKey=max; OddsType_MS88RMB01626539=2; dss=1%7C100; _ga=GA1.2.705112580.1531658063; _gid=GA1.2.221429814.1531658063; __utma=206572623.705112580.1531658063.1532361085.1532422183.46; __utmz=206572623.1531659170.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); ASP.NET_SessionId=uoviyaprpzfeaorewv0eqjwl; visid_incap_1349630=+QPpWZs9RjCA8D2aQwpKuy4/S1sAAAAAQUIPAAAAAABPdO/lsirVlPC0FYv4AEUM; incap_ses_809_1349630=y9gQYLiuD0m0QSYfsiU6C6boVlsAAAAAZPRgGV9H70y73Z+WIFk2vw==; M88-A4=close; memLastLog=; selectedLanguage=zh-CN; OddsType=2; userIPCountry=CN; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; m88_cookie=4194654474.20480.0000; _pk_id.1.bee6=8c52e640da33338e.1531658036.59.1532425770.1532418358.; _pk_ses.1.bee6=*");
        postMethod.setRequestHeader("Host", "nss.m88ms.com");
        postMethod.setRequestHeader("Origin", "https://nss.m88ms.com");
        postMethod.setRequestHeader("Referer", "https://www.m88ms.com/Main/Login/Login.aspx");
        postMethod.setRequestHeader("Upgrade-Insecure-Requests", "1");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");


        //设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了
        NameValuePair[] data = {
                new NameValuePair("txtLoginID", "cbz20018"),
                new NameValuePair("txtPassword", "cv123888"),
                new NameValuePair("txtValCode", "代码"),
                new NameValuePair("__loginVal", "85360A7A-833F-4F21-BF5A-B4228390D52A"),
                new NameValuePair("ioBB","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"),
/*                new NameValuePair("ioBB", "0400aAXOogFCkNMNf94lis1ztlkWjf2fZuuc9wwpda9xwkSq2BaoeyCP8F9riRqbUi+g2LmBieZIc8t64l29L5aAtbEfVk9EjAzqz2mqlN1xLBcmINsJ9VlMYGTX23I2zu" +
                        "DzEYngAz99aSN4XtVYAig4ljtEiuW5olM1mGODXWbkrLSLP/wT3etan8vB0yGfHrbifAHtJofzkmbIB9QxnksoWoWXzUILvpZkY0QijGaCoR6wHHrDW3HSYxnPse2ZJCsq4e3f1u/ETzp5VpgkQXTQzZ2bCkUkx/iD+dWK9V9aX8ZQSe8GkbMX1HOH6MhxZKmnVIAW7u" +
                        "DQjXMJFixrup4qTMSnnqCZVG1N8RWI6SXmgL+VT31SgfbGSWvdDA0Vjmn0aS1V3jCw8l2IkKBrrkY+pahgFPQpYzgU2uyDjiwQ0KMfbdavvp/v+1+rdsbATajArHfzpKu0BjAcqCYRdV0oboem+V7o3Xg/w+E1ob+/FRCJ3uese38lUZb/57l0F0Br19ICgzhT9Wvz6" +
                        "D+YNUMLEewTRhqhcaO/Y8Y1wTfZsWPQHgd8UfXmE2E7VDaOZWB81xsCUyDz1biqu2GdtD8aLJ1rvyZX5MUwJpQYAFUrDlfSKRv/eHV3QiboXLuw9Lm6F6oRgJyUj+jLcCoyDZm+Swn8tgaAx5RidNymITnEQuUlL3DOwAl0hlzqeMmBCH/inFJjUN1WkR+PgIAgh1f+w/wcQunZjr5xdfmpxuRXdup" +
                        "Dgm50c6V/UpKetslI3IixGz5pCHe1/VC4xI1RTIYC8ouD71qCKcmZqa+c5UMfdLNXqLz+1vlqUAr9dE2jcfl0wgroQBfpyuKU/Hx7YkL46tebJgzGPx/MVTw2C4oQ2p4qiA12zfBdKgqopgfJivmtWVQpngZWDZvL9kaL85lzU9YPUST3gTYnG+8gClZWFN+P1XHbFb" +
                        "Wdu9IgF8s35HQzv0w6dFMy2Esizp28x+6HoOCSU0oZPWNEfF9hXdZ4DRRkrbXWcoCj3EFS9nbsv6+gZNnHw9CBwCH9rxZ7qeEFWYU7fizS6vteuhKz1qqXbx9aid5P4D8QCtmLUiR8onW1vBZJ02O+RKuRHgAVLUFj5i+D3EqN8OkstLxsNc+VhB4sPiZXdNCqfKAbzw" +
                        "OrtOItTHA2labaL23XIj00GhUM5jFXuPWw+r5pQgpjTJURthWotTESDVDlaTmdbVxKbLOeUQ50p/wnlDZFRarIuC6RSZndk1PYFXQ2B8n/NyT5AQIIOiQvlOIetxG3cjB5aSrLFocpH3USKOMTM5gDErlgAdC4L8a2LYYlRZHyqRJ91ZBwX6F8FpStCrcVfx+jLvEL6JHC" +
                        "DwVACeEIlxyhW8wE85Ga6VekbyzRPAXkqtWGUlqjaxZfSpXfyb9oxeaREx9s8xrrbCjXXKMQ9k0NyFoJA6bWgpMK952MiiA53PFXd/o1R3Iiqa8wSbSiUFek291tkJV8QrZiMJhnZa9xcMojXmnKjfZGZ5GvqLgsSg3VDW0wzJ5gUjNiq0zKpFEPuihX9F2xjGfvHXkcn5Z" +
                        "NqmrwFy7+IruHTGE6UV6nrtHXFV51Uf5afnBEWo9yVXMmYueFThNxRb68uJlqaiVqOEONh2lP4p2OKKuURGFR2zPIFh1OHf2IwJfVbsMbGIvB5V+uQYHCJ02Lw/434qKAENd/4ZuoZTPuxsLaOe2U3eekFPdj3+ma3XWpv2MtF2PvBnHuZHfmzuFoU50v4l+1K/dqoKi4LEo" +
                        "N1Q1tqLQ3FJBTh8xjFquWXW9PegfY/+MBGfJuOaPmVsolAAx3nw3httJPjkZHNcsemyXqkJV8QrZiMJj61Tb0ipmopv/8wcS9lJ6Pv4sSL8bN2c97QbZL9oUtKD2ZJCfh6Urv/oCG0CN36Pk5RjzXAkiT/aitrC3MuC2/qGWDAoi7y+Fwdgay8eWMIGXRYKeV2vdUIELn" +
                        "UJ9SvB98dgsO4ZarKvbtlJOK/BPbHT3J93wUUvD1M+rSkeTiCyFWkMI5ihc6qc7S9vdc8aZy5Y5F64oAt77Y6qSQNeXS5KKYY3fgIf/l0c/eQ/zF+0kkISukBwIeOsqmzVHfwOo2mX5ha9lpjDjEL543G6P1a0Yy5JnHYpMW3zH/QW2900ZEAOwDzEYs05pDlYp7hco29biw" +
                        "TZ98MSQVtPIGt7ZJGO345cQBeVQPDSN/xbk/45I/NqGUdD3GpDIP0CxSx7ndZKIXHvYMGrTC6TsSYD0QgwaJe/WBqZwhvah0twBACaWgubBod1q4N4LuD/6ErxnQHxn1vGd+9lUmoq3MFE1OLcOA+O688enkXc9sucW8k7/Keei7n7Gnofn+PWIgQzzsT1hf1HVzz9UpXJU/H" +
                        "4+25GRi9V0Uo11i0FRulruXQe3nDNKzZdShLcOA+O688enkXc9sucW8k7/Keei7n7Gnofn+PWIgQzzsT1hf1HVzz9UpXJU/H4+2qmTi2zD2z2pJW+ciiSJNQ3QFq/jRbadW7ZQozpxnSlhCSmeeOip4gichWvTU4DUuCvOywu8UTO0PScT5Njx0iKZcx6f68PrcUZYYSo081OLg" +
                        "BJ2FmkxByP/855SD16Cg67gEcIY7HTJ84uAez6baMmsngsQajmYYSzvTAJF67dCc8VZbCeU1gdxMja/viVkeGxS+WWpbDXm4n2Po5mDSNvhguiPOMe+7zPtKQszdMyOnU5WuDmn2OEiQDdHxqshNbVHquuoJp8eBDoiEfBNdnvZEWgUTdWVNVqnFGlZKrVNmLpqpjNH7DIRzrVl9" +
                        "HhumCzdiGYGrJdAlraNh7aUCJ/V1BR7YJidkDvBiQx6ZabxFvh0BdzP/FyjxvhK4i0acI58khmz9K1SpZlzIRUrmh3CRzAxzR540K/dVvrU1sHuQqkgXOg3YwOlm0NsHAxTRTS+giiWPHoMMwHxEQeJ3Hb9uGB2iSpVhJuPdzGEbcx6gCk3nmFPeyA=="),*/
                new NameValuePair("pageURL", "//www.m88ms.com/Main/Casino")
        };
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        postMethod.setRequestBody(data);

        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int statusCode = httpClient.executeMethod(postMethod);

            System.out.println("statusCode:"+statusCode);

            //获得登陆后的 Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }

            System.out.println("tmpcookies:"+tmpcookies);
            String text = postMethod.getResponseBodyAsString();
            System.out.println("::"+text);

           // postMethod.releaseConnection();

            //进行登陆后的操作
/*            GetMethod getMethod = new GetMethod(dataUrl);

            //每次访问需授权的网址时需带上前面的 cookie 作为通行证
            getMethod.setRequestHeader("cookie",tmpcookies);

            //你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
            //例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
            //   postMethod.setRequestHeader("Referer", "http://unmi.cc");
            //   postMethod.setRequestHeader("User-Agent","Unmi Spot");

            httpClient.executeMethod(getMethod);

            //打印出返回数据，检验一下是否成功
            String text = getMethod.getResponseBodyAsString();
            System.out.println(text);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
