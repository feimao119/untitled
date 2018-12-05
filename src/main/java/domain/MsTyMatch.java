package domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-7-6
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 * ['10022547','0','中国女子足球锦标赛|0',
 '3644665','0','201807051600','河北华夏幸福 (女)','河南徽商 (女)','0_1','1','1','41`','0_0','0_0','|0','1|0','Y','1','0','16',
 '0.2500','|0-0.5',,'0.7700|1.6700','0.9300|1.8300',,,'5','3','3.7500','3.5-4',,'0.6700|1.5700','1.0300|1.9300',,,,,,,,,,'1','7','1','3.2000|','2.8500|','2.2000|','1|-1',,'2','2','0.0000','0|',,'1.0400|1.9400','0.6600|1.5600',
 ,,'6','4','2.5000','2.5',,'2.1700|2.7800','0.1600|1.0600',,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

 [联赛编号，0，联赛名称|0，赛事编号，0，时间戳，主队名，客队名，主队得分，客队得分，比赛步骤（1,2.3），比赛已进行时间，0_0，0_0, |0,1|0,N（Y）,1,0,1,盘口，显示盘口，，全场上盘赔率|？，全场下盘赔率|?，，，5，3，全场大小盘盘口，
 全场大小盘显示盘口，，大球赔率|？，小球赔率|？,,,,,,,,,,'1','7','1',胜赔|，平赔|，负赔|,'1|-1',,'2','2','0.0000(平手盘)','0|',,上半场胜赔率|，上半场负赔率|，
 ,,'6','4',上半场大小盘盘口，上半场大小盘显示盘口，，上半场大赔率|，半场小赔率|,,,,,,,,,,,,,,,,,,'1','0_0__N_90','16'],

 [联赛编号，5501(角球)，联赛名称-角球|0，赛事编号，0，时间戳，主队名，客队名，比分，0，1，比赛已进行时间，0_0，0_0, |0,1|0,N（Y）,1,0,1,
 */
public class MsTyMatch {
    private int id;
/*    private String leagueNo;
    private int leagueType;*/
    private String leagueName;

    private int matchNo;
    private String matchDate;

    private int matchStep;
    private int matchTime = 0;

    private String masterTeamName;
    private String guestTeamName;

//    private String score;
//    private String halfScore;

    private int masterGoalNum;
    private int guestGoalNum;

    private String capfix;
    private String halfCapfix;

    private float handicap;        //盘口
    private float halfHandicap;

    private float upperOdds;     //赔率
    private float downOdds;

    private float halfUpperOdds;
    private float halfDownOdds;

    //比赛数据                           //射门:客射门_射正:客射正_控球率:客控球率_主赔率:客赔率_盘口_比分_比赛时间|
 //   private String halfMatchData = "";   //7:10_4:9_54:46_1.075:0.725_0_1:0_40|
    private String matchData = "";
   // private boolean isUpMatchData = false;

    //private String betData = "";//投注数据

    //private int betCount = 0;

    private boolean isBetH; //上下盘

    private int money;  //投注金额

    private boolean hasRedCard;

    private float baseOdds;

    private float betOdds;

    private String baseMatchData="";
   // private int baseTime = 0;
    private int maxMoney;

    private int betMoney=0;  //投注成功金额

    private Date saveTime;
    private int status;   //默认0，入库1，出库2
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(int matchNo) {
        this.matchNo = matchNo;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

/*
    public boolean isUpMatchData() {
        return isUpMatchData;
    }

    public void setUpMatchData(boolean upMatchData) {
        isUpMatchData = upMatchData;
    }
*/

    public int getMatchStep() {
        return matchStep;
    }

    public void setMatchStep(int matchStep) {
        this.matchStep = matchStep;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }

    public String getMasterTeamName() {
        return masterTeamName;
    }

    public void setMasterTeamName(String masterTeamName) {
        this.masterTeamName = masterTeamName;
    }

    public String getGuestTeamName() {
        return guestTeamName;
    }

    public void setGuestTeamName(String guestTeamName) {
        this.guestTeamName = guestTeamName;
    }
/*
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }*/

    public int getMasterGoalNum() {
        return masterGoalNum;
    }

    public void setMasterGoalNum(int masterGoalNum) {
        this.masterGoalNum = masterGoalNum;
    }

    public int getGuestGoalNum() {
        return guestGoalNum;
    }

    public void setGuestGoalNum(int guestGoalNum) {
        this.guestGoalNum = guestGoalNum;
    }

    public String getCapfix() {
        return capfix;
    }

    public void setCapfix(String capfix) {
        this.capfix = capfix;
    }

    public String getHalfCapfix() {
        return halfCapfix;
    }

    public void setHalfCapfix(String halfCapfix) {
        this.halfCapfix = halfCapfix;
    }

    public float getHandicap() {
        return handicap;
    }

    public void setHandicap(float handicap) {
        this.handicap = handicap;
    }

    public float getHalfHandicap() {
        return halfHandicap;
    }

    public void setHalfHandicap(float halfHandicap) {
        this.halfHandicap = halfHandicap;
    }

    public float getUpperOdds() {
        return upperOdds;
    }

    public void setUpperOdds(float upperOdds) {
        this.upperOdds = upperOdds;
    }

    public float getDownOdds() {
        return downOdds;
    }

    public void setDownOdds(float downOdds) {
        this.downOdds = downOdds;
    }

    public float getHalfUpperOdds() {
        return halfUpperOdds;
    }

    public void setHalfUpperOdds(float halfUpperOdds) {
        this.halfUpperOdds = halfUpperOdds;
    }

    public float getHalfDownOdds() {
        return halfDownOdds;
    }

    public void setHalfDownOdds(float halfDownOdds) {
        this.halfDownOdds = halfDownOdds;
    }
/*
    public String getHalfMatchData() {
        return halfMatchData;
    }

    public void setHalfMatchData(String halfMatchData) {
        this.halfMatchData = halfMatchData;
    }*/

    public String getMatchData() {
        return matchData;
    }

    public void setMatchData(String matchData) {
        this.matchData = matchData;
    }

/*
    public String getBetData() {
        return betData;
    }

    public void setBetData(String betData) {
        this.betData = betData;
    }
*/

/*    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }*/

    public boolean isBetH() {
        return isBetH;
    }

    public void setBetH(boolean betH) {
        isBetH = betH;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isHasRedCard() {
        return hasRedCard;
    }

    public void setHasRedCard(boolean hasRedCard) {
        this.hasRedCard = hasRedCard;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public float getBaseOdds() {
        return baseOdds;
    }

    public void setBaseOdds(float baseOdds) {
        this.baseOdds = baseOdds;
    }

    public String getBaseMatchData() {
        return baseMatchData;
    }

    public void setBaseMatchData(String baseMatchData) {
        this.baseMatchData = baseMatchData;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }

    public float getBetOdds() {
        return betOdds;
    }

    public void setBetOdds(float betOdds) {
        this.betOdds = betOdds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(int betMoney) {
        this.betMoney = betMoney;
    }

    /*    public int getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(int baseTime) {
        this.baseTime = baseTime;
    }*/

    @Override
    public String toString() {
        return "MsTyMatch{" +
              //  "id=" + id +
                ", matchNo=" + matchNo +
                ", leagueName='" + leagueName + '\'' +
                ", baseMatchData='" + baseMatchData + '\'' +
                ", matchData='" + matchData + '\'' +
          //      ", isUpMatchData='" + isUpMatchData + '\'' +
                ", matchStep=" + matchStep +
                ", matchTime=" + matchTime +
                ", baseOdds=" + baseOdds +
           //     ", baseTime=" + baseTime +
                ", masterTeamName='" + masterTeamName + '\'' +
                ", guestTeamName='" + guestTeamName + '\'' +
                ", masterGoalNum=" + masterGoalNum +
                ", guestGoalNum=" + guestGoalNum +

              //  ", matchDate='" + matchDate + '\'' +

            //    ", score='" + score + '\'' +
          //      ", halfScore='" + halfScore + '\'' +
             //   ", capfix=" + capfix +
              //  ", halfCapfix=" + halfCapfix +
                ", handicap=" + handicap +
                ", halfHandicap=" + halfHandicap +
                ", upperOdds=" + upperOdds +
                ", downOdds=" + downOdds +
                ", halfUpperOdds=" + halfUpperOdds +
                ", halfDownOdds=" + halfDownOdds +
           //     ", halfMatchData='" + halfMatchData + '\'' +
            //    ", betData='" + betData + '\'' +
            //    ", betCount='" + betCount + '\'' +
             //   ", isBetH='" + isBetH + '\'' +
           //     ", money='" + money + '\'' +
                  ", betMoney='" + betMoney + '\'' +
                  ", betOdds='" + betOdds + '\'' +
                  ", status='" + status + '\'' +
           //     ", hasRedCard='" + hasRedCard + '\'' +
           //     ", saveTime='" + saveTime + '\'' +
                '}';
    }
}
