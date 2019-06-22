import domain.MsTyMatch;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-7-15
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class MatchDataUtils {
    private static final Logger logger = LoggerFactory.getLogger(MatchDataUtils.class);
    private static String matchDataAllStr = null;
    private static Date matchDataAllDate= null;

    private static String getLeisuTeamId(String teamName1,String teamName2){

        if(StringUtils.isBlank(teamName1)&&StringUtils.isBlank(teamName2)) return null;

        if(matchDataAllStr == null){
            //https://live.leisu.com/
            matchDataAllStr = LeisuHttphelp.getRequest(MsTyLoBet.leisuUrl);
            if(StringUtils.isBlank(matchDataAllStr)||matchDataAllStr.length()<200){
                logger.info("获取比赛数据失败："+MsTyLoBet.leisuUrl+matchDataAllStr);
                return null;
            }
            matchDataAllDate = new Date();
            if(matchDataAllStr.indexOf(teamName1)==-1 && matchDataAllStr.indexOf(teamName2)==-1)return null;
        } else {
            if(matchDataAllStr.indexOf(teamName1)==-1 && matchDataAllStr.indexOf(teamName2)==-1 ){
                if((new Date().getTime()-matchDataAllDate.getTime())<300*1000){
                    logger.info("间隔小于5分钟，不获取新比赛数据");
                    return null;
                }
                matchDataAllStr = LeisuHttphelp.getRequest(MsTyLoBet.leisuUrl);
                if(StringUtils.isBlank(matchDataAllStr)||matchDataAllStr.length()<200){
                    logger.info("获取比赛数据失败："+MsTyLoBet.leisuUrl+matchDataAllStr);
                    return null;
                } else {
                    matchDataAllDate = new Date();
                }
                if(matchDataAllStr.indexOf(teamName1)==-1 && matchDataAllStr.indexOf(teamName2)==-1)return null;
            }
        }

        if(matchDataAllStr.indexOf(teamName1)>-1) {
            if(matchDataAllStr.indexOf(teamName1)!=matchDataAllStr.lastIndexOf(teamName1)){
                if(Math.abs(matchDataAllStr.indexOf(teamName1)-matchDataAllStr.lastIndexOf(teamName1))<30){
                    return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName1), "\":[\"")), "\"")+"_1";
                }else {
                    logger.info("有2个teamName1:"+teamName1);
                }
            }else {
                    return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName1), "\":[\"")), "\"")+"_1";
            }
        }
        if(matchDataAllStr.indexOf(teamName2)>-1) {
            if(matchDataAllStr.indexOf(teamName2)!=matchDataAllStr.lastIndexOf(teamName2)){
                if(Math.abs(matchDataAllStr.indexOf(teamName2)-matchDataAllStr.lastIndexOf(teamName2))<30){
                    return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName2), "\":[\"")), "\"")+"_2";
                }else {
                    logger.info("有2个teamName2:"+teamName2);
                }
            }else {
                return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName2), "\":[\"")), "\"")+"_2";
            }
        }

        return null;

    /*    if(StringUtils.countMatches(matchDataAllStr,teamName1)==1){
            return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName1), "\":[\"")), "\"")+"_1";
        }else if(StringUtils.countMatches(matchDataAllStr,teamName2)==1) {
            return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName2), "\":[\"")), "\"")+"_2";
        }else {
            return null;
        }*/

      /*  if(matchDataAllStr.indexOf(teamName1)!=-1){
//            logger.info("StringUtils.substringBefore(matchDataAllStr,teamName1):"+StringUtils.substringBefore(matchDataAllStr,teamName1));
//            logger.info("StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName1),\"],\\\"\"):"+StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName1),"],\""));
//            logger.info("StringUtils.substringBefore(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName1),\"],\\\"\"),\"\\\":[\");"+StringUtils.substringBefore(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName1),"],\""),"\":["));
           // return StringUtils.substringBefore(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName1),"],\""),"\":[");
            return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName1), "\":[\"")), "\"")+"_1";
        }else if(matchDataAllStr.indexOf(teamName2)!=-1) {
            //return StringUtils.substringBefore(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,teamName2),"],\""),"\":[");
            return StringUtils.substringAfterLast((StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(matchDataAllStr, teamName2), "\":[\"")), "\"")+"_2";
        }else {
            return null;
        }
*/
    }

    private static String getLeisuMatchId(String teamId){
        if(StringUtils.isBlank(teamId)) return null;

        String[] sa = teamId.split("_");

        if(StringUtils.isBlank(sa[0])) return null;
//        logger.info(StringUtils.substringBefore(matchDataAllStr,teamId));
//        logger.info(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,"["+teamId),"],["));
//        logger.info(StringUtils.substringBefore(StringUtils.substringAfterLast(StringUtils.substringBefore(matchDataAllStr,"["+teamId),"],["),","));
        String tmpstr =  StringUtils.substringBefore(matchDataAllStr,"["+sa[0]);
      //  logger.info("tmpstr:"+tmpstr);
      //  logger.info("(tmpstr.substring(tmpstr.length()-2):"+(tmpstr.substring(tmpstr.length()-2)));
        String reverse;
        if((tmpstr.substring(tmpstr.length()-2).equals("],")&&sa[1].equals("2"))
                || !tmpstr.substring(tmpstr.length()-2).equals("],")&&sa[1].equals("1")){
            reverse = "n";
        }else{
            reverse = "y";
        }

        String ranking;
        if((tmpstr.substring(tmpstr.length()-2).equals("],"))){
            String r1 = StringUtils.substringBefore(StringUtils.substringAfterLast(tmpstr,",\""),"\",");
            String r2 = StringUtils.substringBefore(StringUtils.substringAfter(matchDataAllStr,"["+sa[0]+",\""),"\",");

            ranking = (StringUtils.isBlank(r1)?"0":r1)+"_"+ (StringUtils.isBlank(r2)?"0":r2);
        }else{
            String r1 = StringUtils.substringBefore(StringUtils.substringAfter(matchDataAllStr,"["+sa[0]+",\""),"\",");
            String r2 = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(StringUtils.substringAfter(matchDataAllStr,"["+sa[0]),"],["),",\""),"\",");

            ranking = (StringUtils.isBlank(r1)?"0":r1)+"_"+ (StringUtils.isBlank(r2)?"0":r2);
        }

        if(StringUtils.indexOf(tmpstr,"],[")==-1){
            return StringUtils.substringBefore(StringUtils.substringAfterLast(tmpstr,"[["),",")+"_"+reverse+"_"+ranking;
        }else {
            return StringUtils.substringBefore(StringUtils.substringAfterLast(tmpstr,"],["),",")+"_"+reverse+"_"+ranking;
        }
    }

    /*
    \u5c04\u95e8 射门
    \u5c04\u6b63 射正
    \u63a7\u7403\u7387 控球率
    //比赛数据                           //射门:客射门_射正:客射正_控球率:客控球率_主赔率:客赔率_盘口_比分_比赛时间|
    private String halfMatchData;   //7:10_4:9_54:46_1.075:0.725_0_1:0_40|
     */
    private static String getMatchDataByLsmid(String leisuMatchIdStr) {
        if(StringUtils.isBlank(leisuMatchIdStr)) return null;
        String[] leisuMatchArr =  leisuMatchIdStr.split("_");
        String leisuMatchId = leisuMatchArr[0];
        if(StringUtils.isBlank(leisuMatchId)) return null;

      /*  String str = LeisuHttphelp.getRequest("https://api.leisu.com/api/eventdata?scheduleid="+leisuMatchId+"&type=event");
        if(StringUtils.isBlank(str) || str.indexOf("\\u5c04\\u95e8")==-1 || str.indexOf("\\u5c04\\u6b63")==-1 ){
            logger.info("数据不全：https://api.leisu.com/api/eventdata?scheduleid="+leisuMatchId+"&type=event|"+str);
            return null;
        }*/

        //https://api.leisu.com/api/v2/match/stats?id=2578636  新
        String str = LeisuHttphelp.getRequest("https://api.leisu.com/api/v2/match/stats?id="+leisuMatchId);
        if(StringUtils.isBlank(str) || str.indexOf(":21,")==-1 || str.indexOf(":22,")==-1 ){
            logger.info("数据不全：https://api.leisu.com/api/v2/match/stats?id="+leisuMatchId+"|"+str);
            return null;
        }
       // {"code":0,"data":{"stats":[{"type":2,"home":0,"away":1},{"type":3,"home":0,"away":0},{"type":4,"home":0,"away":0},{"type":8,"home":0,"away":0},{"type":21,"home":4,"away":3},{"type":22,"home":1,"away":3}],
       // "incidents":[{"type":1,"second":540,"time":"9","belong":2,"player_name":"Bosnjak"},{"type":1,"second":2580,"time":"43","belong":1},{"type":1,"second":2640,"time":"44","belong":2}]}}
/*        String mshoot = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":21,"),"home\":"),",\"away");
        String gshoot = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":21,"),"away\":"),"}");
        String mshootz = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":22,"),"home\":"),",\"away");
        String gshootz = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":22,"),"away\":"),"}");*/


        String mshootp = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":22,"),"home\":"),",\"away");
        String gshootp = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":22,"),"away\":"),"}");
        String mshootz = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":21,"),"home\":"),",\"away");
        String gshootz = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,":21,"),"away\":"),"}");

        String mshoot =  (NumberUtils.toInt(mshootp,0)+ NumberUtils.toInt(mshootz,0))+"";
        String gshoot = (NumberUtils.toInt(gshootp,0)+ NumberUtils.toInt(gshootz,0))+"";

        String mBallPoss = "30";
        String gBallPoss = "30";

        if(str.indexOf("\\u63a7\\u7403\\u7387")!=-1){
            mBallPoss = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,"\\u63a7\\u7403\\u7387"),"HomeData\":\""),"%\",\"AwayData");
            gBallPoss = StringUtils.substringBefore(StringUtils.substringAfter(StringUtils.substringAfter(str,"\\u63a7\\u7403\\u7387"),"AwayData\":\""),"%\"}");
        } else {
            //logger.info("缺少控球率，补全："+str);
        }

        if("y".equals(leisuMatchArr[1])){
            return gshoot+":"+mshoot+"_"+ gshootz+":"+mshootz+"_"+ gBallPoss+":"+mBallPoss+"_"+leisuMatchArr[3]+":"+leisuMatchArr[2];
        }else {
            return mshoot+":"+gshoot+"_"+ mshootz+":"+gshootz+"_"+ mBallPoss+":"+gBallPoss+"_"+leisuMatchArr[2]+":"+leisuMatchArr[3];
        }


    }

    public static String getMatchData(String teamName1,String teamName2){
        return getMatchDataByLsmid(getLeisuMatchId(getLeisuTeamId(teamName1, teamName2)));
    }

    //射门:客射门_射正:客射正_控球率:客控球率_排名:客排名
