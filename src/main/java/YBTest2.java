import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-8-25
 * Time: 上午12:37
 * To change this template use File | Settings | File Templates.
 */
public class YBTest2 {
    private static final Logger logger = LoggerFactory.getLogger(YBTest2.class);

   // private static CloseableHttpClient httpclient = HttpClients.createDefault();
   // private static RequestConfig.Builder config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000);

    public static String getRequest(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        RequestConfig.Builder config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000);
        httppost.setConfig(config.build());
       // PostMethod listPostMethod = new PostMethod("http://xj-sb-asia-yabo.prdasbbwla1.com/zh-cn/serv/getodds");

        // httppost.setEntity(stringEntity);
        httppost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httppost.setHeader("Accept-Encoding", "gzip,deflate");
        httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        // httppost.setHeader("Cache-Control", "max-age=0");
        httppost.setHeader("Connection", "keep-alive");
       // httppost.setHeader("Content-Length", "132");
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        //httppost.setHeader("Cookie","ASP.NET_SessionId=3ugqekd0qqoqtfkmshlgcvxk; sbawl3=1007685386.20480.0000; .Xauth=1E72145FC7E6562058507CF4224DCEDC657591069920D62B6BBC1F664B38646FBF3EB72FF773D56F20F4303CED6D408BA2310DD4E29945BE6BC2514B06B64B101A9F2E0CD2742A2DE91C23979AE262A2D12A4DB7EC64F3A59BC772CEDC09CCF98E863FF39B705B2EBC0875DAA12CB52CF44FB9EC174B3BF89FE38FC83A9F5F21ACB172B256CD1848C86D69E0F51C525A467A9E03CEF17D3612E447E00901EEA7");
       // httppost.setHeader("Cookie","ASP.NET_SessionId=3ugqekd0qqoqtfkmshlgcvxk; sbawl3=1007685386.20480.0000; .Xauth=FBDE066E4EBAE1483847C26705918631B73450D9F1993A5A8BDAE3882EF62FE0949DCF0B6C917C9A2EF7B1F0D30BD1DADE7964767EED2DF43D101690109C62C36F3ACACC6441804C05246ED5DE8DB5F1DDA5C57C1676FCFC76373F183F71871775AB5A15F952999939A30F0E76E598BF9610C9B38F99ABBE2E654368CCA94C1FA5D7133EC6BA40C84AFE86C857EB41E7AB7A8DDD7C65310B04068C28D0A6A8FF");
        httppost.setHeader("Cookie","ASP.NET_SessionId=rtypmefm0ejpkmaqtjynnpqx; sbawl3=890244874.20480.0000; .Xauth=7EF29E556C3B3AAE4419D6E85CB8DDC4BA3FECE08E36F89D2CBE10C49959CE0745E64E1B19E5D8FF2CBE99D915DF3692788DCE144EF042F341BA864C2A77844DB46EE1C86BD8D308B89D126B12F4D51ED7961CE40AA2AB90E33FD8E8C085792C038D7B4672988B2072452216FD6F546A0A0DF007EE0D26C35A7506DC12CBDFE16C6918B0B592CA21CFABE2D89FA88EF7ECB07711057D018426504EA888BDDFD1");
        httppost.setHeader("Host", "xj-sb-asia-yabo.prdasbbwla1.com");
        httppost.setHeader("Origin", "http://xj-sb-asia-yabo.prdasbbwla1.com");
        httppost.setHeader("Referer", "http://xj-sb-asia-yabo.prdasbbwla1.com/zh-cn/sports");
        httppost.setHeader("X-Requested-With", "XMLHttpRequest");
        httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.5702.400 QQBrowser/10.2.1893.400");

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity("{\"ifl\":false,\"ipf\":false,\"iip\":true,\"pn\":0,\"tz\":480,\"pt\":1,\"pid\":0,\"sid\":[1],\"ubt\":\"am\",\"dc\":null,\"dv\":0,\"ot\":2,\"sb\":1,\"v\":[212078]}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        stringEntity.setContentType("text/json");
        httppost.setEntity(stringEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                //logger.info("LeisuHttphelp调用http请求成功:"+status+"| url:" +url);
                logger.info("Executing request " + httppost.getRequestLine());

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
            } else {
                logger.info("LeisuHttphelp调用http请求失败:"+status+"| url:" +url);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            logger.info("LeisuHttphelp调用http请求异常:url:" +url+"IOException" +e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return null;
    }

    public static void main(String[] args) {
        // String a = getRequest("https://free.leisu.com/");

        //String b = getRequest("https://api.leisu.com/api/eventdata?scheduleid=2529413&type=event");
        // System.out.println(getRequest("https://live.leisu.com/"));

        System.out.println(getRequest("http://xj-sb-asia-yabo.prdasbbwla1.com/zh-cn/serv/getodds"));


    }
}
