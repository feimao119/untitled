import dao.MsTyMatchDao;
import domain.MsTyMatch;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MsTyLoBet1210 {

    private static final PropertyUtil prop = new PropertyUtil("E://work//untitled//src//main//resources//conf.properties");
    //private static final int Money = NumberUtils.toInt(prop.getString("Money"),50);
    private static String RollTime = prop.getString("rollTime");
    private static String Agent = prop.getString("Agent");
    private static String Cookie = prop.getString("Cookie");
    private static final String TestStr = prop.getString("TestStr");
    private static final String BetUrl = prop.getString("BetUrl");
    private static final String ListUrl = prop.getString("ListUrl");
    public static final String leisuUrl = prop.getString("leisuUrl");
    private static  String saveLanguage = "en";

    private static final Logger logger = LoggerFactory.getLogger(MsTyLoBet1210.class);
    private static final String Charset = "UTF-8";
    private static Map<Integer,MsTyMatch> msTyMatches = new HashMap<Integer, MsTyMatch>();
   // private static Map<Integer,MsTyMatch> baseMsTyMatches = new HashMap<Integer, MsTyMatch>();
    private static Date _date = new Date();
    private static  HttpClient httpClient;
    private static MsTyMatch prevMsTyMatch = null;
    private static MsTyMatch betMoneySurMsTyMatch = null;
    private  String prevType = null;

    MsTyLoBet1210(){
        httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);
    }

    public static void main(String[] args) throws Exception {
        // PropertyConfigurator.configure("log4j.properties");

        logger.info("===============================prop==============================================");
        logger.info("[new]");
        logger.info("[Money:"+prop.getString("Money") +"]");
        logger.info("[Cookie:"+Cookie +"]");
        logger.info("===============================prop end==============================================");

        //获取比赛列表
        //备选条件策略
        //赛事id存入临时list
        //备选赛事入库 + 第一次比赛数据（包括数据id）
        //*每分钟备选赛事数据更新（赔率+数据）
        //1分钟后循环

        //备选赛事观察线程  //先单线程
        //循环获取观察赛事
        //应用投注策略
        //符合策略投注
        //继续观察

        //备选条件策略（领先+赔率0.775以上）

        /*投注策略
        先都5分钟一单，不区分上下半场
        上半场:
           1）每3分钟观察1次（事实获取备选赛事数据（赔率+数据））比分扳平或反超放弃（标注）
           2）观察时符合2倍盈利条件、赔率稳定、数据上升的下注，根据2倍盈利条件数据大小决定注额大小， 分1.5-2.0，2.1-2.8，2.8以上3档
           3）30分钟前最多1注，30分钟后每次观测符合条件的再下1注，42分钟停止投注，上半场最多5注
        下半场：
           1）每5分钟观察1次 （事实获取备选赛事数据（赔率+数据）
           2）观察时符合2倍盈利条件、赔率稳定、数据上升的下注，根据2倍盈利条件数据大小决定注额大小， 分1.5-2.0，2.1-2.8，2.8以上3档
           3）70分钟前最多1注，70分钟后每次观测符合条件的再下1注，90分钟停止投注，下半场最多5注
         */

        MsTyLoBet1210 myTyBet = new MsTyLoBet1210();
        myTyBet.rollMsBetStart();

/*        String testString = "<script type=\"text/javascript\">var d=[];d[0]=[[['10022547','0','中国女子足球锦标赛|0','3644573','0','201807051400','Shaanxi Datai Zhicheng (女)','八一惠州瀛峰 (女)','0_2','0','3','32`','0_0','0_0','|0','1|0','Y','1','0','1','-0.2500','0-0.5|',,'0.9100|1.8100','0.7900|1.6900',,,'5','17','2.5000','2.5','1','0.7500|1.6500','0.9500|1.8500','1','0',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__N_90','0'],\n" +
                "[,,,,,,,,,,,,,,,,,,'0','16','-0.5000','0.5|',,'1.2500|2.1100','0.5000|1.4000',,,'5','3','2.7500','2.5-3',,'0.9900|1.8900','0.7100|1.6100',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__N_90','1'],\n" +
                "[,,,'3644574','0','201807051400','长春农商银行 (女)','大连权健 (女)','0_1','3','3','29`','0_0','0_0','|0','1|0','Y','1','0','1','0.2500','|0-0.5',,'0.7500|1.6500','0.9500|1.8500',,,'5','17','4.7500','4.5-5','1','0.8200|1.7200','0.8800|1.7800','0','1',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__Y_90','2'],\n" +
                "[,,,,,,,,,,,,,,,,,,'0','16','0.5000','|0.5','1','0.4500|1.3500','1.3300|2.1700','0','1','5','3','4.5000','4.5','1','0.6900|1.5900','1.0100|1.9100','1','0',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__Y_90','3']\n" +
                "],['40'],['10~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];d[1]=[[['12014138','0','新西兰全国男子篮球联赛|0','3644282','0','201807051500','超級城市突击队','南部鲨鱼','1_0_28_12_0_0_0_40_0_0_0_0_0_0_0_0_0|__4_0_','0_27_25_0_0_0_52_0_0_0_0_0_0_0_0_0|','12','03`_T','0_0','0_0','3644282|0','1|0','N','1','0','7','16.5000','|16.5','1','0.9500|','0.8100|','0','1','5','3','210.5000','210.5','1','0.6800|','0.9800|','0','1','3','5',,'0.9100|','0.9700|',,,,,,,,,,,'2','2','12.5000','|12.5','1','0.9500|','0.8100|','0','1',,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__','0'],\n" +
                "['12011836','0','菲律宾 PBA 发展联赛|0','3644467','0','201807051500','中央大学蝎子','Che Lu SSC-R 雄鹿','1_0_16_13_0_0_0_29_0_0_0_0_0_0_0_0_0|-__2_0_','0_23_21_0_0_0_44_0_0_0_0_0_0_0_0_0|-','12','03`','0_0','0_0','3644467|0','1|0','N','1','0','7','13.5000','|13.5','1','0.7000|','1.0000|','0','1','5','8','186.5000','186.5','1','1.0000|','0.6000|','1','0','3','5',,'0.9100|','0.9100|',,,,,,,,,,,'2','2','14.5000','|14.5','1','1.0500|','0.6500|','1','0',,,,,,,,,,,,,,,,,,,,,,,,,'0','0_0__','1']\n" +
                "],['29'],['12~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];parent.main.prepDtMulti(d);</script>";
        myTyBet.getMatchList(testString);*/
    }

    public void rollMsBetStart() throws Exception {
        while (true){
            try {
                logger.info("=================rollMsBetStart===================");
                //Cookie = prop.getString("Cookie");
                int rollTime = NumberUtils.toInt(prop.getString("rollTime"),30)*1000;
                String matchesStr = this.getMatchListStr();
                if(matchesStr==null){
                  //  int random =  RandomUtils.getRandom50000to99999();
                    logger.info("=================没有合适的比赛 sleep ==================="+rollTime+"毫秒");
                    //Thread.sleep(random);
                    Thread.sleep(rollTime);
                    continue;
                }
                this.getMatchList(matchesStr);
               // int random =  RandomUtils.getRandom5000to9999();
                logger.info("================轮询结束 sleep =================="+rollTime+"毫秒");
                // Thread.sleep(random*7);
                Thread.sleep(rollTime);

            }  catch (Exception e) {
                e.printStackTrace();
                logger.error("error",e);
                continue;
                //throw e;
            }

        }
    }

    public String bet(MsTyMatch msTyMatch)  {

        // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/TabBetSlipFrame.aspx");
        PostMethod method = new PostMethod(BetUrl);

        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
        method.setRequestHeader("Connection", "keep-alive");

        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setRequestHeader("Cookie",prop.getString("Cookie"));
/*        method.setRequestHeader("Host", "nss.m88ms.com");
        method.setRequestHeader("Origin", "https://nss.m88ms.com");
        method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/tabmenu.aspx?lid=zh-CN");*/

        method.setRequestHeader("Host", "www.ms88kr.com");
        method.setRequestHeader("Origin", "https://www.ms88kr.com");
        method.setRequestHeader("Referer", "https://www.ms88kr.com/Main/Sports/mSports/nss/tabmenu.aspx?lid=zh-CN");

        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent", Agent);

        int money = msTyMatch.getMoney();

        if(money<100){
            method.setRequestHeader("Content-Length", "111");
        }else if(money>=100&&money<1000){
            method.setRequestHeader("Content-Length", "112");
        }else if(money>=1000&&money<10000){
            method.setRequestHeader("Content-Length", "113");
        }else if(money>=10000){
            method.setRequestHeader("Content-Length", "114");
        }

//        STR:M_0_2H_3657433_28_0.520000_2_0.00000_003_002_100_     M_0_上半场_比赛编号_？_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:2
//        betLog:20180719122236612_R7GsHvqps00

        Map matchMap = new HashMap<String, String>();
        matchMap.put("STR", MatchDataUtils.getBetStr(msTyMatch, false));

        // matchMap.put("STR","M_0_1H_3660907_02_0.830000_2_0.00000_000_000_30_");
        //登录 Lspid:10_12_11_52
        // matchMap.put("Lspid", "10_12_11_52");
        matchMap.put("IsParlay", "false");
        matchMap.put("Mode", "2");  //21正在滚球 2是未开始
        // matchMap.put("dt", "11");
        matchMap.put("betLog", MatchDataUtils.uniqRand());

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(matchMap));

        long startTime = System.currentTimeMillis();

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;


        try {
            logger.info("===================bet http start======================");

            statusCode = httpClient.executeMethod(method);

            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            String result = method.getResponseBodyAsString();
            logger.info("||MatchNo:"+msTyMatch.getMatchNo()+"|bet result:" + result)
            ;
            logger.info("=========================bet http end=========================");

            if(result == null){
                return null;
            } else {
                return result;
            }

        } catch (Exception ex) {
            statusCode = 499;
            try {
                logger.info("调用http请求异常: " + method.getURI() + ",耗时：" + elapsedTime
                        + "ms, exception:" + ex.getMessage());
            } catch (URIException uriex) {
                logger.info("uriex:",uriex);
            }
            if (ex instanceof HttpInvokeException) {
                throw (HttpInvokeException) ex;
            } else {
                throw new HttpInvokeException(statusCode, ex);
            }
        } finally {
            method.releaseConnection();
        }

    }
    public String preBet(MsTyMatch msTyMatch)  {

        // PostMethod method = new PostMethod("https://nss.m88ms.com/nss/TabBetSlipFrame.aspx");
        PostMethod method = new PostMethod(BetUrl);

        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        method.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control", "max-age=0");
        method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Content-Length", "81");
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setRequestHeader("Cookie",prop.getString("Cookie"));

/*        method.setRequestHeader("Host", "nss.m88ms.com");
        method.setRequestHeader("Origin", "https://nss.m88ms.com");
        method.setRequestHeader("Referer", "https://nss.m88ms.com/nss/tabmenu.aspx?lid=zh-CN");*/

        method.setRequestHeader("Host", "www.ms88kr.com");
        method.setRequestHeader("Origin", "https://www.ms88kr.com");
        method.setRequestHeader("Referer", "https://www.ms88kr.com/Main/Sports/mSports/nss/tabmenu.aspx?lid=zh-CN");

        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("User-Agent",Agent);

//        STR:M_0_1H_3657302_30_0.950000_2_-0.2500_001_000_0_
        //    M_0_上半场_比赛编号_赔率前缀_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:1
//        betLog:

        Map matchMap = new HashMap<String, String>();
        matchMap.put("STR", MatchDataUtils.getBetStr(msTyMatch,true));
        //登录 Lspid:10_12_11_52
        // matchMap.put("Lspid", "10_12_11_52");
        matchMap.put("IsParlay", "false");
        matchMap.put("Mode", "1");  //21正在滚球 2是未开始
        // matchMap.put("dt", "11");
        matchMap.put("betLog","");


        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(matchMap));

        long startTime = System.currentTimeMillis();
        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {

            logger.info("===================preBet http start======================");

            statusCode = httpClient.executeMethod(method);

            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

            String result = method.getResponseBodyAsString();
            logger.info("||MatchNo:"+msTyMatch.getMatchNo()+"preBet result:" + result)
            ;
            logger.info("=========================preBet http end=========================");

            if(result == null){
                return null;
            } else {
                return result;
            }

        } catch (Exception ex) {
            statusCode = 499;
            try {
                logger.info("调用http请求异常: " + method.getURI() + ",耗时：" + elapsedTime
                        + "ms, exception:" + ex.getMessage());
            } catch (URIException uriex) {
                // ignore this exception
            }
            if (ex instanceof HttpInvokeException) {
                throw (HttpInvokeException) ex;
            } else {
                throw new HttpInvokeException(statusCode, ex);
            }
        } finally {
            method.releaseConnection();
        }

    }
    public String getMatchListStr()  {
        PostMethod listPostMethod = new PostMethod(ListUrl);

        listPostMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        listPostMethod.setRequestHeader(" Accept-Encoding", "gzip, deflate, br");
        listPostMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        listPostMethod.setRequestHeader("Cache-Control", "max-age=0");
        listPostMethod.setRequestHeader("Connection", "keep-alive");
        listPostMethod.setRequestHeader("Content-Length", "115");
        listPostMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        listPostMethod.setRequestHeader("Cookie",prop.getString("Cookie"));

/*        listPostMethod.setRequestHeader("Host", "nss.m88ms.com");
        listPostMethod.setRequestHeader("Origin", "https://nss.m88ms.com");*/

        listPostMethod.setRequestHeader("Host", "www.ms88kr.com");
        listPostMethod.setRequestHeader("Origin", "https://www.ms88kr.com");


        /*if("all".equalsIgnoreCase(prop.getString("Language"))){
            if("en".equalsIgnoreCase(saveLanguage)){
                listPostMethod.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=en-US&spid=10&spname=Soccer&dt=2");
                saveLanguage = "cn";
            }else {
                listPostMethod.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=zh-CN&spid=10&spname=Soccer&dt=2");
                saveLanguage = "en";
            }
        }else if("en".equalsIgnoreCase(prop.getString("Language"))) {
            listPostMethod.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=en-US&spid=10&spname=Soccer&dt=2");
        }else{
            listPostMethod.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=zh-CN&spid=10&spname=Soccer&dt=2");
        }*/
        //listPostMethod.setRequestHeader("Referer", "https://nss.m88ms.com/nss/main2.aspx?lid=zh-CN&spid=10&spname=Soccer&dt=2");

        listPostMethod.setRequestHeader("Referer", "https://www.ms88kr.com/Main/Sports/mSports/nss/main2.aspx?lid=zh-CN&spid=10&spname=Soccer&dt=2");

        listPostMethod.setRequestHeader("Upgrade-Insecure-Requests", "1");
        listPostMethod.setRequestHeader("User-Agent", Agent);

        Map matchMap = new HashMap<String, String>();
        matchMap.put("spid", "10");
        matchMap.put("ot", "2");
        matchMap.put("dt", "21");
        matchMap.put("vt", "0");
        matchMap.put("vs", "0");
        matchMap.put("tf", "0");
        matchMap.put("vd", "0");
        if("all".equalsIgnoreCase(prop.getString("Language"))){
            if("en".equalsIgnoreCase(saveLanguage)){
                matchMap.put("lid", "en");
                saveLanguage = "cn";
            }else {
                matchMap.put("lid", "ch");
                saveLanguage = "en";
            }
        }else if("en".equalsIgnoreCase(prop.getString("Language"))) {
            matchMap.put("lid", "en");
        }else{
            matchMap.put("lid", "ch");
        }
      // matchMap.put("lid", "ch");
       // matchMap.put("lid", "en");
        matchMap.put("lgnum", "999");
        matchMap.put("reqpart", "0");
        matchMap.put("verpart", "undefined");
        matchMap.put("prevParams", "undefined");
        matchMap.put("verpartLA", "");

        listPostMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        listPostMethod.setRequestBody(buildNameValuePair(matchMap));

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

    public void getMatchList(String matchListStr){
        logger.info("=========================getMatchList start=========================");
        logger.info(matchListStr.length()+"");
        String substring = StringUtils.substringBetween(matchListStr,"[[[","]\n],");
        if(StringUtils.isBlank(substring)){
            logger.info("*****==无比赛列表=*****");
            return;
        }

        //  String[] matchArray = StringUtils.split(substring,"],\n[");
        String[] matchArray = substring.split("],\n\\[");
        //logger.info("==msTyMatches==:"+msTyMatches.size()+"|"+msTyMatches.toString());
        logger.info("==msTyMatches==:"+msTyMatches.size()+"|");

        /*
        /0       ,1 ,2          ,3        ,4 , 5     , 6     , 7     , 8       , 9       ,10,11             ,12  ,13  , 14,15, 16    ,17,18,19,20  , 21      ,22,23             ,24             ,25,26,27,28,29,
        [联赛编号，0，联赛名称|0，赛事编号，0，时间戳，主队名，客队名，主队得分，客队得分，1，比赛已进行时间，0_0，0_0, |0,1|0,N（Y）, 1 ,0, 1,盘口，显示盘口，，全场上盘赔率|？，全场下盘赔率|?， ， ，5，3，全场大小盘盘口，
        全场大小盘显示盘口，1，大球赔率|？，小球赔率|？,,,,,,,,,,'1','7','1',胜赔|，平赔|，负赔|,'1|-1',,'2','2','0.0000','0|',,上半场胜赔率，上半场负赔率，
        ,,'6','4',上半场大小盘盘口，上半场大小盘显示盘口，，上半场大赔率|，半场小赔率|,,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

         /0       ,  1 ,            2          ,3        ,4 , 5            , 6                 , 7             , 8   , 9 ,10,  11  ,12   ,13   , 14 ,15   , 16,17 ,18, 19  , 20     , 21    ,22,23             ,24            ,25,26,27,28,29,
        ['10022547','0','中国女子足球锦标赛|0','3644665','0','201807051600','河北华夏幸福 (女)','河南徽商 (女)','0_1','1','1','41`','0_0','0_0','|0','1|0','Y','1','0','16','0.2500','|0-0.5',,'0.7700|1.6700','0.9300|1.8300',  ,  ,'5','3','3.7500',
        30    ,31,32            , 33            ,34,35,36,37,38,39,40,41,42,43 ,44 ,45 ,46       ,47       ,48       ,49    ,50,51 ,52 ,53      ,54 ,55,56             ,57             ,
        '3.5-4',,'0.6700|1.5700','1.0300|1.9300',  ,  ,  ,  ,  ,  ,  ,  ,  ,'1','7','1','3.2000|','2.8500|','2.2000|','1|-1',  ,'2','2','0.0000','0|', ,'1.0400|1.9400','0.6600|1.5600',
        58,59 ,60 ,61      ,62   ,63, 64            ,65             ,
        , ,'6','4','2.5000','2.5',  ,'2.1700|2.7800','0.1600|1.0600',,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

        [,,,,,,,,,,,,,,,,,,'0','21','2.2500','|2-2.5',,'1.2900|2.0600','0.6100|1.4400',,,'5','3','4.0000','4',,'1.0400|1.8800','0.7800|1.6200',,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,'8','0_0__Y_90','2'],
        [,,,'3644638','0','201807051630','泰国 U19','新加坡 U19','0_3','0','1','36`','0_0','0_0','|0','1|0','N','1','0','21','-1.5000','1.5|','1','0.8100|1.7600','1.0300|1.9800','0','1','5','22','5.5000','5.5','1','0.8200|1.7700','1.0000|1.9500','1','0',,,,,,,,,,,,,,,,'2','2','-0.2500','0-0.5|','1','1.2000|2.1300','0.6700|1.6200','1','0','6','4','3.5000','3.5','1','1.1900|2.1200','0.6600|1.6100','0','1',,,,,,,,,,,,,,,,'8','0_0__Y_90','3'],

        ['40'],['10~ch~AH_Live~Double~999~Hongkong~3~0~AllTime~Normal']];
         */

        for( int i = 0;i < matchArray.length;i++){
            //logger.info("matchArray["+i+"]:"+matchArray[i]);

            //去引号
            String matchArrayStr = StringUtils.remove(StringUtils.remove(matchArray[i],"'"),"`");

            String[]  matchProps = matchArrayStr.split(",");

            if(StringUtils.isNotBlank(matchProps[1])){
                this.prevType = matchProps[1];

                if(!matchProps[1].equals("0")) {
                   // logger.info("--------非联赛跳过--matchProps[1]:"+matchProps[1]);
                    continue;//非联赛跳过
                }
            } else {
                if(this.prevType!=null&&!this.prevType.equals("0")){
                  //  logger.info("-非联赛多个赔率跳过："+this.prevType+"|"+matchArray[i]);
                    continue;
                }
            }
            if(StringUtils.isBlank(matchProps[10])){//只赔率数组特殊处理  //只更新prevMsTyMatch;
                //logger.info("-多个赔率处理--");
                /*
                只赔率数组特殊处理
                 */
                if(NumberUtils.toFloat(matchProps[20])==0 ){
                    //    ||(NumberUtils.toFloat(matchProps[20])>0&&NumberUtils.toFloat(matchProps[20])<prevMsTyMatch.getHalfHandicap())){
                    prevMsTyMatch.setCapfix(matchProps[19]);
                    prevMsTyMatch.setHandicap(NumberUtils.toFloat(matchProps[20]));
                    prevMsTyMatch.setUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[23],"|")));
                    prevMsTyMatch.setDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[24],"|")));
                }

                if(StringUtils.isNotBlank(matchProps[56])) {
                    if(NumberUtils.toFloat(matchProps[53])==0){
                        //   ||(NumberUtils.toFloat(matchProps[53])>0&&NumberUtils.toFloat(matchProps[53])<prevMsTyMatch.getHalfHandicap())){
                        prevMsTyMatch.setHalfCapfix(matchProps[52]);
                        prevMsTyMatch.setHalfHandicap(NumberUtils.toFloat(matchProps[53]));
                        prevMsTyMatch.setHalfUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[56],"|")));
                        prevMsTyMatch.setHalfDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[57],"|")));
                    }
                }

                continue;
            }

            /*                                                                                                    红牌点球
             /0       ,1 ,2          ,3        ,4 , 5     , 6     , 7     , 8       , 9       ,10,11             ,12  ,13  , 14,15, 16    ,17,18,19,20  , 21      ,22,23             ,24             ,25,26,27,28,29,
            [联赛编号，0，联赛名称|0，赛事编号，0，时间戳，主队名，客队名，主队得分，客队得分，1，比赛已进行时间，0_0，0_0, |0,1|0,N（Y）, 1 ,0, 1,盘口，显示盘口，，全场上盘赔率|？，全场下盘赔率|?， ， ，5，3，全场大小盘盘口，
            全场大小盘显示盘口，1，大球赔率|？，小球赔率|？,,,,,,,,,,'1','7','1',胜赔|，平赔|，负赔|,'1|-1',,'2','2','0.0000','0|',,上半场胜赔率，上半场负赔率，
            ,,'6','4',上半场大小盘盘口，上半场大小盘显示盘口，，上半场大赔率|，半场小赔率|,,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

             /0       ,  1 ,            2          ,3        ,4 , 5            , 6                 , 7             , 8   , 9 ,10,  11  ,12   ,13   , 14 ,15   , 16,17 ,18, 19  , 20     , 21    ,22,23             ,24            ,25,26,27,28,29,
            ['10022547','0','中国女子足球锦标赛|0','3644665','0','201807051600','河北华夏幸福 (女)','河南徽商 (女)','0_1','1','1','41`','0_0','0_0','|0','1|0','Y','1','0','16','0.2500','|0-0.5',,'0.7700|1.6700','0.9300|1.8300',  ,  ,'5','3','3.7500',
            30    ,31,32            , 33            ,34,35,36,37,38,39,40,41,42,43 ,44 ,45 ,46       ,47       ,48       ,49    ,50,51 ,52 ,53      ,54 ,55,56             ,57             ,
            '3.5-4',,'0.6700|1.5700','1.0300|1.9300',  ,  ,  ,  ,  ,  ,  ,  ,  ,'1','7','1','3.2000|','2.8500|','2.2000|','1|-1',  ,'2','2','0.0000','0|', ,'1.0400|1.9400','0.6600|1.5600',
            58,59 ,60 ,61      ,62   ,63, 64            ,65             ,
            , ,'6','4','2.5000','2.5',  ,'2.1700|2.7800','0.1600|1.0600',,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

             */
            MsTyMatch msTyMatch = new MsTyMatch();
            if(StringUtils.isNotBlank(matchProps[2])){ //联赛名称
                msTyMatch.setLeagueName(StringUtils.substringBefore(matchProps[2], "|"));
            } else if  (prevMsTyMatch != null){
                msTyMatch.setLeagueName(prevMsTyMatch.getLeagueName());
            }
