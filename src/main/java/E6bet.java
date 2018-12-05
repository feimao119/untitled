import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-15
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class E6bet {

    private static final PropertyUtil prop = new PropertyUtil("d://elog//conf.properties");
    private static final Logger logger = LoggerFactory.getLogger(E6bet.class);
    private static final String Charset = "GBK";
    private static final String Money = prop.getString("Money");
    private static final String ChipIn_Acc = prop.getString("ChipIn_Acc");
    private static final String Domain = "https://www.e6bet.com";
    private static final int MatchTime_Half = NumberUtils.toInt(prop.getString("MatchTime_Half"));
    private static final int MatchTime_All = NumberUtils.toInt(prop.getString("MatchTime_All"));
    private static final double ChipIn_PL_Int_Start = NumberUtils.toDouble(prop.getString("ChipIn_PL_Int_Start"));
    private static final double ChipIn_PL_Int_End =  NumberUtils.toDouble(prop.getString("ChipIn_PL_Int_End"));
    private static final String Cookie = prop.getString("Cookie");
    private static final int SET_Match_NowScore = NumberUtils.toInt(prop.getString("SET_Match_NowScore"));

    private static String Jump_Match = "後備";

    private static final Map BetdMatch = new HashMap<String, Map>();
    private static final int PageLimit = 40;
    private static int MyPageSize = 0;


    public static void main(String[] args) throws Exception {
        logger.info("=============================================================================");
        logger.info("[Money:"+Money +"]");
        logger.info("[Jump_Match:"+Jump_Match +"]");
        logger.info("[ChipIn_Acc:"+ChipIn_Acc +"]");
        logger.info("[MatchTime_Half:"+MatchTime_Half +"]");
        logger.info("[MatchTime_All:"+MatchTime_All +"]");
        logger.info("[ChipIn_PL_Int_Start:"+ChipIn_PL_Int_Start +"]");
        logger.info("[ChipIn_PL_Int_End:"+ChipIn_PL_Int_End +"]");
        logger.info("[Cookie:"+Cookie +"]");
        logger.info("[SET_Match_NowScore:"+SET_Match_NowScore +"]");
        logger.info("=============================================================================");


//        String ss = HttpClientUtils.postQuietly(Domain+"/touzhu/FT_Browser/FT_Roll.aspx?p=1&t=396&name=", new HashMap<String,String>());
//        System.out.println(ss);
//
        //  String ss = HttpClientUtils.get(Domain+"/touzhu/FT_Browser/FT_Roll.aspx");
        //  logger.info(ss);


       // String ss = HttpClientUtils.get(Domain+"/Member/userLogin.aspx?action=update&timeStamp="+System.currentTimeMillis());
      //  System.out.println(getUser_Money());

      //  System.out.println(Cookie);
        //  logger.info("================================="+Math.round(Math.random() * 1000));
//        E6Bet e6bet = new E6Bet();
//        e6bet.rollBetStart();
        E6bet e6bet = new E6bet();
        e6bet.rollBetStart();

        //  System.out.println(e6bet.getUser_Money());
        // get();
    }

    public void rollBetStart() throws Exception {
        while (true){
            try {
                logger.info("=================rollBetStart===================");
                List<Map<String,String>> list = getRollBetMatches();
                if(list==null || list.size()==0){
                    int random =  RandomUtils.getRandom50000to99999();
                    logger.info("=================没有合适的比赛 sleep ==================="+random+"毫秒");
                    Thread.sleep(random);
                    continue;
                }
                for(Map<String,String> matchMap:list){
                    String key =  matchMap.get("ChipIn_MatchID")+matchMap.get("ChipIn_dType");
                    if(BetdMatch.get(key)==null){
                        String result = bet(matchMap);
                        if(result!=null){
                            BetdMatch.put(key,matchMap);
                            logger.info("");
                            logger.info("**************************************************************");
                            logger.info("BetdMatch:" + BetdMatch);
                            logger.info("**************************************************************");
                            logger.info("");
                            Thread.sleep(RandomUtils.getRandom5000to9999());
                        }
                    }else{
                        logger.info("key:"+key+"|beted");
                    }
                }
                int random =  RandomUtils.getRandom5000to9999();
                logger.info("================轮询结束 sleep =================="+random+"毫秒");
                Thread.sleep(random);

            }  catch (Exception e) {
                e.printStackTrace();
                logger.error("error",e);
                continue;
               //throw e;
            }

        }
    }

    public List<Map<String,String>> getRollBetMatches() throws IOException{
        List<Map<String,String>> matches = new ArrayList<Map<String, String>>();

        for(int i=1;i<20; i++){
            List<Map<String,String>> match4Page = getRollBetMatches4Page(i);
            if(match4Page!=null && match4Page.size()>0){
                matches.addAll(match4Page);
            }
            if(MyPageSize<PageLimit){
                logger.info("----break,i="+i);
                break;
            }
        }

        return matches;
    }

    public List<Map<String,String>> getRollBetMatches4Page(int page) throws IOException{
        String rollInfoStrAll = getRollInfoAllStr(page);
        // logger.info("rollInfoStrAll:"+rollInfoStrAll);

        String beginStr = new String("new Array");
        String endStr = "parent.sclasscount";
        if(rollInfoStrAll.indexOf(beginStr)<0){
            MyPageSize = 0;
            if(page==1){
                logger.info("=======no roll! sleep 30分钟");
                try {
                    Thread.sleep(1800000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            return null;
        }

        int begin = rollInfoStrAll.indexOf(beginStr)+beginStr.length();
        int end = rollInfoStrAll.indexOf(endStr);

//        logger.info("begin:"+begin);
//        logger.info("end:"+end);

        String rollInfoStr = rollInfoStrAll.substring(begin,end).trim();
       // logger.info("rollInfoStr:"+rollInfoStr);

        String[] rollInfoArray = rollInfoStr.split(beginStr);
        List<Map<String,String>> rollList = new ArrayList<Map<String, String>>();

         logger.info("=======rollInfoArray length:"+rollInfoArray.length+"|page:"+page+"|");
         MyPageSize = rollInfoArray.length;

        String[] Jump_Match_Array = Jump_Match.split(",");

        int l = 0;
        for(String matchStr:rollInfoArray){
            logger.info("----------------"+l+++"-------------------");
           // logger.info("matchStr:"+matchStr);
            String match = matchStr.substring(2,matchStr.indexOf("');"));
            // logger.info("match:"+match);
            String[] matchPropArray = match.split("','");
            int i=1;
            Map<Integer,String> countMap = new HashMap<Integer,String>();

            for(String matchProp:matchPropArray){
                // logger.info("matchProp:"+matchProp);
                countMap.put(i,matchProp);
                i++;
            }
            logger.info("countMap:"+countMap);

            Map matchMap = new HashMap<String,String>();

            //投注条件--------------------------------------------------------------

            //比赛时间
            int matchTime = NumberUtils.toInt(countMap.get(4)+"",-1);

            logger.info("matchTime:"+matchTime);


            if(matchTime==-1){
                continue;
            }

            if(matchTime<MatchTime_Half || (matchTime>46 && matchTime<MatchTime_All)){
                continue;
            }
            if(matchTime<46 && matchTime>=MatchTime_Half){
                matchMap.put("ChipIn_dType","211");
            }else if(matchTime>=MatchTime_All){
                matchMap.put("ChipIn_dType","111");
            }else{
                continue;
            }

            //让球不压

            String ChipIn_PK;
            if (matchTime>=MatchTime_All){
                ChipIn_PK = countMap.get(13);
            } else{
                ChipIn_PK = countMap.get(29);
            }

            if(ChipIn_PK!=null&&(ChipIn_PK.equals("0")||ChipIn_PK.equals("*0"))){
                matchMap.put("ChipIn_PK",ChipIn_PK);
            }else {
                logger.info("ChipIn_PK not 0:"+ChipIn_PK);
                continue;
            }

            //高水位或低水位不压
            String ChipIn_PL;
            if (matchTime>=MatchTime_All){
                ChipIn_PL = countMap.get(11);
            } else{
                ChipIn_PL = countMap.get(22);
            }

            double  ChipIn_PL_Int = NumberUtils.toDouble(ChipIn_PL,-1);
            if(ChipIn_PL_Int>ChipIn_PL_Int_End || ChipIn_PL_Int<ChipIn_PL_Int_Start){
                logger.info("ChipIn_PL_Int error,continue:"+ChipIn_PL_Int);
                continue;
            } else {
                matchMap.put("ChipIn_PL",ChipIn_PL);
            }


            //比分
            String Match_NowScore = countMap.get(32);

            if(StringUtils.isNotBlank(Match_NowScore)){
                String[] Match_NowScore_Arr = Match_NowScore.split(":");

                if(SET_Match_NowScore==1){
                    if(NumberUtils.toInt(Match_NowScore_Arr[1],-1)-NumberUtils.toInt(Match_NowScore_Arr[0],-1)!=1){
                        logger.info("Match_NowScore not "+SET_Match_NowScore+",continue:"+Match_NowScore);
                        continue;
                    }
                }else if(SET_Match_NowScore==0){
                    if(NumberUtils.toInt(Match_NowScore_Arr[1],-1)-NumberUtils.toInt(Match_NowScore_Arr[0],-1)==1
                        ||  NumberUtils.toInt(Match_NowScore_Arr[1],-1)-NumberUtils.toInt(Match_NowScore_Arr[0],-1)==0
                            ){

                    } else {
                        logger.info("Match_NowScore not "+SET_Match_NowScore+",continue:"+Match_NowScore);
                        continue;
                    }
                }


            } else {
                logger.info("Match_NowScore is null,continue:"+Match_NowScore);
                continue;
            }

            if(countMap.get(5).indexOf("[中]")>-1){
                logger.info("~~~~~~~~~~中立场地跳过~~~~~~~~");

            }

            for(String Jump_Match:Jump_Match_Array){
                if(countMap.get(5).indexOf(Jump_Match)>-1){
                    logger.info("~~~~~~~~~~"+Jump_Match+"跳过~~~~~~~~");
                }
            }

            String betdKey = countMap.get(1) + matchMap.get("ChipIn_dType");
            //已投注
            if(BetdMatch.get(betdKey)!=null){
                logger.info("betdKey:"+betdKey +"| 已经投注！");
                continue;
            }

            //---------------------------------------------------------------------------------------------

            matchMap.put("money",Money+"");
            matchMap.put("ChipIn_Acc",ChipIn_Acc);
            matchMap.put("ChipIn_MatchID",countMap.get(1));

            matchMap.put("ChipIn_Name",countMap.get(6));
            matchMap.put("Match_Date",countMap.get(3));
            matchMap.put("ChipIn_Master",countMap.get(5));
            matchMap.put("ChipIn_Guest",countMap.get(7));
            matchMap.put("ChipIn_Type","3");

            double myMoney =   getUser_Money();
            if(myMoney < NumberUtils.toDouble(Money,100)){
                logger.info("no money, sleep 1分钟后继续:"+myMoney);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                continue;
            }

            matchMap.put("User_Money",myMoney+"");
            matchMap.put("User_Max","5000");
            matchMap.put("User_Min","10");
            matchMap.put("Count_Max","100000");
            matchMap.put("SumChipIn_Money","0.00");

            matchMap.put("ChipInedName","");
            matchMap.put("Match_NowScore",countMap.get(32));
            matchMap.put("Match_CoverDate",countMap.get(39));
            matchMap.put("Chipin_replace","true");

            logger.info("matchMap:"+matchMap);

            rollList.add(matchMap);
        }

        return rollList;
    }

    public String getRollInfoAllStr(int page) throws IOException {
        GetMethod method = new GetMethod(Domain+"/touzhu/FT_Browser/FT_Roll.aspx?p="+page+"&t="+Math.round(Math.random() * 1000)+"&name=");
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        method.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
//        method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
        method.setRequestHeader("Connection","keep-alive");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host","www.e6bet.com");
        method.setRequestHeader("Referer","https://www.e6bet.com/touzhu/FT_Browser/FT_Roll_l.aspx");
        method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");

        String text = null;


            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(method);
            text = IOUtils.toString(method.getResponseBodyAsStream(), Charset);



       // logger.info("getRollInfoAllStr all:"+text);
        return text;
    }



    public double getUser_Money() {
        GetMethod method = new GetMethod(Domain+"/Member/userLogin.aspx?action=update&timeStamp="+System.currentTimeMillis());
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        method.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
//        method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control","max-age=0");
        method.setRequestHeader("Connection","keep-alive");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host","www.e6bet.com");
        method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");

        String text ="<money>5000</money>";

    try{
        HttpClient httpClient = new HttpClient();

        httpClient.executeMethod(method);

        //打印出返回数据，检验一下是否成功
       // String text =  IOUtils.toString(method.getResponseBodyAsStream());

        text = method.getResponseBodyAsString();

    } catch (Exception e) {
        e.printStackTrace();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return getUser_Money();
    }

        logger.info("getUser_Money all:"+text);

        int start = text.indexOf("<money>")+"<money>".length();
        int end = text.indexOf("</money>");
        String money = text.substring(start,end);


        return NumberUtils.toDouble(money,-1);
    }

    public String bet(Map<String,String> match){
        String url;
        if(match.get("ChipIn_dType").equals("211")){
            url = Domain+"/Member/ChipIn/Bet_ChipIn/ChipIn_GQSbrq.aspx?id="+match.get("ChipIn_MatchID")+"&Type=H&"+Math.round(Math.random() * 1000000);
        } else if(match.get("ChipIn_dType").equals("111")){
            url = Domain+"/Member/ChipIn/Bet_ChipIn/ChipIn_GQQcrq.aspx?id="+match.get("ChipIn_MatchID")+"&Type=H&"+Math.round(Math.random() * 1000000);
        } else {
            logger.info("ChipIn_dType error:"+match.get("ChipIn_dType"));
            return null;
        }

        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        method.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
//        method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
        method.setRequestHeader("Cache-Control","max-age=0");
        method.setRequestHeader("Connection","keep-alive");
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        method.setRequestHeader("Cookie",Cookie);
        method.setRequestHeader("Host","www.e6bet.com");
        method.setRequestHeader("Origin","https://www.e6bet.com");
        method.setRequestHeader("Referer:",url);
        method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");


        //----------------

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Charset);
        method.setRequestBody(buildNameValuePair(match));

        long startTime = System.currentTimeMillis();
        HttpClient client = new HttpClient();

        client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        client.getHttpConnectionManager().getParams().setSoTimeout(120000);

        int statusCode = HttpStatus.SC_OK;
        long elapsedTime = 0;

        try {
            statusCode = client.executeMethod(method);
            elapsedTime = System.currentTimeMillis() - startTime;

            logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);

//            if (statusCode != HttpStatus.SC_OK) {
//                logger.error("调用http请求失败: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);
//                throw new HttpInvokeException(statusCode, "调用http服务返回响应错误, url: " + method.getURI() + ",响应码："
//                        + statusCode);
//            } else {
//                logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);
//            }

            String result =  method.getResponseBodyAsString();
            logger.info("=======================start bet! match info:"+match+"==================");
            logger.info("result:"+result);
            logger.info("=========================end bet!=========================");

            if(result.indexOf("最新水位已更新")>0){
                logger.info("------------------------水位更新-|"+match+"|||");
                return null;
            }else{
                logger.info("------------------------投注成功--|"+match+"|||");
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
}