/*    //7:10_4:9_54:46
    public static double assessMatchData(String matchData){
        if(StringUtils.isBlank(matchData))return 0;

        String[] matchDataArray = matchData.split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        String[] ballpossArr = matchDataArray[2].split(":");

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0])+NumberUtils.toDouble(ballpossArr[0])/10.00;
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1])+NumberUtils.toDouble(ballpossArr[1])/10.00;

        return mData/gData;
    }*/

   public static boolean validateMatchData(String matchData,int count){

       if(StringUtils.isBlank(matchData))return false;

       String[] matchDataArray = matchData.split("_");
       String[] shootArr = matchDataArray[0].split(":");
     //  String[] shootzArr = matchDataArray[1].split(":");
       //String[] ballpossArr = matchDataArray[2].split(":");

       int mData = NumberUtils.toInt(shootArr[0]);
       int gData = NumberUtils.toInt(shootArr[1]);

/*       int mData = NumberUtils.toInt(shootArr[0])+ NumberUtils.toInt(shootzArr[0]);
       int gData = NumberUtils.toInt(shootArr[1])+ NumberUtils.toInt(shootzArr[1]);*/

       if(mData+gData<count){
           return false;
       }else {
           return true;
       }

   }

    public static boolean validateMatchData(String baseData ,String oldMatchData,String matchData){

        if(StringUtils.isBlank(baseData)
                || StringUtils.isBlank(oldMatchData)
                ||StringUtils.isBlank(matchData))return false;

        String[] baseDataArray = baseData.split("_");
        String[] oldMatchDataArray = oldMatchData.split("_");
        String[] matchDataArray = matchData.split("_");


        if(baseDataArray[3].equals(oldMatchDataArray[3]) && baseDataArray[3].equals(matchDataArray[3])){

        }else {
            return false;
        }

        if(baseDataArray[2].equals("30:30") || oldMatchDataArray[2].equals("30:30") || matchDataArray[2].equals("30:30")) {
            if(baseDataArray[2].equals(oldMatchDataArray[2]) && baseDataArray[2].equals(matchDataArray[2])){

            } else {
                return false;
            }
        }

        return true;

    }

    public static int compareRanking(MsTyMatch msTyMatch){
        if(msTyMatch==null)return 100;
        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] rankingArr = matchDataArray[3].split(":");

        if(rankingArr[0].equals("0")||rankingArr[1].equals("0")){
            return 100;
        }

        String c1 = rankingArr[0].replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        String c2 = rankingArr[1].replaceAll("[^(\\u4e00-\\u9fa5)]", "");

        if(!c1.equals(c2)){
            return 100;
        }

        String tmp1;
        String tmp2;
        if(rankingArr[0].indexOf("-")>0 && rankingArr[1].indexOf("-")>0){
                tmp1 = rankingArr[0].split("-")[1];
                tmp2 = rankingArr[1].split("-")[1];
        }else {
            tmp1 = rankingArr[0];
            tmp2 = rankingArr[1];
        }

        String n1 = tmp1.replaceAll("[^(0-9)]", "");
        String n2 = tmp2.replaceAll("[^(0-9)]", "");

        if(n1.equals("0")||n2.equals("0")){
            return 100;
        }


        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
            return Integer.parseInt(n1)-Integer.parseInt(n2);
        } else {
            return Integer.parseInt(n2)-Integer.parseInt(n1);
        }
    }

    //射门:客射门_射正:客射正_控球率:客控球率
    //7:10_4:9_54:46
    public static double assessMatchData(MsTyMatch msTyMatch){
        if(msTyMatch==null)return 0;

        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        String[] ballpossArr = matchDataArray[2].split(":");

        double aaa = 10.00;
        if(msTyMatch.getMatchStep()==1 && msTyMatch.getMatchTime()<10)aaa = 25.00;

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0])*0.5+NumberUtils.toDouble(ballpossArr[0])/aaa;
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1])*0.5+NumberUtils.toDouble(ballpossArr[1])/aaa;

        //logger.info("mData|gData:"+mData+"|"+gData+"@@"+mData/gData);

        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
        //    logger.info("mData|gData:"+mData+"|"+gData+"@@"+mData/gData);
            return mData/gData;
        } else {
        //    logger.info("gData/mData:"+gData+"|"+mData+"@@"+gData/mData);
            return gData/mData;
        }
    }

   /* public static double compMatchData(MsTyMatch msTyMatch){
        if(msTyMatch==null)return 0;

        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        String[] ballpossArr = matchDataArray[2].split(":");

        double aaa = 10;
        if(msTyMatch.getMatchStep()==1 && msTyMatch.getMatchTime()<10)aaa = 25;

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0])+NumberUtils.toDouble(ballpossArr[0])/aaa;
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1])+NumberUtils.toDouble(ballpossArr[1])/aaa;

        //logger.info("mData|gData:"+mData+"|"+gData+"@@"+mData/gData);

        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
            logger.info("mData|gData:"+mData+"|"+gData+"@@"+mData/gData);
            return mData/gData;
        } else {
            logger.info("gData/mData:"+gData+"|"+mData+"@@"+gData/mData);
            return gData/mData;
        }
    }*/

