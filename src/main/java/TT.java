import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-7-14
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
public class TT {
    public static void main(String[] args) throws Exception {
        TT t = new TT();

            t.getMatchListStr2();

    }

    private static final Logger logger = LoggerFactory.getLogger(MsTyLoBet.class);

    public String getMatchListStr()  {
        HttpClient httpClient = new HttpClient();

        GetMethod listPostMethod = new GetMethod("https://bet.m88ms.com/UnderOver_data.aspx?Market=l&Sport=1&DT=&RT=W&CT=08/29/2018+10:10:08&Game=0&OrderBy=0&OddsType=2&MainLeague=0&k541344156=v541344649&_=1535546287113");

        listPostMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        listPostMethod.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        listPostMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        listPostMethod.setRequestHeader("Connection", "keep-alive");
        listPostMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        listPostMethod.setRequestHeader("Cookie","userIPCountry=CN; visid_incap_1349630=gT2nvx9MQYKIC4DSFszHakfJZlsAAAAAQUIPAAAAAAAm38HxGcTjLku9znH5qe+6; LangKey=cs; OddsType_MS88RMB01629296=2; dct=85114480329e7db37db3a649c0b635b0; KeepOdds-31738616=no; _ga=GA1.2.1929996184.1533463127; __utma=206572623.1929996184.1533463127.1535533394.1535539531.57; __utmz=206572623.1533463652.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); dss=1%7C1000; OddsType_SPONUUS01445=4; selectedLanguage=zh-CN; IsBDLogin=false; switchViewSkinType_M88=0; DispVer=3; MiniKey=max; _gid=GA1.2.1343174839.1535295198; iovc=3601242; incap_ses_572_1349630=7PF2WJuU/Sq7Dkmo5SfwBzN5hlsAAAAApUAsnIrBcQF8QTJQX+KY+Q==; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; s=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; g=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; u=lhylhy11; memLastLog=; OddsType=2; OddsGroup=3; curr1=RMB; lastdt=10%7ESoccer%7E2; category=0; categoryMenu=0; t=U0VTU0lPTl9JRD05OTVBQUE3RUMxQkVDMjZENkUwODQxQUFBRkM4NzQ4OEU0QjMwMTNGMjQ3RjUzODYwMzFBOTc4RjZDN0I4RTc1RDA1NDJDRjJCNzM2RUFFQjJENjk1MzBGOEVBMUEyMDc0QkEyNUY2NjFENzE2MzYxRDJFQTcwQTQzMTc0N0RBNkJGOTYzQkM0RTExRDMzRTZBQTA1RDkwNTkyNkRBMzczRUI3MUYyQkZBOTREQUVFMDMzMkRBNzYwNzg3RDlCMjNFRUQ1ODlDREFFQ0RCODUwQzVCREFBQTg1ODcwQjAzRTI3RjMzNkE5ODdBQTVBMkEzRjk1RjM2MTExODA1MDI2MDAwQzFEQjQzRDc2QjU5MTkyQkI5RDEyMDJEODBCNTcxNzhDODlDN0JCQzE0QzA4MkIwQUFDMTlEMTcyOTlEREQwQjVFODhEQzkzQThCRTAmVElDS0VUX0lEPTI1MzM0NTkzOA%3d%3d; puid=lhylhy11; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E424740821651B2AB28F65AE9F43C414F843ADD59B76D46FB814EF41FA57B5CB788596885A6A0FFFD1AFF02571A790825C9EF853A64F0B567FB5D7F432750B105984548D268C4A698E033739B5A36D16F81F83018A6DEF7E570755EEB898250A8A53F6EDB0867C45A6407F6D2FACFCB1F913CF66D9CBB7F8CBCD476A3C4E638A226D71E7EE08FD69D4EB67DC1A8F8C7689E141A9FEA4268DEA04566F6D9795D1273C9E30FFBBBDBE6FBB93E368F1EDA7B3246FE18F46390420405AEA16494B374B550C82AADE68FCFCFFA0AB3B96F4FDD705E495BA4852594379C0050F3FAE9E8C233BB9B0526C8AF33A4DC202C495E29EB616BBBC928BA8B6CC0297246F343F43E1A08FDBC1AF234CB6D394ADEE1DB49B2F9E8F5362F3CF1EE8D63F45478A51EB247357D75141C657845F9DA87585EA5893FF86215F38750DF07865101F7363BC52BA7E3BB6848F6D336F2C04FA8D2E87F59D514E1BC9031B6C1180C1CF6762062F3C77B4B6AEFA28E1B22F1CBA80BCC8922FBC504790140DB8059DACAAB3CEC5C345DBB08C7FD351F243C7FB3E34560B67133EE4BDDC9D932C2B9F9417D742B51D41CC98D68D98D295934D6519B01A369E00351C3D4DF980B5B987AF0AE60FF16E51DD32A27379DDBCC8F197578A18210F4BFF99940B6F88B58696A97A1BEB746577AD2B4A9D56450C8A17C95A90C6111A2EC6E49500D1791076A9C178BFC9C99063003207C6FE87969545648E0D8DB5866E5DAE3F69CDC9BA45C40907812CF4C65CDE3E3B9E10AD3E86DD067E8DE5F6FBF9BC0D852B76057FC4CA86FBD3414AF3C3873C6D1E60A1C8A8293F92F9E374863CCAB613257E2A1BD7C7799B61B444708E0422A913F148FFD3B0347AC7526EBE55F128DF8DAFFC10CB32FF630EDCE6F6A3B6312D34786E6B2E701D3F4084EA63588B334E56A1096FEAE17536F0848A5; ot=2; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; curr=RMB; dtC=2; Elapse=3; lgnum_13=10_999; lgnum_21=999; lgnum_12=10_999; Lspid=10_12_11_32; __utmc=206572623; srttyp=0; lgbts=20180829184543347_Z6HQf3QPm00; ASP.NET_SessionId=j21wbw5vndfcj4dmejkbpjhj; lip=wrrCtcKUwoTDhcOSwpfCjMK0w7fDmcKhdsKNw6bCjA==");
        listPostMethod.setRequestHeader("Host", "nss.m88ms.com");
        listPostMethod.setRequestHeader("Origin", "https://nss.m88ms.com");
        listPostMethod.setRequestHeader("Referer", "https://bet.m88ms.com/UnderOver.aspx?Sport=1&Market=t&DispVer=3&Game=0");
        listPostMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");

/*        Map matchMap = new HashMap<String, String>();
        matchMap.put("spid", "10");


        listPostMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        listPostMethod.setRequestBody(buildNameValuePair(matchMap));*/

        long startTime = System.currentTimeMillis();

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {
            logger.info("===================getMatchListStr http start======================");

            statusCode = httpClient.executeMethod(listPostMethod);

            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + listPostMethod.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            String result = listPostMethod.getResponseBodyAsString();

            logger.info("getMatchListStr result Length:" + result.length());
            logger.info("result:" +result);

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

    public  String getMatchListStr2() throws Exception{

        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);

        GetMethod listPostMethod = new GetMethod("https://bet.m88ms.com/UnderOver_data.aspx?Market=l&Sport=1&DT=&RT=W&CT=08/29/2018+10:10:08&Game=0&OrderBy=0&OddsType=2&MainLeague=0&k541344156=v541344649&_=1535546287113");

        listPostMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        listPostMethod.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        listPostMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        listPostMethod.setRequestHeader("Connection", "keep-alive");
        listPostMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        listPostMethod.setRequestHeader("Cookie","userIPCountry=CN; visid_incap_1349630=gT2nvx9MQYKIC4DSFszHakfJZlsAAAAAQUIPAAAAAAAm38HxGcTjLku9znH5qe+6; LangKey=cs; OddsType_MS88RMB01629296=2; dct=85114480329e7db37db3a649c0b635b0; KeepOdds-31738616=no; _ga=GA1.2.1929996184.1533463127; __utma=206572623.1929996184.1533463127.1535533394.1535539531.57; __utmz=206572623.1533463652.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); dss=1%7C1000; OddsType_SPONUUS01445=4; selectedLanguage=zh-CN; IsBDLogin=false; switchViewSkinType_M88=0; DispVer=3; MiniKey=max; _gid=GA1.2.1343174839.1535295198; iovc=3601242; incap_ses_572_1349630=7PF2WJuU/Sq7Dkmo5SfwBzN5hlsAAAAApUAsnIrBcQF8QTJQX+KY+Q==; ms_session=%7B%22brand%22%3A%22M88%22%2C%22channel%22%3A%7B%22trafficSource%22%3A%22DIRECT%22%2C%22trafficReferral%22%3A%22%22%2C%22referralURL%22%3A%22%22%2C%22referralFullURL%22%3A%22%22%2C%22registerFrom%22%3A%22www.m88ms.com%22%2C%22platform%22%3A%22Desktop%22%2C%22custom_data%22%3A%7B%7D%7D%7D; ms_traffic_source=DIRECT; s=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; g=DBBE5894-7138-4BC2-847A-CE6CF2BD8A8C; u=lhylhy11; memLastLog=; OddsType=2; OddsGroup=3; curr1=RMB; lastdt=10%7ESoccer%7E2; category=0; categoryMenu=0; t=U0VTU0lPTl9JRD05OTVBQUE3RUMxQkVDMjZENkUwODQxQUFBRkM4NzQ4OEU0QjMwMTNGMjQ3RjUzODYwMzFBOTc4RjZDN0I4RTc1RDA1NDJDRjJCNzM2RUFFQjJENjk1MzBGOEVBMUEyMDc0QkEyNUY2NjFENzE2MzYxRDJFQTcwQTQzMTc0N0RBNkJGOTYzQkM0RTExRDMzRTZBQTA1RDkwNTkyNkRBMzczRUI3MUYyQkZBOTREQUVFMDMzMkRBNzYwNzg3RDlCMjNFRUQ1ODlDREFFQ0RCODUwQzVCREFBQTg1ODcwQjAzRTI3RjMzNkE5ODdBQTVBMkEzRjk1RjM2MTExODA1MDI2MDAwQzFEQjQzRDc2QjU5MTkyQkI5RDEyMDJEODBCNTcxNzhDODlDN0JCQzE0QzA4MkIwQUFDMTlEMTcyOTlEREQwQjVFODhEQzkzQThCRTAmVElDS0VUX0lEPTI1MzM0NTkzOA%3d%3d; puid=lhylhy11; sr=10F002E6106E9EF2E7C0C580C500E5E3721E1C77EB2F0E424740821651B2AB28F65AE9F43C414F843ADD59B76D46FB814EF41FA57B5CB788596885A6A0FFFD1AFF02571A790825C9EF853A64F0B567FB5D7F432750B105984548D268C4A698E033739B5A36D16F81F83018A6DEF7E570755EEB898250A8A53F6EDB0867C45A6407F6D2FACFCB1F913CF66D9CBB7F8CBCD476A3C4E638A226D71E7EE08FD69D4EB67DC1A8F8C7689E141A9FEA4268DEA04566F6D9795D1273C9E30FFBBBDBE6FBB93E368F1EDA7B3246FE18F46390420405AEA16494B374B550C82AADE68FCFCFFA0AB3B96F4FDD705E495BA4852594379C0050F3FAE9E8C233BB9B0526C8AF33A4DC202C495E29EB616BBBC928BA8B6CC0297246F343F43E1A08FDBC1AF234CB6D394ADEE1DB49B2F9E8F5362F3CF1EE8D63F45478A51EB247357D75141C657845F9DA87585EA5893FF86215F38750DF07865101F7363BC52BA7E3BB6848F6D336F2C04FA8D2E87F59D514E1BC9031B6C1180C1CF6762062F3C77B4B6AEFA28E1B22F1CBA80BCC8922FBC504790140DB8059DACAAB3CEC5C345DBB08C7FD351F243C7FB3E34560B67133EE4BDDC9D932C2B9F9417D742B51D41CC98D68D98D295934D6519B01A369E00351C3D4DF980B5B987AF0AE60FF16E51DD32A27379DDBCC8F197578A18210F4BFF99940B6F88B58696A97A1BEB746577AD2B4A9D56450C8A17C95A90C6111A2EC6E49500D1791076A9C178BFC9C99063003207C6FE87969545648E0D8DB5866E5DAE3F69CDC9BA45C40907812CF4C65CDE3E3B9E10AD3E86DD067E8DE5F6FBF9BC0D852B76057FC4CA86FBD3414AF3C3873C6D1E60A1C8A8293F92F9E374863CCAB613257E2A1BD7C7799B61B444708E0422A913F148FFD3B0347AC7526EBE55F128DF8DAFFC10CB32FF630EDCE6F6A3B6312D34786E6B2E701D3F4084EA63588B334E56A1096FEAE17536F0848A5; ot=2; vp7=0; vp26=0; onHashID=; MPL=-99^5.0000^2000.0000^200000.0000|1^5.0000^20000.0000^200000.0000|2^5.0000^10000.0000^200000.0000|3^5.0000^3000.0000^200000.0000|90^5.0000^5000.0000^200000.0000|; curr=RMB; dtC=2; Elapse=3; lgnum_13=10_999; lgnum_21=999; lgnum_12=10_999; Lspid=10_12_11_32; __utmc=206572623; srttyp=0; lgbts=20180829184543347_Z6HQf3QPm00; ASP.NET_SessionId=j21wbw5vndfcj4dmejkbpjhj; lip=wrrCtcKUwoTDhcOSwpfCjMK0w7fDmcKhdsKNw6bCjA==");
        listPostMethod.setRequestHeader("Host", "nss.m88ms.com");
        listPostMethod.setRequestHeader("Origin", "https://nss.m88ms.com");
        listPostMethod.setRequestHeader("Referer", "https://bet.m88ms.com/UnderOver.aspx?Sport=1&Market=t&DispVer=3&Game=0");
        listPostMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");

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
}