//            if (msTyMatch.getLeagueName().equals("梦幻对垒")){
//                logger.info("梦幻对垒跳过");
//                continue;
//            }
            msTyMatch.setMatchNo(NumberUtils.toInt(matchProps[3]));
            msTyMatch.setMatchDate(matchProps[5]);
            msTyMatch.setMasterTeamName(MatchDataUtils.cleanTeamName(matchProps[6]));
            msTyMatch.setGuestTeamName(MatchDataUtils.cleanTeamName(matchProps[7]));
            msTyMatch.setMasterGoalNum(NumberUtils.toInt(StringUtils.substringAfter(matchProps[8],"_")));
            msTyMatch.setGuestGoalNum(NumberUtils.toInt(matchProps[9]));
            msTyMatch.setMatchStep(NumberUtils.toInt(matchProps[10]));
            msTyMatch.setMatchTime(NumberUtils.toInt(matchProps[11]));
            msTyMatch.setCapfix(matchProps[19]);
            msTyMatch.setHandicap(NumberUtils.toFloat(matchProps[20]));
            msTyMatch.setUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[23],"|")));
            msTyMatch.setDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[24],"|")));
            if(StringUtils.isNotBlank(matchProps[56])) {
                msTyMatch.setHalfCapfix(matchProps[52]);
                msTyMatch.setHalfHandicap(NumberUtils.toFloat(matchProps[53]));
                msTyMatch.setHalfUpperOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[56],"|")));
                msTyMatch.setHalfDownOdds(NumberUtils.toFloat(StringUtils.substringBefore(matchProps[57],"|")));
            }else if(msTyMatch.getMatchStep()==1 && msTyMatch.getMatchTime()<40){
/*                logger.error("msTyMatch:"+msTyMatch);
                logger.error("matchProps[52]:"+matchProps[52]);
                logger.error("matchProps[53]:"+matchProps[53]);
                logger.error("matchProps[56]:"+matchProps[56]);
                logger.error("matchProps[57]:"+matchProps[57]);*/
            }

            if(NumberUtils.toInt(matchProps[12].replaceAll("[^(1-9)]", ""), 0)>0
              ||NumberUtils.toInt(matchProps[13].replaceAll("[^(1-9)]", ""), 0)>0){
                msTyMatch.setHasRedCard(true);
            }

       //     logger.info("msTyMatch:"+msTyMatch);

            //测试
            if(prop.getString("testBet").equals("1")){
                msTyMatch.setBetH(true);
                msTyMatch.setMoney(RandomUtils.getRandom10to25());
                msTyMatch.setBetOdds(MatchDataUtils.getOdds(msTyMatch));
                String _preResult = this.bet(msTyMatch);
                logger.info("testBet_preResult："+_preResult);
                break;
            }

            Date _a = new Date();

            if(NumberUtils.toInt(prop.getString("timeBet"),0)>0&&((_a.getTime() - _date.getTime())/1000>NumberUtils.toInt(prop.getString("timeBet"),40)*60-(RandomUtils.getRandom5000to9999())/12.5)){
                msTyMatch.setBetH(true);
               // msTyMatch.setMoney(RandomUtils.getRandom10to25());
               // msTyMatch.setMoney(100);
                msTyMatch.setMoney(NumberUtils.toInt(prop.getString("Money"),50)/10);
                msTyMatch.setBetOdds(MatchDataUtils.getOdds(msTyMatch));
                String _preResult = this.bet(msTyMatch);
                if(_preResult==null){
                    logger.info("定时投注失败_preResult is null",_preResult);
                }else if(MatchDataUtils.parseBetResultCode(_preResult)==0
                        || MatchDataUtils.parseBetResultCode(_preResult)==401){
                    logger.info("定时投注成功_preResult："+_preResult);
                    _date = _a;
                }else {
                    logger.info("定时投注失败_preResult："+_preResult);
                }
                break;
            }

            if (prevMsTyMatch == null) {
                prevMsTyMatch = msTyMatch;
                continue;
            }

            //如果已经入库，则5分钟一次更新数据     上一次数据
            if(msTyMatches.containsKey(prevMsTyMatch.getMatchNo())){
                prevMsTyMatch.setBetMoney(0);

                if(prevMsTyMatch.getMatchStep()==2){
                    logger.info("半场休息跳过不处理："+prevMsTyMatch);
                    prevMsTyMatch = msTyMatch;
                    continue;
                }

                if(MatchDataUtils.getOdds(prevMsTyMatch)<0.1){
                    logger.info("错误的odds，跳过："+prevMsTyMatch);
                    prevMsTyMatch = msTyMatch;
                    continue;
                }
                if(prevMsTyMatch.isHasRedCard()){
                    msTyMatches.remove(prevMsTyMatch.getMatchNo());
                    MsTyMatchDao.getInstance().updataStatus(prevMsTyMatch.getMatchNo(),2);
                    logger.info("有红牌出库："+prevMsTyMatch);
                    prevMsTyMatch = msTyMatch;
                    continue;
                }
                MsTyMatch oldMsTyMatch =  msTyMatches.get(prevMsTyMatch.getMatchNo());
                logger.info("已经入库，oldMsTyMatch："+oldMsTyMatch);

                if((prevMsTyMatch.getMatchStep()==1&&prevMsTyMatch.getHalfHandicap()!=oldMsTyMatch.getHalfHandicap())
                        ||(prevMsTyMatch.getMatchStep()==3&&prevMsTyMatch.getHandicap()!=oldMsTyMatch.getHandicap())){
                    msTyMatches.remove(prevMsTyMatch.getMatchNo());
                    MsTyMatchDao.getInstance().updataStatus(prevMsTyMatch.getMatchNo(),2);
                    logger.info("水位变化，出库prevMsTyMatch："+prevMsTyMatch);
                    prevMsTyMatch = msTyMatch;
                    continue;
                }

                int obeTime = NumberUtils.toInt(prop.getString("obeTime"),3);
                if(prevMsTyMatch.getMatchTime()-oldMsTyMatch.getMatchTime()>=obeTime){//小于间隔时间不处理
                    logger.info(prevMsTyMatch.getMatchNo()+" |"+obeTime+"分钟观察："+ ( prevMsTyMatch.getMatchStep()==1?"上半场":"下半场" )
                            + prevMsTyMatch.getMatchTime()+"分钟|"+prevMsTyMatch.getMasterTeamName()+"-"+prevMsTyMatch.getGuestTeamName()+"|"+prevMsTyMatch.getMasterGoalNum()+":"+prevMsTyMatch.getGuestGoalNum());
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    String matchDataContain = MatchDataUtils.getMatchData(prevMsTyMatch.getMasterTeamName(), prevMsTyMatch.getGuestTeamName());
                    if(StringUtils.isBlank(matchDataContain)){
                        logger.info(obeTime+"分钟观察内比赛数据matchDataContain为空prevMsTyMatch："+prevMsTyMatch);
                        prevMsTyMatch = msTyMatch;
                        continue;
                    }
                    prevMsTyMatch.setMatchData(matchDataContain);
                    if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()){
                        prevMsTyMatch.setBetH(true);
                    }else {
                        prevMsTyMatch.setBetH(false);
                    }

                    /*if(StringUtils.isBlank(oldMsTyMatch.getBaseMatchData())){
                       // prevMsTyMatch.setBaseTime(oldMsTyMatch.getMatchTime());
                        prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                        prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());
                        //oldMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                        MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                        logger.info("第一次"+obeTime+"分钟观察,设置BaseMatchData,不投注"+prevMsTyMatch);
                        prevMsTyMatch = msTyMatch;
                        continue;
                    }*/

                    if(oldMsTyMatch.getMasterGoalNum() == prevMsTyMatch.getMasterGoalNum() && oldMsTyMatch.getGuestGoalNum() == prevMsTyMatch.getGuestGoalNum()){
                        logger.info("比分没变化prevMsTyMatch："+prevMsTyMatch);
                    } else {
                        // msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                     /*   if(MatchDataUtils.isMastLead(prevMsTyMatch)==MatchDataUtils.isMastLead(oldMsTyMatch)){//扩大优势不出库{

                            if(MatchDataUtils.getOdds(prevMsTyMatch)<0.1){
                                logger.info("############比分变化,领先没变，更新数据,进球后数据未更新,跳过："+ prevMsTyMatch.toString());
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }
             *//*               if(prevMsTyMatch.getMatchTime()-oldMsTyMatch.getBaseTime()>10){
                                prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            }else {
                                prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getBaseMatchData());
                            }*//*

                            prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                            MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                            logger.info("############比分变化,领先没变，更新数据："+ prevMsTyMatch.toString());
                        }else {
                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            MsTyMatchDao.getInstance().updataStatus(prevMsTyMatch.getMatchNo(),2);
                            logger.info("############比分变化，出库："+ prevMsTyMatch.toString());
                        }*/

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        MsTyMatchDao.getInstance().updataStatus(prevMsTyMatch.getMatchNo(),2);
                        logger.info("############比分变化，出库："+ prevMsTyMatch.toString());

                        prevMsTyMatch = msTyMatch;
                        continue;
                    }

                        float betOdds =  MatchDataUtils.getOdds(prevMsTyMatch);
                        double secondPre = MatchDataUtils.assessMatchData(prevMsTyMatch)*betOdds;
                        float baseBetOdds =  oldMsTyMatch.getBaseOdds();
                        float oldBetOdds =  MatchDataUtils.getOdds(oldMsTyMatch);

                        logger.info("| "+prevMsTyMatch.getMatchNo()+" |secondPre："+secondPre+"|baseBetOdds:"+baseBetOdds+"|oldBetOdds:"+oldBetOdds+"|betOdds:"+betOdds);

                        if((secondPre>=2.5 && prevMsTyMatch.getMatchStep() == 1 && prevMsTyMatch.getMatchTime()>= (NumberUtils.toInt(prop.getString("InStartTime"),20) - 8 )&& betOdds>=baseBetOdds && betOdds>=oldBetOdds)
                                ||(secondPre>=2.5 && prevMsTyMatch.getMatchStep() == 3 && prevMsTyMatch.getMatchTime()>= (NumberUtils.toInt(prop.getString("SecInStartTime"),20) - 8 ) && betOdds>=baseBetOdds && betOdds>=oldBetOdds)){

                            logger.info("优势secondPre,可提前投注secondPre|prevMsTyMatch:"+secondPre+"|"+prevMsTyMatch.getMatchNo());

                        }else if((secondPre>=2.0 && prevMsTyMatch.getMatchStep() == 1 && prevMsTyMatch.getMatchTime()>= (NumberUtils.toInt(prop.getString("InStartTime"),20) - 5 ) && betOdds>=baseBetOdds && betOdds>=oldBetOdds)
                                ||(secondPre>=2.0 && prevMsTyMatch.getMatchStep() == 3 && prevMsTyMatch.getMatchTime()>= (NumberUtils.toInt(prop.getString("SecInStartTime"),20) - 5 ) && betOdds>=baseBetOdds && betOdds>=oldBetOdds)){

                            logger.info("优势secondPre,可提前投注secondPre|prevMsTyMatch:"+secondPre+"|"+prevMsTyMatch.getMatchNo());

                        }else if((prevMsTyMatch.getMatchStep() == 1 && prevMsTyMatch.getMatchTime()<NumberUtils.toInt(prop.getString("InStartTime"),20))
                                ||(prevMsTyMatch.getMatchStep() == 3 && prevMsTyMatch.getMatchTime()<NumberUtils.toInt(prop.getString("SecInStartTime"),20))){

                            prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                            MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                            logger.info("比赛时间不足,更新数据不投注prevMsTyMatch"+prevMsTyMatch);
                            prevMsTyMatch = msTyMatch;
                            continue;
                        }

                    if(MatchDataUtils.validateMatchData(prevMsTyMatch.getMatchData(),5)==false){
                        prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                        prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                        MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                        logger.info("比赛数据过少,更新数据不投注prevMsTyMatch"+prevMsTyMatch);
                        prevMsTyMatch = msTyMatch;
                        continue;
                    }

                    //数据库取baseData
                    int baseTime = NumberUtils.toInt(prop.getString("baseTime"),10);
                    String baseData =   MsTyMatchDao.getInstance().selectMatchData(prevMsTyMatch.getMatchNo(),prevMsTyMatch.getMatchTime()-baseTime,prevMsTyMatch.getMatchStep());
                    //logger.info("prevMsTyMatch设置baseData："+baseData+"|"+prevMsTyMatch.toString());
                    if(baseData == null){
                        logger.info("baseData为空更新数据不投注："+baseData+"|"+prevMsTyMatch.toString());
                        prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                        prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                        MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                        prevMsTyMatch = msTyMatch;
                        continue;
                    }

                    oldMsTyMatch.setBaseMatchData(baseData);

                    if(NumberUtils.toInt(prop.getString("noBase"),0)==1){
                      //  oldBetOdds=0;
                        baseBetOdds=0;
                    }

                    boolean testPolicy = false;

                    logger.info("| "+prevMsTyMatch.getMasterTeamName()+" | "+prevMsTyMatch.getMatchNo()+" |baseData："+baseData
                            + "|oldMsTyMatch.getMatchData:"+oldMsTyMatch.getMatchData()
                            + "|prevMsTyMatch.getMatchData:"+prevMsTyMatch.getMatchData());

                    if(NumberUtils.toInt(prop.getString("policy"),1)==1){

                        if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && betOdds-oldBetOdds>=MatchDataUtils.canUp(oldBetOdds)
                                && betOdds-baseBetOdds>=MatchDataUtils.canUp(baseBetOdds)
                                && secondPre>=1.3
                                ){
                            logger.info("oldBetOdds:"+oldBetOdds+"-canUP oldBetOdds:"+MatchDataUtils.canUp(oldBetOdds)+"|baseBetOdds:"+baseBetOdds+"-canUP baseBetOdds:"+MatchDataUtils.canUp(baseBetOdds)+"|"+prevMsTyMatch.getMatchNo());
                            logger.info("唯有二倍定律canUP,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
/*                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && betOdds-oldBetOdds>-0.05
                                && betOdds-baseBetOdds>=-0.05
                                && secondPre>=1.3
                                ){
                            logger.info("唯有二倍定律,射门上升，赔率稳定,,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());*/
//                        } else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
//                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
//                                && betOdds>0.83
//                                && betOdds-oldBetOdds>0
//                                && betOdds-baseBetOdds>0.4
//                                && baseBetOdds<0.8
//                                && secondPre>=1.3
//                                ){
//                            logger.info("唯有二倍定律,连续升盘,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
/*                        } else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && betOdds-oldBetOdds>-0.1
                                && betOdds-baseBetOdds>=-0.1
                                && secondPre>=1.5
                                ){
                            logger.info("唯有二倍定律,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());*/
                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                            && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                            && betOdds>0.83
                            && betOdds-oldBetOdds>-0.15
                            && betOdds-baseBetOdds>=-0.15
                            && secondPre>=2
                            && betOdds>=1.35
                            ){
                            logger.info("唯有二倍定律高second高赔率,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && betOdds-oldBetOdds>0
                                && betOdds-baseBetOdds>=0
                                && secondPre>=1.175
                                ){
                            logger.info("二倍定律,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        } else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && betOdds-oldBetOdds>=0
                                && betOdds-baseBetOdds>=0
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                ){
                            logger.info("0 小优势比赛,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
/*                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                               // && MatchDataUtils.isOddsScope(prevMsTyMatch, oldMsTyMatch)
                                && betOdds>0.83
                                && betOdds-oldBetOdds>-0.05
                                && betOdds-baseBetOdds>-0.1
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                        ){
                            logger.info("1 小优势比赛,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());*/
                        }else if( betOdds>=2.3
                                &&prevMsTyMatch.getMatchTime()>=35
                                && secondPre>=1
                                && (MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1 || MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1)){
                            logger.info("2 最后10分钟,超高赔率,射门上升，开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());

                        }else if(MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=0
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                               // && MatchDataUtils.isOddsScopeUp(prevMsTyMatch, oldMsTyMatch,0.09)
                                && NumberUtils.toInt(prop.getString("noBase"),0)==0
                                && betOdds>0.83
                                && betOdds-oldBetOdds>0.09
                                && betOdds-baseBetOdds>0
                                && secondPre>=1.2
                                && prevMsTyMatch.getMatchTime()>=25
                                &&(MatchDataUtils.assessMatchData(prevMsTyMatch)>=1 || (MatchDataUtils.assessMatchData(prevMsTyMatch)>0.7&&betOdds>=1.77))
                                ){
                            logger.info("3 数据稳定,赔率大幅提升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                            testPolicy = true;
                        }else if(betOdds>1.57
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=0.8
                                && betOdds-oldBetOdds>-0.1
                                && betOdds-baseBetOdds>-0.1
                                ){
                            logger.info("4 高赔率比赛,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if(betOdds>1.29
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1
                                && betOdds-oldBetOdds>-0.1
                                && betOdds-baseBetOdds>-0.1
                                && prevMsTyMatch.getMatchTime()>=25
                                ){
                            logger.info("4.5 中赔率比赛,射门上升,70开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( betOdds>0.83
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1.45
                                && betOdds-oldBetOdds>-0.055
                                && betOdds-baseBetOdds>-0.1
                                ){
                            logger.info("5 优势明显,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( betOdds>0.83
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=3
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=2
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1.1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && betOdds-oldBetOdds>-0.1
                                && betOdds-baseBetOdds>-0.1
                                ){
                            logger.info("6 占优比赛,攻势小波..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( betOdds>0.9
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=0
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=0
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1.35
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && betOdds-oldBetOdds>=0
                                && betOdds-baseBetOdds>=0
                                && prevMsTyMatch.getMatchTime()>=35
                                ){
                            logger.info("7 优势比赛,后10分钟加注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                            testPolicy = true;
                        }else if( betOdds>0.9
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=0
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=0
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1
                                && betOdds-oldBetOdds>=MatchDataUtils.canUp(oldBetOdds)
                                && betOdds-baseBetOdds>=MatchDataUtils.canUp(baseBetOdds)
                                && prevMsTyMatch.getMatchTime()>=30
                                && secondPre>=1.5
                                ){
                            logger.info("8 高second,后15分钟加注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                            testPolicy = true;
                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && (MatchDataUtils.assessMatchData(prevMsTyMatch)>=1 ||  secondPre>=2)
                                && betOdds>=0.83
                                && betOdds-oldBetOdds>=MatchDataUtils.canUp(oldBetOdds)
                                && betOdds-baseBetOdds>=MatchDataUtils.canUp(baseBetOdds)
                                && secondPre>=1.2
                                ){
                            logger.info("only second,shoot up,bet..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else{
                            prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                            MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                            logger.info("不满足投注条件,更新数据!prevMsTyMatch"+prevMsTyMatch);
                            prevMsTyMatch = msTyMatch;
                            continue;
                      }
                    }else if(NumberUtils.toInt(prop.getString("policy"),1)==2){
                       if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && (MatchDataUtils.assessMatchData(prevMsTyMatch)>=1 ||  secondPre>=2)
                                && betOdds>=0.83
                                && betOdds-oldBetOdds>=MatchDataUtils.canUp(oldBetOdds)
                                && betOdds-baseBetOdds>=MatchDataUtils.canUp(baseBetOdds)
                                && secondPre>=1.2
                                ){
                            logger.info("only second,shoot up,bet..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else{
                            prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                            MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                            logger.info("不满足投注条件,更新数据!prevMsTyMatch"+prevMsTyMatch);
                            prevMsTyMatch = msTyMatch;
                            continue;
                        }
                    }else {
                        if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && MatchDataUtils.assessMatchData(prevMsTyMatch)>=1.35
                                && MatchDataUtils.assessMatchDataOnlyShoot(prevMsTyMatch)>=1.35
                                && betOdds>0.83
                                ){
                            logger.info("策略3,优势比赛，射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && prevMsTyMatch.getMatchTime()>30
                                && betOdds>=2
                                ){
                            logger.info("策略3,高赔比赛，射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else if( MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch)>=1
                                && MatchDataUtils.compOnlyShoot(prevMsTyMatch,oldMsTyMatch.getBaseMatchData())>=1
                                && betOdds>0.83
                                && secondPre>=1.5
                                ){
                            logger.info("策略3,1.5倍定律,射门上升,开始投注..."+ prevMsTyMatch.getMasterTeamName()+"|"+prevMsTyMatch.getMatchNo());
                        }else{
                            prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                            prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                            msTyMatches.remove(prevMsTyMatch.getMatchNo());
                            msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                            MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                            logger.info("不满足投注条件,更新数据!prevMsTyMatch"+prevMsTyMatch);
                            prevMsTyMatch = msTyMatch;
                            continue;
                        }
                    }

                            //int second =(int)Math.ceil( MatchDataUtils.assessMatchData(prevMsTyMatch)* MatchDataUtils.getOdds(prevMsTyMatch));
                            //double second = MatchDataUtils.assessMatchData(prevMsTyMatch)* MatchDataUtils.getOdds(prevMsTyMatch);

                            Long betMoney = (Math.round((NumberUtils.toInt(prop.getString("Money"),100)*secondPre)/100))*100;
                            logger.info(prevMsTyMatch.getMasterTeamName()+"| "+prevMsTyMatch.getMatchNo()+"############second："+secondPre+"|"+"############betMoney："+betMoney);

                            prevMsTyMatch.setMoney(betMoney.intValue());

                        if((prevMsTyMatch.getLeagueName().contains("ENGLISH")||prevMsTyMatch.getLeagueName().contains("英格兰"))&&NumberUtils.toInt(prop.getString("passEnglish"),0)==1){
                            prevMsTyMatch.setMoney(prevMsTyMatch.getMoney()/5);
                            logger.info("ENGLISH|英格兰 money/5:" + prevMsTyMatch.getMasterTeamName() + " | " + prevMsTyMatch.getMatchNo() +" | money:"+prevMsTyMatch.getMoney());
                        }

                        if(testPolicy == true && NumberUtils.toInt(prop.getString("testPolicy"),1) == 1 ){
                            prevMsTyMatch.setMoney(prevMsTyMatch.getMoney()/2);
                            logger.info("testPolicy is true money/2:" + prevMsTyMatch.getMasterTeamName() + " | " + prevMsTyMatch.getMatchNo() +" | money:"+prevMsTyMatch.getMoney());

                        }

                    if(NumberUtils.toInt(prop.getString("testPolicy"),1) == 1 && prevMsTyMatch.getMatchData().lastIndexOf("30:30")>6){
                        prevMsTyMatch.setMoney(prevMsTyMatch.getMoney()/2);
                        logger.info("30:30 is true money/2:" + prevMsTyMatch.getMasterTeamName() + " | " + prevMsTyMatch.getMatchNo() +" | money:"+prevMsTyMatch.getMoney());

                    }

                    //betMoney
                    int betMoney_finish =  MsTyMatchDao.getInstance().getBetMoney(prevMsTyMatch.getMatchNo(),prevMsTyMatch.getMatchStep());
                    int maxMoneyProp = NumberUtils.toInt(prop.getString("maxMoney"),10000);

                    if(maxMoneyProp-betMoney_finish<10){
                        logger.info("投注余额小于10，更新数据不投注:" + prevMsTyMatch.getMasterTeamName() + " | " + prevMsTyMatch.getMatchNo() +" | prevMsTyMatch.getBetMoney(): " + prevMsTyMatch.getBetMoney()
                                +" | maxMoneyProp:"+maxMoneyProp+" | betMoney_finish:"+betMoney_finish);

                        prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                        prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                        MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                        prevMsTyMatch = msTyMatch;
                        continue;

                    }

                    if(prevMsTyMatch.getMoney()>(maxMoneyProp-betMoney_finish)){
                        prevMsTyMatch.setMoney(maxMoneyProp-betMoney_finish);
                    }


                            prevMsTyMatch.setBetOdds(MatchDataUtils.getOdds(prevMsTyMatch));

                            String prebetResultStr = this.preBet(prevMsTyMatch);
                            int resCode = MatchDataUtils.parseBetResultCode(prebetResultStr);

                            //float cap_  =  MatchDataUtils.parseBetResultHandicap(prebetResultStr, 0);

                            float odds_ = 0;

                            if(resCode==-419){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |logged out end");
                                System.exit(0);
                            }else if(resCode==-416){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |Your account is disabled!");
                                System.exit(0);
                            }else if(resCode==402){
                                odds_ =MatchDataUtils.parseBetResultOdds(prebetResultStr, 0);
                            }else  if(resCode==0){
                                odds_ = MatchDataUtils.getOdds(prevMsTyMatch);
                            }else{
                                logger.info("| "+prevMsTyMatch.getMatchNo()+"|赔率更新,跳过:prebetResultStr:" +prebetResultStr);
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }

                            double canUP = MatchDataUtils.canUp(betOdds);

                            if(odds_-MatchDataUtils.getOdds(prevMsTyMatch)<canUP){
                                logger.info("赔率变化过大,继续观察| odds_:"+odds_+"-canUP:"+canUP+"|原:betOdds"+ betOdds +"|"+ prevMsTyMatch.getMasterTeamName()+"-"+prevMsTyMatch.getMatchNo() );
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }

                            prevMsTyMatch.setBetOdds(odds_);

                            int maxMoney = MatchDataUtils.parseBetMoney(prebetResultStr);

                            prevMsTyMatch.setMaxMoney(maxMoney);

                            String betResult = "";

                           /* if(prevMsTyMatch.getMoney() > maxMoney){
                                Long oneMoney = Math.round(maxMoney*0.95/100)*100;
                                prevMsTyMatch.setMoney(oneMoney.intValue());
                            }

                            betResult = this.bet(prevMsTyMatch);*/

                             // 分额
                            int zbBetMoney = prevMsTyMatch.getMoney();

                            if(prevMsTyMatch.getMoney() > maxMoney){

                                Long oneMoney = Math.round(maxMoney*0.9/100)*100;
                                int betMoneySur = prevMsTyMatch.getMoney();

                                boolean isContinue = false;

                                int m=0;
                                while (betMoneySur>0 && m<30){
                                    if(betMoneySur<oneMoney.intValue()){
                                        prevMsTyMatch.setMoney(betMoneySur);
                                    }else {
                                        prevMsTyMatch.setMoney(oneMoney.intValue());
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }
                                    betResult = this.bet(prevMsTyMatch);

                                   if(MatchDataUtils.parseBetResultCode(betResult)==-416){
                                        logger.info("| "+prevMsTyMatch.getMatchNo()+" |Your account is disabled!",betResult);
                                        System.exit(0);
                                   } else if(MatchDataUtils.parseBetResultCode(betResult)==-419){
                                        logger.info("| "+prevMsTyMatch.getMatchNo()+" |logged out end",betResult);
                                        System.exit(0);
                                   }

                                  //  logger.info("| "+prevMsTyMatch.getMatchNo()+" |注额过大,分额投注："+oneMoney+"|"+betResult);
                                    int ii=0;
                                    while (MatchDataUtils.parseBetResultCode(betResult)==402){
                                        betResult = this.oddUpBet(betResult);
                                        if(ii++>4)break;
                                    }

                                        if(MatchDataUtils.parseBetResultCode(betResult)==401||MatchDataUtils.parseBetResultCode(betResult)==0){
                                                m++;
                                                betMoneySur = betMoneySur - oneMoney.intValue();
                                                try {
                                                    Thread.sleep(2500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                                }
                                                String prebetResultStr_in = this.preBet(prevMsTyMatch);
                                                int resCode_in = MatchDataUtils.parseBetResultCode(prebetResultStr_in);
                                                float odds_in ;
                                                if(resCode_in==402){
                                                    odds_in =MatchDataUtils.parseBetResultOdds(prebetResultStr_in, 0);
                                                }else  if(resCode_in==0){
                                                    odds_in = MatchDataUtils.getOdds(prevMsTyMatch);
                                                }else{
                                                    logger.info("| "+prevMsTyMatch.getMatchNo()+" |分额投注赔率更新,跳过:prebetResultStr_in:" +prebetResultStr_in +"|"+prevMsTyMatch.toString());
                                                    isContinue = true;
                                                    break;
                                                }
                                                double canUP_in = MatchDataUtils.canUp(betOdds);
                                                if(odds_in-betOdds<canUP_in){
                                                    logger.info("分额投注赔率变化过大,继续观察| odds_in:"+odds_in+"-canUP_in:"+canUP_in+"|原:betOdds"+ betOdds +"|"+ prevMsTyMatch.getMasterTeamName()+"-"+prevMsTyMatch.getMatchNo() );
                                                    isContinue = true;
                                                    break;
                                                }
                                                prevMsTyMatch.setBetOdds(odds_in);

                                        }else if(MatchDataUtils.parseBetResultCode(betResult)==-405){
                                            try {
                                                Thread.sleep(6000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                            }

                                        }else {
                                            logger.info("| "+prevMsTyMatch.getMatchNo()+" |投注失败,分额投注中断："+oneMoney+"|"+betResult);
                                            if(m>0)isContinue=true;
                                            break;
                                        }
                                }

                                if(isContinue){
                                    //更新数据
                                    prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                                    prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                                    prevMsTyMatch.setBetMoney(zbBetMoney-betMoneySur);

                                    msTyMatches.remove(prevMsTyMatch.getMatchNo());
                                    msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                                    MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                                    logger.info("| "+prevMsTyMatch.getMatchNo()+" |分额投注 部分投注成功，更新数据!prevMsTyMatch"+prevMsTyMatch);

                                    prevMsTyMatch = msTyMatch;
                                    continue;
                                }else {
                                    if(MatchDataUtils.parseBetResultCode(betResult)==401||MatchDataUtils.parseBetResultCode(betResult)==0){
                                        logger.info("| "+prevMsTyMatch.getMatchNo()+" |分额投注全部成功： betResult："+betResult);
                                        prevMsTyMatch.setBetMoney(zbBetMoney);
                                    }else {
                                        logger.info("| "+prevMsTyMatch.getMatchNo()+" |分额投注全部失败,不更新数据，重新观察： betResult："+betResult);
                                        if(MatchDataUtils.parseBetResultCode(betResult)==-404){
                                            logger.info("| "+prevMsTyMatch.getMatchNo()+" |钱不够，休息5分钟："+betResult);
                                            try {
                                                Thread.sleep(1000*60*5);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                            }
                                        }
                                        prevMsTyMatch = msTyMatch;
                                        continue;
                                    }
                                }

                            } else {
                                betResult = this.bet(prevMsTyMatch);
                            }

                            // logger.info("preResult："+preResult);
                            if(StringUtils.isBlank(betResult)){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |preResult is null",betResult);
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==-419){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |logged out end",betResult);
                                System.exit(0);
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==-416){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |Your account is disabled!",betResult);
                                System.exit(0);
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==-408){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |注额过大："+betResult);
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==0){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |投注成功0："+betResult);
                                prevMsTyMatch.setBetMoney(zbBetMoney);
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==401){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |投注成功："+betResult);
                                prevMsTyMatch.setBetMoney(zbBetMoney);
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==-404){
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |钱不够，休息5分钟："+betResult);
                                try {
                                    Thread.sleep(1000*60*5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }else if(MatchDataUtils.parseBetResultCode(betResult)==402){
                                String rs = this.oddUpBet(betResult);
                                int iii=0;
                                while (MatchDataUtils.parseBetResultCode(rs)==402){
                                    rs = this.oddUpBet(rs);
                                    if(iii++>6)break;
                                }


                                if(MatchDataUtils.parseBetResultCode(rs)==401||MatchDataUtils.parseBetResultCode(rs)==0){
                                    prevMsTyMatch.setBetMoney(zbBetMoney);
                                } else {
                                    prevMsTyMatch = msTyMatch;
                                    continue;
                                }

                            }else {
                                logger.info("| "+prevMsTyMatch.getMatchNo()+" |preResult is error",betResult);
                                prevMsTyMatch = msTyMatch;
                                continue;
                            }

                        //更新数据
                        prevMsTyMatch.setBaseMatchData(oldMsTyMatch.getMatchData());
                        prevMsTyMatch.setBaseOdds(oldMsTyMatch.getBaseOdds());

                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                        MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                        logger.info("更新数据!prevMsTyMatch"+prevMsTyMatch);


                }else if (prevMsTyMatch.getMatchTime()-oldMsTyMatch.getMatchTime() < 0 ){
                    oldMsTyMatch.setMatchTime(0);
                    if(prevMsTyMatch.getHandicap()!=0){
                        msTyMatches.remove(prevMsTyMatch.getMatchNo());
                        MsTyMatchDao.getInstance().updataStatus(prevMsTyMatch.getMatchNo(),2);
                        logger.info("@@@@@@@@@@@oldMsTyMatch.setMatchTime(0);非平手盘出库重新观察");
                    }else {
                        logger.info("@@@@@@@@@@@oldMsTyMatch.setMatchTime(0);下半场不出库测试重新观察");
                    }

                }

            }else {
                //入库策略，先必须仙人指路
                if(prevMsTyMatch.isHasRedCard()){
                    logger.info("有红牌不入库："+prevMsTyMatch);
                    prevMsTyMatch = msTyMatch;
                    continue;
                }

                int goalDiff = prevMsTyMatch.getMasterGoalNum()-prevMsTyMatch.getGuestGoalNum();
                if (goalDiff != 0 ) {//比分

                    if (
                            (goalDiff > 0 && prevMsTyMatch.getMatchStep()==3 && prevMsTyMatch.getHandicap() >= 0 && prevMsTyMatch.getUpperOdds() >= 0.700)
                            ||(goalDiff > 0 && prevMsTyMatch.getMatchStep()==1 && prevMsTyMatch.getHalfHandicap() >= 0 && prevMsTyMatch.getHalfUpperOdds() >= 0.700)
                            ||(goalDiff < 0 && prevMsTyMatch.getMatchStep()==3 && prevMsTyMatch.getHandicap() <= 0 && prevMsTyMatch.getDownOdds() >= 0.700)
                            ||(goalDiff < 0 && prevMsTyMatch.getMatchStep()==1 && prevMsTyMatch.getHalfHandicap() <= 0 && prevMsTyMatch.getHalfDownOdds() >= 0.700)
//                            (goalDiff > 0 && prevMsTyMatch.getMatchStep()==3 && prevMsTyMatch.getHandicap() == 0 && prevMsTyMatch.getUpperOdds() >= 0.700)
//                            ||(goalDiff > 0 && prevMsTyMatch.getMatchStep()==1 && prevMsTyMatch.getHalfHandicap() == 0 && prevMsTyMatch.getHalfUpperOdds() >= 0.700)
//                            ||(goalDiff < 0 && prevMsTyMatch.getMatchStep()==3 && prevMsTyMatch.getHandicap() == 0 && prevMsTyMatch.getDownOdds() >= 0.700)
//                            ||(goalDiff < 0 && prevMsTyMatch.getMatchStep()==1 && prevMsTyMatch.getHalfHandicap() == 0 && prevMsTyMatch.getHalfDownOdds() >= 0.700)
                            ){

                        if(prevMsTyMatch.getMatchTime()>0){
                            //入库前获取比赛数据
                            logger.info("开始获取比赛数据，等待3秒~");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            String matchData = MatchDataUtils.getMatchData(prevMsTyMatch.getMasterTeamName(), prevMsTyMatch.getGuestTeamName());

                            if(matchData!=null){
                                //prevMsTyMatch.setMatchData(prevMsTyMatch.getBetData()+"|"+matchData);

                                prevMsTyMatch.setMatchData(matchData);
                 /*               if(prevMsTyMatch.getMatchStep()==2 ) {
                                    logger.info("############中场休息，时间测试getMatchTime:" + prevMsTyMatch.getMatchTime());
                                    prevMsTyMatch.setMatchTime(0);
                                }*/

                                //if(MatchDataUtils.assessMatchData(prevMsTyMatch)>0.6){
                                    //prevMsTyMatch.setSaveTime(new Date());
                                    prevMsTyMatch.setBaseMatchData(matchData);
                                    prevMsTyMatch.setBaseOdds(MatchDataUtils.getOdds(prevMsTyMatch));
                                    prevMsTyMatch.setStatus(1);
                                    msTyMatches.put(prevMsTyMatch.getMatchNo(),prevMsTyMatch);
                                    MsTyMatchDao.getInstance().insertMsTyMatch(prevMsTyMatch);
                                    //save
                                    logger.info("############入库:" + prevMsTyMatch.toString());
//                                }else {
//                                    logger.info("############不入库|绝对劣势:" + prevMsTyMatch.toString());
//                                }

                            }else {
                                logger.info("############无法获取比赛数据，不入库:"+prevMsTyMatch.toString());
                            }
                        }else {
                            logger.info("############比赛时间不足，不入库:"+prevMsTyMatch.toString());
                        }



                    }else {
                        logger.info("不入库，无法满足条件:"+prevMsTyMatch.toString());
                    }
                }
            }

            prevMsTyMatch = msTyMatch;

        }
    }

    private static NameValuePair[] buildNameValuePair(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return EMPTY_NAMEVALUE_PAIRS;
        }
        NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(parameters.size());
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nameValuePairList.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        nameValuePairList.toArray(nameValuePairs);
        return nameValuePairs;
    }

    private static final NameValuePair[] EMPTY_NAMEVALUE_PAIRS = new NameValuePair[]{};


    private String oddUpBet(String preResult){

        float odds_ =  MatchDataUtils.parseBetResultOdds(preResult, prevMsTyMatch.getMoney());
        float cap_  =  MatchDataUtils.parseBetResultHandicap(preResult, prevMsTyMatch.getMoney());
      //  logger.info("赔率变化402| odds_ |  cap_:"+odds_+"|"+cap_);

        double canUP = MatchDataUtils.canUp(odds_);

       logger.info("赔率变化402| odds_ |  cap_ |canUP:"+odds_+"|"+cap_+"|"+canUP);

        if(odds_-MatchDataUtils.getOdds(prevMsTyMatch)<canUP){
            logger.info("赔率变化过大,继续观察| odds_:"+odds_+"-canUP:"+canUP+"|原:betOdds"+ MatchDataUtils.getOdds(prevMsTyMatch) +"|"+ prevMsTyMatch.getMasterTeamName()+"-"+prevMsTyMatch.getMatchNo() );
            return null;
        }


        prevMsTyMatch.setBetOdds(odds_);

        String rs = null;
        if(prevMsTyMatch.getMatchStep()==1){
            if(prevMsTyMatch.getHalfHandicap() == cap_){
                if(prevMsTyMatch.isBetH()){
                    if( odds_ - prevMsTyMatch.getHalfUpperOdds()>=canUP ){
                        prevMsTyMatch.setHalfUpperOdds(odds_);
                        rs =  this.bet(prevMsTyMatch);
                        logger.info("赔率变化，重新投注："+rs);
                    }
                }else {
                    if( odds_ - prevMsTyMatch.getHalfDownOdds()>=canUP ){
                        prevMsTyMatch.setHalfDownOdds(odds_);
                        rs =  this.bet(prevMsTyMatch);
                        logger.info("赔率变化，重新投注："+rs);
                    }
                }
            }else {
                logger.info("水位变化，跳过");
            }

        }else {
            if(prevMsTyMatch.getHandicap() == cap_){
                if(prevMsTyMatch.isBetH()){
                    if( odds_ - prevMsTyMatch.getUpperOdds()>=canUP ){
                        prevMsTyMatch.setUpperOdds(odds_);
                        rs =  this.bet(prevMsTyMatch);
                        logger.info("赔率变化，重新投注："+rs);
                    }
                }else {
                    if( odds_ - prevMsTyMatch.getDownOdds()>=canUP ){
                        prevMsTyMatch.setDownOdds(odds_);
                        rs =  this.bet(prevMsTyMatch);
                        logger.info("赔率变化，重新投注："+rs);
                    }
                }
            }else {
                logger.info("水位变化，跳过");
            }
        }

        return rs;
    }


}