/*    public static double assessMatchDataOnlyShoot(String matchData){
        if(StringUtils.isBlank(matchData))return 0;

        String[] matchDataArray = matchData.split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        // String[] ballpossArr = matchDataArray[2].split(":");

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0]);
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1]);

        return mData/gData;
    }*/

    public static int compOnlyShoot(MsTyMatch msTyMatch,MsTyMatch oldMsTyMatch){
        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        // String[] ballpossArr = matchDataArray[2].split(":");

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0]);
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1]);

        String[] matchDataArrayOld = oldMsTyMatch.getMatchData().split("_");
        String[] shootArrOld = matchDataArrayOld[0].split(":");
        String[] shootzArrOld = matchDataArrayOld[1].split(":");
        // String[] ballpossArrOld = matchDataArrayOld[2].split(":");

        double mDataOld = NumberUtils.toDouble(shootArrOld[0])+ NumberUtils.toDouble(shootzArrOld[0]);
        double gDataOld = NumberUtils.toDouble(shootArrOld[1])+ NumberUtils.toDouble(shootzArrOld[1]);

        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
            Double up = mData - mDataOld -(gData - gDataOld);
            //logger.info("onlyShoot:m-g"+up);
            return up.intValue();
        } else {
            Double up = gData - gDataOld -(mData - mDataOld);
            //logger.info("onlyShoot:g-m"+up);
            return up.intValue();
        }
    }

    //射门:客射门_射正:客射正_控球率:客控球率
    //7:10_4:9_54:46

    public static int compOnlyShoot(MsTyMatch msTyMatch,String oldMatchData){
        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        // String[] ballpossArr = matchDataArray[2].split(":");

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0]);
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1]);

        String[] matchDataArrayOld = oldMatchData.split("_");
        String[] shootArrOld = matchDataArrayOld[0].split(":");
        String[] shootzArrOld = matchDataArrayOld[1].split(":");
        // String[] ballpossArrOld = matchDataArrayOld[2].split(":");

        double mDataOld = NumberUtils.toDouble(shootArrOld[0])+ NumberUtils.toDouble(shootzArrOld[0]);
        double gDataOld = NumberUtils.toDouble(shootArrOld[1])+ NumberUtils.toDouble(shootzArrOld[1]);

        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
            Double up = mData - mDataOld -(gData - gDataOld);
           // logger.info("onlyShoot:m-g"+up);
            return up.intValue();
        } else {
            Double up = gData - gDataOld -(mData - mDataOld);
           // logger.info("onlyShoot:g-m"+up);
            return up.intValue();
        }
    }

    public static double assessMatchDataOnlyShoot(MsTyMatch msTyMatch){
        if(msTyMatch==null)return 0;

        String[] matchDataArray = msTyMatch.getMatchData().split("_");
        String[] shootArr = matchDataArray[0].split(":");
        String[] shootzArr = matchDataArray[1].split(":");
        // String[] ballpossArr = matchDataArray[2].split(":");

        double mData = NumberUtils.toDouble(shootArr[0])+ NumberUtils.toDouble(shootzArr[0])*0.5+1;
        double gData = NumberUtils.toDouble(shootArr[1])+ NumberUtils.toDouble(shootzArr[1])*0.5+1;



        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
          //  logger.info("onlyShoot: mData|gData:"+mData+"|"+gData+"@@"+mData/gData);
            return mData/gData;
        } else {
         //   logger.info("onlyShoot: gData|mData:"+gData+"|"+mData+"@@"+gData/mData);
            return gData/mData;
        }
    }

    public static boolean isOddsUp(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch){
        if(prevMsTyMatch.getMatchStep()==1){
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getHalfUpperOdds()>oldMsTyMatch.getHalfUpperOdds()){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getHalfDownOdds()>oldMsTyMatch.getHalfDownOdds()){
                    return true;
                }else {
                    return false;
                }
            }

        } else {
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getUpperOdds()>oldMsTyMatch.getUpperOdds()){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getDownOdds()>oldMsTyMatch.getDownOdds()){
                    return true;
                }else {
                    return false;
                }
            }

        }

    }

    public static boolean isOddsScopeUp(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch){
        if(prevMsTyMatch.getMatchStep()==1){
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getHalfUpperOdds()-oldMsTyMatch.getHalfUpperOdds()>0.09 && prevMsTyMatch.getHalfUpperOdds()>=0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getHalfDownOdds()-oldMsTyMatch.getHalfDownOdds()>0.09 && prevMsTyMatch.getHalfDownOdds()>=0.82){
                    return true;
                }else {
                    return false;
                }
            }

        } else {
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getUpperOdds()-oldMsTyMatch.getUpperOdds()>0.09 && prevMsTyMatch.getUpperOdds()>=0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getDownOdds()-oldMsTyMatch.getDownOdds()>0.09 && prevMsTyMatch.getDownOdds()>=0.82){
                    return true;
                }else {
                    return false;
                }
            }

        }

    }

    public static boolean isOddsScopeUp(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch,double upOdds){
        if(prevMsTyMatch.getMatchStep()==1){
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getHalfUpperOdds()-oldMsTyMatch.getHalfUpperOdds()>upOdds && prevMsTyMatch.getHalfUpperOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getHalfDownOdds()-oldMsTyMatch.getHalfDownOdds()>upOdds && prevMsTyMatch.getHalfDownOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }

        } else {
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getUpperOdds()-oldMsTyMatch.getUpperOdds()>upOdds && prevMsTyMatch.getUpperOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getDownOdds()-oldMsTyMatch.getDownOdds()>upOdds && prevMsTyMatch.getDownOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }
        }

    }

    public static boolean isOddsScope(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch){
        if(prevMsTyMatch.getMatchStep()==1){
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getHalfUpperOdds()-oldMsTyMatch.getHalfUpperOdds()>=-0.05 && prevMsTyMatch.getHalfUpperOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getHalfDownOdds()-oldMsTyMatch.getHalfDownOdds()>=-0.05 && prevMsTyMatch.getHalfDownOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }

        } else {
            if(prevMsTyMatch.getMasterGoalNum()>prevMsTyMatch.getGuestGoalNum()) {
                if(prevMsTyMatch.getUpperOdds()-oldMsTyMatch.getUpperOdds()>=-0.05 && prevMsTyMatch.getUpperOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }else {
                if(prevMsTyMatch.getDownOdds()-oldMsTyMatch.getDownOdds()>=-0.05 && prevMsTyMatch.getDownOdds()>0.82){
                    return true;
                }else {
                    return false;
                }
            }

        }

    }

    public static boolean isOOCap(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch){
        if(prevMsTyMatch.getMatchStep()==1){
            if (prevMsTyMatch.getHalfHandicap() == 0 && prevMsTyMatch.getHalfHandicap() == oldMsTyMatch.getHalfHandicap()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (prevMsTyMatch.getHandicap() == 0 && prevMsTyMatch.getHandicap() == oldMsTyMatch.getHandicap()) {
                return true;
            } else {
                return false;
            }
        }

    }

    public static boolean isGreaterOOCap(MsTyMatch prevMsTyMatch, MsTyMatch oldMsTyMatch){
        if(prevMsTyMatch.getMatchStep()==1){
            if (prevMsTyMatch.getHalfHandicap() >= 0 && prevMsTyMatch.getHalfHandicap() == oldMsTyMatch.getHalfHandicap()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (prevMsTyMatch.getHandicap() >= 0 && prevMsTyMatch.getHandicap() == oldMsTyMatch.getHandicap()) {
                return true;
            } else {
                return false;
            }
        }
    }

    //<script type="text/javascript">var lDt=["401","615777863=10"]; parent.processQBDataResult(lDt); </script>
    public static int parseBetResultCode(String betResultStr){
        if(StringUtils.isBlank(betResultStr))return -1;
        return NumberUtils.toInt(StringUtils.substringBefore(StringUtils.substringAfter(betResultStr, "lDt=[\""), "\",\""));
    }

    //<script type="text/javascript">var lDt=["402","0.970~0~-0.25~0-0.5~0~0|2000.00~5.00~X"]; parent.initQBDataResult(lDt); </script>
    public static float parseBetResultOdds(String betResultStr,int money){
        if(StringUtils.isBlank(betResultStr))return -1;
        return NumberUtils.toFloat(StringUtils.substringBefore(StringUtils.substringAfter(betResultStr, "\",\""), "~"+money+"~"));
    }
    //<script type="text/javascript">var lDt=["402","1.110~30~-0.25~0-0.5~1~2|20000.00~5.00~X"]; parent.processQBDataResult(lDt); </script>
    public static float parseBetResultHandicap(String betResultStr,int money){
        if(StringUtils.isBlank(betResultStr))return -1;
        return NumberUtils.toFloat(StringUtils.substringBefore(StringUtils.substringAfter(betResultStr, "~"+money+"~"),"~"));
    }

    //<script type="text/javascript">var lDt=["0","45000.00^5.00^X"]; parent.initQBDataResult(lDt); </script>
    public static int parseBetMoney(String betResultStr){
        if(StringUtils.isBlank(betResultStr))return -1;
        Float mm;
        if(parseBetResultCode(betResultStr)==0){
           mm = NumberUtils.toFloat(StringUtils.substringBefore(StringUtils.substringAfter(betResultStr, "\",\""),"^"));
        }else{
            mm = NumberUtils.toFloat(StringUtils.substringBefore(StringUtils.substringAfter(betResultStr, "|"),"~"));
        }
        return mm.intValue();
    }

    public static String cleanTeamName(String teamName){
        if("SSV Jahn Regensburg".equalsIgnoreCase(teamName))return "Jahn Regensburg";
        if("苏梅".equalsIgnoreCase(teamName))return "苏迈";
        if("武里南联".equalsIgnoreCase(teamName))return "武里南";
        if("本尤德科塔什干".equalsIgnoreCase(teamName))return "本尤德科";
        if("索菲亚中央陆军".equalsIgnoreCase(teamName))return "索非亚中央陆军";
        if("阿德米拉瓦克默德林".equalsIgnoreCase(teamName))return "阿德米拉";
        if("林肯红色小魔鬼".equalsIgnoreCase(teamName))return "林肯红魔";
        if("新圣地".equalsIgnoreCase(teamName))return "新圣徒";
        if("RB莱比锡".equalsIgnoreCase(teamName))return "RB莱比锡";
        if("拉达陶里亚蒂".equalsIgnoreCase(teamName))return "拉达";
        if("伊泽夫斯克".equalsIgnoreCase(teamName))return "FK泽尼特伊热夫斯克";
        if("SV哈恩".equalsIgnoreCase(teamName))return "SV霍恩";
        if("奥地利维也纳青年紫罗兰".equalsIgnoreCase(teamName))return "奥地利维也纳青年队";
        if("韦尔 1900".equalsIgnoreCase(teamName))return "韦尔";
        if("塞维特".equalsIgnoreCase(teamName))return "塞尔维特";
        if("迪拜沙巴布阿尔阿赫利".equalsIgnoreCase(teamName))return "阿尔阿赫利";
        if("阿尔沙迦".equalsIgnoreCase(teamName))return "沙瑞加";
        if("依塔".equalsIgnoreCase(teamName))return "伊塔尔";
        if("埃斯特雷马都拉".equalsIgnoreCase(teamName))return "艾斯马度华";
        if("阿尔萨特".equalsIgnoreCase(teamName))return "艾沙尔特";
        if("阿赫利".equalsIgnoreCase(teamName))return "艾尔利";
        if("NS 穆拉".equalsIgnoreCase(teamName))return "慕拉";
        if("NK 特里格拉夫".equalsIgnoreCase(teamName))return "拖里格拉瓦";
        if("奥斯曼里体育 FK".equalsIgnoreCase(teamName))return "奥斯曼里士邦";
        if("加齐瑟希尔 加齐安特普 FK".equalsIgnoreCase(teamName))return "加辛塔BB";
        if("圣马丁图库曼".equalsIgnoreCase(teamName))return "图库曼圣马丁";
        if("班菲特".equalsIgnoreCase(teamName))return "班菲尔德";
        if("廸波特斯".equalsIgnoreCase(teamName))return "秘鲁体育大学";
        if("万卡约运动".equalsIgnoreCase(teamName))return "万卡约";
        if("苏瓦 FC".equalsIgnoreCase(teamName))return "苏瓦足协";
        if("肯萨斯体育会".equalsIgnoreCase(teamName))return "肯萨斯竞技";
        if("FK 图门".equalsIgnoreCase(teamName))return "FK秋明";
        if("法克尔 沃罗涅日".equalsIgnoreCase(teamName))return "沃罗涅日火炬";
        if("艾美利亚".equalsIgnoreCase(teamName))return "阿尔梅里亚";
        if("沙希尔 SC".equalsIgnoreCase(teamName))return "Al沙希尔";
        if("阿拉毕 SC".equalsIgnoreCase(teamName))return "阿拉比科威特";
        if("AA 弗拉门戈 SP U20".equalsIgnoreCase(teamName))return "法林明高(SP)青年队";

        String tmpstr = teamName.replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)", "").trim().replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("队", "").trim();

        if(StringUtils.isBlank(tmpstr)){
            String name =  teamName.replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)","").replaceAll("FC","").trim();

            if(name.lastIndexOf("s")==name.length()-1){
                return name.substring(0,name.length()-1);
            }else {
                return name;
            }
        }else {
            return tmpstr;
        }



      /*  if("en".equalsIgnoreCase(language)){
           // teamName.indexOf(",")==teamName.length();
            String name =  teamName.replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)","").replaceAll("FC","").trim();

            if(name.lastIndexOf("s")==name.length()-1){
                return name.substring(0,name.length()-1);
            }else {
                return name;
            }
        }else {
            String tmpstr = teamName.replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)", "").replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("队", "").trim();
            if(StringUtils.isBlank(tmpstr)){
                return teamName.replaceAll("\\（(.*?)\\）","").replaceAll("\\((.*?)\\)","").trim();
            }else {
                return tmpstr;
            }
          // return  StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.deleteWhitespace(teamName), "FC"), "队"), "(青年队)"), "（女）"), "（青年队）"),"SC"),"SKU"),"(后备)");
        }*/
     }

    public static double canUp(float odds_){

        double canUP = -0.06;
        if(odds_<1.06){
            canUP = -0.07;
        }else if(odds_>=1.06&&odds_<1.27){
            canUP=-0.09;
        }else if(odds_>=1.27&&odds_<1.37){
            canUP=-0.11;
        }else if(odds_>=1.37&&odds_<1.47){
            canUP=-0.12;
        }else if(odds_>=1.47&&odds_<1.57){
            canUP=-0.13;
        }else if(odds_>=1.57&&odds_<1.67){
            canUP=-0.14;
        }else if(odds_>=1.67&&odds_<1.78){
            canUP=-0.17;
        }else if(odds_>=1.78&&odds_<2.00){
            canUP=-0.23;
        }else if(odds_>=2.00&&odds_<2.25){
            canUP=-0.27;
        }else if(odds_>=2.25&&odds_<2.48){
            canUP=-0.33;
        }else if(odds_>=2.48&&odds_<2.70){
            canUP=-0.39;
        }else if (odds_>=2.7&&odds_<3.10){
            canUP=-0.49;
        }else if (odds_>=3.10&&odds_<3.50){
            canUP=-0.59;
        }else if(odds_>=3.50){
            canUP=-0.70;
        }

        return canUP;
    }

    public static float getOdds(MsTyMatch msTyMatch){
        if(msTyMatch.getMasterGoalNum()>msTyMatch.getGuestGoalNum()){
            msTyMatch.setBetH(true);
        }else {
            msTyMatch.setBetH(false);
        }
        float betOdds;

        if(msTyMatch.getMatchStep()==1) {
            if(msTyMatch.isBetH()){
                betOdds = msTyMatch.getHalfUpperOdds();
            }else {
                betOdds = msTyMatch.getHalfDownOdds();
            }
        }else {
            if(msTyMatch.isBetH()){
                betOdds = msTyMatch.getUpperOdds();
            }else {
                betOdds = msTyMatch.getDownOdds();
            }
        }
        return betOdds;
    }

    public static int isMastLead(MsTyMatch msTyMatch){
        if(msTyMatch.getMasterGoalNum()-msTyMatch.getGuestGoalNum()>0){
           return 1;
        }else if(msTyMatch.getMasterGoalNum()-msTyMatch.getGuestGoalNum()==0){
            return 0;
        }else {
            return -1;
        }
    }

    /*
    //        STR:M_0_1H_3657302_30_0.950000_2_-0.2500_001_000_0_
      //    M_0_上半场_比赛编号_赔率前缀_赔率_2_平手盘_主队进球_客队进球_金额_
//        IsParlay:false
//        Mode:1
//        betLog:

     */

