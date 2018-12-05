import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-7-27
 * Time: 下午7:37
 * To change this template use File | Settings | File Templates.
 */
public class LeisuHttphelpTest {
    private static final Logger logger = LoggerFactory.getLogger(LeisuHttphelpTest.class);
    private static   CloseableHttpClient httpclient = HttpClients.createDefault();
    private static RequestConfig .Builder config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000);

    public static String getRequest(String url){
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config.build());
        httpget.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        httpget.setHeader("Accept-Encoding","gzip, deflate, sdch, br");
        httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8");
        httpget.setHeader("Connection","keep-alive");
        //httpget.setHeader("Cookie","aliyungf_tc=AQAAAGGlh3Y/1wkADY1LahkecKVB9Rza; PHPSESSID=j5ja6uliqsbt6qlom75h0i8u51; remember-me=%2FoSoJTXG9Hoqgju3M9HNXr%2FCTT%2FGnLgRasWaUz%2BzFtnf%2Btn%2BcUGts4Ui6bIx1UOA%2F8hY8vorTxRDbrriuxxfng%3D%3D; Hm_lvt_63b82ac6d9948bad5e14b1398610939a=1532566356,1532590662,1532708672,1532709301; Hm_lpvt_63b82ac6d9948bad5e14b1398610939a=1532709301; acw_tc=AQAAAH2QjFc44woADY1LavpsNqOT3J4O; public_token=leisu_test; SERVERID=7701f28667987f14e43ce1a88b31663b|1532710019|1532709301");
      //  httpget.setHeader("Cookie","Hm_lvt_63b82ac6d9948bad5e14b1398610939a=1532272589,1532273195,1532679810,1532968602; aliyungf_tc=AQAAAJLmDivDfwgADY1LahgWNVlnfuvb; acw_tc=AQAAAOQnAh/4awgADY1LajqRD9kgR1tq; public_token=leisu_test; Hm_lpvt_63b82ac6d9948bad5e14b1398610939a=1532974389; SERVERID=b8793da2f445eec3bc2df0ef269be0ec|1532974760|1532968602");
       // httpget.setHeader("Cookie","Hm_lvt_63b82ac6d9948bad5e14b1398610939a=1532177445,1533998431; aliyungf_tc=AQAAAPwzdlprAwMADY1LajknYb2/E2vF; acw_tc=AQAAAKyDZA/HbwQADY1Lavu5Di4dYycB; acw_sc__=5b6f241f9e21caf67d8cfcb292b5887221ccb0ef");
       // httpget.setHeader("Cookie","aliyungf_tc=AQAAAB9ibGEjFgcAH6lLapLy0kGv5LA7; Hm_lvt_63b82ac6d9948bad5e14b1398610939a=1532177445,1533998431,1534010418,1534018451; Hm_lpvt_63b82ac6d9948bad5e14b1398610939a=1534018451; acw_tc=AQAAACRnoQTecg4AH6lLanRJXQfeK0gs; public_token=leisu_test; SERVERID=b8793da2f445eec3bc2df0ef269be0ec|1534018457|1534018451");
        httpget.setHeader("Cookie","remember-me=blX%2B4s%2Ba5FLgR%2BTEa81%2BDDNBsM2bmUIBJkThqFcGrIrI7f9Dzz27KKyAauOO%2FrN6ZM2icAJ9POA9SFc2SspLPQ%3D%3D; acw_tc=AQAAABiurlrgwgoA9RYMamcc/LvDSCtP; Hm_lvt_63b82ac6d9948bad5e14b1398610939a=1534575560,1534701521,1534839977,1534926376; Hm_lpvt_63b82ac6d9948bad5e14b1398610939a=1534926376; aliyungf_tc=AQAAAMRsXAtgbg0AmWBvfa7W4vFLn7Ed; PHPSESSID=ivgrn1anve622s04bcer8ulgk1; SERVERID=b8793da2f445eec3bc2df0ef269be0ec|1534926384|1534926382; public_token=leisu_test");
        if(url.equalsIgnoreCase(MsTyLoBet.leisuUrl)){

            if("https://free.leisu.com/".equalsIgnoreCase(url)){
                httpget.setHeader("Host","free.leisu.com");
            }else {
                httpget.setHeader("Host","live.leisu.com");
            }
           httpget.setHeader("Upgrade-Insecure-Requests","1");
           httpget.setHeader("Cache-Control","max-age=0");
       }else {
            httpget.setHeader("Host","api.leisu.com");

            if("https://free.leisu.com/".equalsIgnoreCase(url)){
                httpget.setHeader("Origin","https://free.leisu.com");
                httpget.setHeader("Referer","https:free.leisu.com/?width=720&theme=red&skin=188");
            }else {
                httpget.setHeader("Origin","https://live.leisu.com");
                httpget.setHeader("Referer","https://live.leisu.com/");
            }

       }
       // httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");
       // httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
       // httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
        httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.5702.400 QQBrowser/10.2.1893.400");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                //logger.info("LeisuHttphelp调用http请求成功:"+status+"| url:" +url);
                logger.info("Executing request " + httpget.getRequestLine());

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity,"utf-8") : null;
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

    public static String getRequest2(){
        String url = "https://bet.m88ms.com/UnderOver_data.aspx?Market=l&Sport=1&DT=&RT=W&CT=08/29/2018+10:10:08&Game=0&OrderBy=0&OddsType=2&MainLeague=0&k541344156=v541344649&_=1535546287113";
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config.build());

        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpget.setHeader(" Accept-Encoding", "gzip, deflate, br");
        httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        httpget.setHeader("Connection", "keep-alive");
        httpget.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpget.setHeader("Cookie","userIPCountry=CN; visid_incap_1349630=gT2nvx9MQYKIC4DSFszHakfJZlsAAAAAQUIPAAAAAAAm38HxGcTjLku9znH5qe+6; LangKey=cs; OddsType_MS88RMB01629296=2; dct=85114480329e7db37db3a649c0b635b0; KeepOdds-31738616=no; _ga=GA1.2.1929996184.1533463127; __utma=206572623.1929996184.1533463127.1535533394.1535539531.57; __utmz=206572623.1533463652.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); dss=1%7C1000; OddsType_SPONUUS01445=4; selectedLanguage=zh-CN; IsBDLogin=false; switchViewSkinType_M88=0; DispVer=3; MiniKey=max; _gid=GA1.2.1343174839.1535295198; iovc=3601242; incap_ses_572_1349630=7PF2WJuU/Sq7Dkmo5SfwBzN5hlsAAAAApUAsnIrBcQF8QTJQX+KY+Q==; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; s=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; g=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; u=lhylhy11; memLastLog=; OddsType=2; OddsGroup=3; curr1=RMB; lastdt=10%7ESoccer%7E2; category=0; categoryMenu=0; t=U0VTU0lPTl9JRD05OTVBQUE3RUMxQkVDMjZENkUwODQxQUFBRkM4NzQ4OEU0QjMwMTNGMjQ3RjUzODYwMzFBOTc4RjZDN0I4RTc1RDA1NDJDRjJCNzM2RUFFQjJENjk1MzBGOEVBMUEyMDc0QkEyNUY2NjFENzE2MzYxRDJFQTcwQTQzMTc0N0RBNkJGOTYzQkM0RTExRDMzRTZBQTA1RDkwNTkyNkRBMzczRUI3MUYyQkZBOTREQUVFMDMzMkRBNzYwNzg3RDlCMjNFRUQ1ODlDREFFQ0RCODUwQzVCREFBQTg1ODcwQjAzRTI3RjMzNkE5ODdBQTVBMkEzRjk1RjM2MTExODA1MDI2MDAwQzFEQjQzRDc2QjU5MTkyQkI5RDEyMDJEODBCNTcxNzhDODlDN0JCQzE0QzA4MkIwQUFDMTlEMTcyOTlEREQwQjVFODhEQzkzQThCRTAmVElDS0VUX0lEPTI1MzM0NTkzOA%3d%3d; puid=lhylhy11; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E424740821651B2AB28F65AE9F43C414F843ADD59B76D46FB814EF41FA57B5CB788596885A6A0FFFD1AFF02571A790825C9EF853A64F0B567FB5D7F432750B105984548D268C4A698E033739B5A36D16F81F83018A6DEF7E570755EEB898250A8A53F6EDB0867C45A6407F6D2FACFCB1F913CF66D9CBB7F8CBCD476A3C4E638A226D71E7EE08FD69D4EB67DC1A8F8C7689E141A9FEA4268DEA04566F6D9795D1273C9E30FFBBBDBE6FBB93E368F1EDA7B3246FE18F46390420405AEA16494B374B550C82AADE68FCFCFFA0AB3B96F4FDD705E495BA4852594379C0050F3FAE9E8C233BB9B0526C8AF33A4DC202C495E29EB616BBBC928BA8B6CC0297246F343F43E1A08FDBC1AF234CB6D394ADEE1DB49B2F9E8F5362F3CF1EE8D63F45478A51EB247357D75141C657845F9DA87585EA5893FF86215F38750DF07865101F7363BC52BA7E3BB6848F6D336F2C04FA8D2E87F59D514E1BC9031B6C1180C1CF6762062F3C77B4B6AEFA28E1B22F1CBA80BCC8922FBC504790140DB8059DACAAB3CEC5C345DBB08C7FD351F243C7FB3E34560B67133EE4BDDC9D932C2B9F9417D742B51D41CC98D68D98D295934D6519B01A369E00351C3D4DF980B5B987AF0AE60FF16E51DD32A27379DDBCC8F197578A18210F4BFF99940B6F88B58696A97A1BEB746577AD2B4A9D56450C8A17C95A90C6111A2EC6E49500D1791076A9C178BFC9C99063003207C6FE87969545648E0D8DB5866E5DAE3F69CDC9BA45C40907812CF4C65CDE3E3B9E10AD3E86DD067E8DE5F6FBF9BC0D852B76057FC4CA86FBD3414AF3C3873C6D1E60A1C8A8293F92F9E374863CCAB613257E2A1BD7C7799B61B444708E0422A913F148FFD3B0347AC7526EBE55F128DF8DAFFC10CB32FF630EDCE6F6A3B6312D34786E6B2E701D3F4084EA63588B334E56A1096FEAE17536F0848A5; ot=2; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; curr=RMB; dtC=2; Elapse=3; lgnum_13=10_999; lgnum_21=999; lgnum_12=10_999; Lspid=10_12_11_32; __utmc=206572623; srttyp=0; lgbts=20180829184543347_Z6HQf3QPm00; ASP.NET_SessionId=j21wbw5vndfcj4dmejkbpjhj; lip=wrrCtcKUwoTDhcOSwpfCjMK0w7fDmcKhdsKNw6bCjA==");
        httpget.setHeader("Host", "nss.m88ms.com");
        httpget.setHeader("Origin", "https://nss.m88ms.com");
        httpget.setHeader("Referer", "https://bet.m88ms.com/UnderOver.aspx?Sport=1&Market=t&DispVer=3&Game=0");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300) {
                //logger.info("LeisuHttphelp调用http请求成功:"+status+"| url:" +url);
                logger.info("Executing request " + httpget.getRequestLine());

                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity,"utf-8") : null;
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
            System.out.println(getRequest2());

      //  System.out.println(getRequest("https://api.leisu.com/api/eventdata?scheduleid=2529413&type=event"));

    }

}
