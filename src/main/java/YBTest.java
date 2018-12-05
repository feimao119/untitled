import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-8-25
 * Time: 上午12:03
 * To change this template use File | Settings | File Templates.
 */
public class YBTest {
    private static final Logger logger = LoggerFactory.getLogger(YBTest.class);

    public static String getMatchListStr() throws Exception{

        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);

        PostMethod listPostMethod = new PostMethod("http://xj-sb-asia-yabo.prdasbbwla1.com/zh-cn/serv/getodds");

        listPostMethod.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        listPostMethod.setRequestHeader("Accept-Encoding", "gzip, deflate");
        listPostMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9");
       // listPostMethod.setRequestHeader("Cache-Control", "max-age=0");
        listPostMethod.setRequestHeader("Connection", "keep-alive");
        listPostMethod.setRequestHeader("Content-Length", "113");
        listPostMethod.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        //listPostMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        //listPostMethod.setRequestHeader("Cookie","ASP.NET_SessionId=3ugqekd0qqoqtfkmshlgcvxk; sbawl3=1007685386.20480.0000; .Xauth=1E72145FC7E6562058507CF4224DCEDC657591069920D62B6BBC1F664B38646FBF3EB72FF773D56F20F4303CED6D408BA2310DD4E29945BE6BC2514B06B64B101A9F2E0CD2742A2DE91C23979AE262A2D12A4DB7EC64F3A59BC772CEDC09CCF98E863FF39B705B2EBC0875DAA12CB52CF44FB9EC174B3BF89FE38FC83A9F5F21ACB172B256CD1848C86D69E0F51C525A467A9E03CEF17D3612E447E00901EEA7");
        listPostMethod.setRequestHeader("Cookie","ASP.NET_SessionId=3ugqekd0qqoqtfkmshlgcvxk; sbawl3=1007685386.20480.0000; .Xauth=FBDE066E4EBAE1483847C26705918631B73450D9F1993A5A8BDAE3882EF62FE0949DCF0B6C917C9A2EF7B1F0D30BD1DADE7964767EED2DF43D101690109C62C36F3ACACC6441804C05246ED5DE8DB5F1DDA5C57C1676FCFC76373F183F71871775AB5A15F952999939A30F0E76E598BF9610C9B38F99ABBE2E654368CCA94C1FA5D7133EC6BA40C84AFE86C857EB41E7AB7A8DDD7C65310B04068C28D0A6A8FF");
        listPostMethod.setRequestHeader("Host", "xj-sb-asia-yabo.prdasbbwla1.com");
        listPostMethod.setRequestHeader("Origin", "http://xj-sb-asia-yabo.prdasbbwla1.com");
        listPostMethod.setRequestHeader("Referer", "http://xj-sb-asia-yabo.prdasbbwla1.com/zh-cn/sports");
       // listPostMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        listPostMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.5702.400 QQBrowser/10.2.1893.400");

        RequestEntity entity = new StringRequestEntity("{\"ifl\":true,\"ipf\":false,\"iip\":false,\"pn\":0,\"tz\":480,\"pt\":1,\"pid\":0,\"sid\":[1],\"ubt\":\"am\",\"dc\":null,\"dv\":-1,\"ot\":0}", "text/html", "utf-8");
        listPostMethod.setRequestEntity(entity);


        listPostMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        long startTime = System.currentTimeMillis();

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {
            logger.info("===================getMatchListStr http start======================");

            statusCode = httpClient.executeMethod(listPostMethod);

            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + listPostMethod.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);
            System.out.print("charset:"+listPostMethod.getResponseCharSet()+"~~~");
           // String result = listPostMethod.getResponseBodyAsString();
            String result = IOUtils.toString(listPostMethod.getResponseBodyAsStream(), "UTF-8");

            logger.info("getMatchListStr result Length:" + result);

            logger.info("=========================getMatchListStr http end=========================");

            if(result==null || result.length()<100){
                return null;
            } else {
                return result;
            }

        } catch (Exception ex) {
            statusCode = 499;
            try {
                logger.info("调用http请求异常: " + listPostMethod.getURI() + ",耗时：" + elapsedTime
                        + "ms, exception:" + ex.getMessage());
            } catch (URIException uriex) {
                logger.info("uriex"+uriex);
            }
            if (ex instanceof HttpInvokeException) {
                throw (HttpInvokeException) ex;
            } else {
                throw new HttpInvokeException(statusCode, ex);
            }
        } finally {
            listPostMethod.releaseConnection();
        }

    }

    public static void main(String[] args) throws Exception {
        // String a = getRequest("https://free.leisu.com/");

        //String b = getRequest("https://api.leisu.com/api/eventdata?scheduleid=2529413&type=event");
        // System.out.println(getRequest("https://live.leisu.com/"));

       getMatchListStr();


    }


}