/*    public static String getBetStr(MsTyMatch msTyMatch,boolean isPreBet){
        StringBuilder str = new StringBuilder("M_0_");
        float betOdds;
        float handicap;
        String capfix;
        if(msTyMatch.getMatchStep()==1) {
            if(msTyMatch.isBetH()){
                str.append("1H");
                betOdds = msTyMatch.getHalfUpperOdds();
            }else {
                str.append("1A");
                betOdds = msTyMatch.getHalfDownOdds();
            }
            handicap = msTyMatch.getHalfHandicap();
            capfix = msTyMatch.getHalfCapfix();
        }else {
            if(msTyMatch.isBetH()){
                str.append("2H");
                betOdds = msTyMatch.getUpperOdds();
            }else {
                str.append("2A");
                betOdds = msTyMatch.getDownOdds();
            }
            handicap = msTyMatch.getHandicap();
            capfix = msTyMatch.getCapfix();
        }
        str.append("_").append(StringUtils.leftPad(msTyMatch.getMatchNo()+"",7,"0")).append("_")
                .append(StringUtils.leftPad(capfix,2,"0")).append("_")
                .append(StringUtils.rightPad(betOdds+"",8,"0")).append("_").append("2").append("_")
                .append(StringUtils.rightPad(handicap+"",7,"0")).append("_")
                .append(StringUtils.leftPad(msTyMatch.getMasterGoalNum()+"",3,"0")).append("_")
                .append(StringUtils.leftPad(msTyMatch.getGuestGoalNum()+"",3,"0")).append("_");
        if(isPreBet){
            str.append("0");
        }else {
            str.append(msTyMatch.getMoney());
        }
        str.append("_");
        logger.info("STR:"+str.toString());
        return str.toString();
    }*/

    public static String getBetStr(MsTyMatch msTyMatch,boolean isPreBet){
        StringBuilder str = new StringBuilder("M_0_");
        //float betOdds;
        float handicap;
        String capfix;
        if(msTyMatch.getMatchStep()==1) {
            if(msTyMatch.isBetH()){
                str.append("1H");
               // betOdds = msTyMatch.getHalfUpperOdds();
            }else {
                str.append("1A");
               // betOdds = msTyMatch.getHalfDownOdds();
            }
            handicap = msTyMatch.getHalfHandicap();
            capfix = msTyMatch.getHalfCapfix();
        }else {
            if(msTyMatch.isBetH()){
                str.append("2H");
               /// betOdds = msTyMatch.getUpperOdds();
            }else {
                str.append("2A");
               // betOdds = msTyMatch.getDownOdds();
            }
            handicap = msTyMatch.getHandicap();
            capfix = msTyMatch.getCapfix();
        }
        str.append("_").append(StringUtils.leftPad(msTyMatch.getMatchNo()+"",7,"0")).append("_")
                .append(StringUtils.leftPad(capfix,2,"0")).append("_")
                .append(StringUtils.rightPad(msTyMatch.getBetOdds()+"",8,"0")).append("_").append("2").append("_")
                .append(StringUtils.rightPad(handicap+"",7,"0")).append("_")
                .append(StringUtils.leftPad(msTyMatch.getMasterGoalNum()+"",3,"0")).append("_")
                .append(StringUtils.leftPad(msTyMatch.getGuestGoalNum()+"",3,"0")).append("_");
        if(isPreBet){
            str.append("0");
        }else {
            str.append(msTyMatch.getMoney());
        }
        str.append("_");
        logger.info("STR:"+str.toString());
        return str.toString();
    }

    public static String uniqRand() {
        Date j = new Date();

        //String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String y = "2018";
        int k = j.getMonth() + 1;
        int e = j.getDate();
        int f = j.getHours();
        int m = j.getMinutes();
        int a = j.getSeconds();
        //int h = j.getMilliseconds();
//        String hh=j.getTime()+"";
//        int h = NumberUtils.toInt(StringUtils.substring(j.getTime()+"",hh.length()-3,hh.length()));
        int h =  new Random().nextInt(1000);
        StringBuilder l = new StringBuilder("");
        l.append(y);
        if(k<10)l.append("0");
        l.append(k);
        if(e<10)l.append("0");
        l.append(e);
        if(f<10)l.append("0");
        l.append(f);
        if(m<10)l.append("0");
        l.append(m);
        if(a<10)l.append("0");
        l.append(a);
        if(h<10){
            l.append("00");
        }else if(h<100&&h>=10){
            l.append("0");
        }
        l.append(h).append("_").append(RandomStringUtils.random(9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).append("00");

        logger.info("y:"+y+"k:"+k+"|e:"+e+"|f:"+f+"|m:"+m+"|a:"+a+"|h:"+h+"|l:"+l+"|");

        return l.toString();
    }


    public static void main(String[] args){
//        String teamId = MatchDataUtils.getLeisuTeamIdByTeam("塔洛纳女足","");
//        logger.info(teamId);
//        logger.info(MatchDataUtils.getLeisuMatchId(teamId));


//        String ss = "2517688,1946,4,1531627800,1531631594,[40445,\"\",0,0,0,0,1,-1]," ;
//        logger.info(StringUtils.substringBefore(ss,","));

        //       logger.info(getMatchData("2444920"));

        //       logger.info(getMatchData("西阿德莱德",""));

        // System.out.print(16.50/11.40);
/*        System.out.println(parseBetResultCode("<script type=\"text/javascript\">var lDt=[\"402\",\"1.110~30~-0.25~0-0.5~1~2|20000.00~5.00~X\"]; parent.processQBDataResult(lDt); </script>"));
        System.out.println(parseBetResultOdds("<script type=\"text/javascript\">var lDt=[\"402\",\"1.110~30~-0.25~0-0.5~1~2|20000.00~5.00~X\"]; parent.processQBDataResult(lDt); </script>",30));
        System.out.println(parseBetResultHandicap("<script type=\"text/javascript\">var lDt=[\"402\",\"1.110~30~-0.25~0-0.5~1~2|20000.00~5.00~X\"]; parent.processQBDataResult(lDt); </script>",30));*/

//        System.out.println(Math.ceil(0.4546));
//        System.out.println(Math.ceil(1.4546));
//        System.out.println(Math.ceil(2.4546));
//
//        System.out.println(1.223*0.990);
////        System.out.println((int)(Math.ceil(1.223 * 0.990)));

/*        logger.info(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击"));
        logger.info(MatchDataUtils.getLeisuMatchId(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击")));
        logger.info(MatchDataUtils.getMatchDataByLsmid(MatchDataUtils.getLeisuMatchId(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击"))));*/
      // logger.info(getMatchData("贝特谢安","马卡比艾哈迈德"));


       // System.out.println( StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.remove(StringUtils.deleteWhitespace("Xanthi FC"), "FC"), "(女)"), "(青年队)"), "（女）"), "（青年队）"),"SC"),"SKU"),"(后备)"));

       // System.out.println( StringUtils.remove("sfs (33)","(.*)"));

/*        String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
        Pattern p = Pattern.compile("qq(.*?)qq(.*?)fd");
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));
            strs.add(m.group(2));
        }
        for (String s : strs){
            System.out.println(s);
        }*/

/*        String str = "(345)sfs (3e3)";
        Pattern p = Pattern.compile("\\((.*?)\\)");
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));
        }
        for (String s : strs){
            str = StringUtils.remove(str,"("+s+")");
        }*/


//        System.out.println("P 0_1s fs我".replaceAll("[^(1-9)]", ""));
//        System.out.println("P2_P1".replaceAll("[^(1-9)]", ""));
//        String ss = "sf中国(前年)ff（4我）2队";
//        System.out.println(ss.replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("\\(\\)","").trim());
//        System.out.println(ss.replaceAll("\\((.*?)\\)","").replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("\\(\\)","").trim());
//        System.out.println(ss.replaceAll("\\（(.*?)\\）","").replaceAll("\\((.*?)\\)","").replaceAll("[^(\\u4e00-\\u9fa5)]", "").trim());


//        System.out.println(NumberUtils.toInt("",0));
//        System.out.println(NumberUtils.toInt("P0_1".replaceAll("[^(1-9)]", ""),0));
//        System.out.println(Math.round(2.888));
//        System.out.println((int)Math.round(1.44));
//        System.out.println((int)Math.round(0.66));
//        System.out.println(Math.round(2.888*800/100)*100);

//
//        Long betMoney = (Math.round((800*1.456705)/100))*100;
//        System.out.println(betMoney.intValue());

/*        String ss = "sdfsdf],[";
        System.out.println(ss.substring(ss.length()-3).equals("],["));*/
//        System.out.println(getMatchData("sfsfssg4444444","布罗马博亚纳女足"));
//
//        System.out.println(getMatchData("布罗马博亚纳女足","ggggWWWWWWWWWWWfff"));

      //  System.out.println( StringUtils.countMatches("布罗马博亚纳马女足","马"));

//        MsTyMatch ms = new MsTyMatch();
//        ms.setMatchData("0:3_0:3_61:39");
//        ms.setMasterGoalNum(0);
//        ms.setGuestGoalNum(3);
//        MatchDataUtils.assessMatchData(ms);
//        System.out.println( MatchDataUtils.assessMatchData(ms));

/*        String ss = "sfsggffssooshhss";

        System.out.println(ss.lastIndexOf("s"));
        System.out.println(ss.length()-1);

        if(ss.lastIndexOf("s")==ss.length()-1){
            System.out.println(ss.substring(0,ss.length()-1));
        }


        System.out.println(cleanTeamName("sfsggffssooshhss","en"));*/

/*        String ss="<script type=\"text/javascript\">var lDt=[\"402\",\"1.110~30~-0.25~0-0.5~1~2|20000.00~5.00~X\"]; parent.processQBDataResult(lDt); </script>";
        String s1 = "<script type=\"text/javascript\">var lDt=[\"0\",\"45000.00^5.00^X\"]; parent.initQBDataResult(lDt); </script>";
        System.out.println(parseBetMoney(ss));
        System.out.println(parseBetMoney(s1));*/

/*        int maxMoney = 9001;
        Long oneMoney = Math.round(maxMoney*0.9/100)*100;*/
       // System.out.println(parseBetResultOdds("<script type=\"text/javascript\">var lDt=[\"0\",\"2000.00^5.00^X\"]; parent.initQBDataResult(lDt); </script>",0));

       // System.out.println(44/10.00);
/*
        System.out.println("我们是)sf(ff)分 美国 Institute （ss）".replaceAll("[^(\\u4e00-\\u9fa5)]", ""));
        System.out.println(cleanTeamName("FC (hou) 中国队 （青年）"));
        System.out.println(cleanTeamName("FNSW Institute (女)"));
        System.out.println(cleanTeamName("美国 Institute (女)"));

        String tmpstr = "我们是)sf(ff)分 美国 Institute （ss）".replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)", "").trim().replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("队", "").trim().replaceAll("\\(","").replaceAll("\\)","");
        System.out.println(tmpstr);

        System.out.println("美国 Institute (女)".replaceAll("\\（(.*?)\\）", "").replaceAll("\\((.*?)\\)", "").trim().replaceAll("[^(\\u4e00-\\u9fa5)]", "").replaceAll("队", "").trim());

        System.out.println(cleanTeamName("sdfsfff(ff)"));
        System.out.println(cleanTeamName("ffff3r23rf"));*/

       // logger.info(getMatchData("皇家贝蒂斯","莱万特"));

/*        float betOdds = (float) 1.51;
        float oldbet = (float) 1.63;

        System.out.println(betOdds-oldbet>-0.5);
        System.out.println(betOdds-oldbet>-0.05);
        System.out.println(betOdds-oldbet);*/
        //System.out.println("99-30:30-5:9".split("-")[2]);

        /*        logger.info(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击"));
        logger.info(MatchDataUtils.getLeisuMatchId(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击")));
        logger.info(MatchDataUtils.getMatchDataByLsmid(MatchDataUtils.getLeisuMatchId(MatchDataUtils.getLeisuTeamId("贝特谢安","鲁伏射击"))));*/
/*

        String c1 = "2".replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        String c2 = "完3美丽".replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        System.out.println("c1:"+c1);
        System.out.println("c2:"+c2);
        System.out.println(c1.equals(c2));
*/

    }

}
