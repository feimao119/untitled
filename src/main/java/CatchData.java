import dao.SinaDataDao;
import domain.FootballGoal;
import domain.FootballMatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: chenzx
 * Date: 13-11-11
 * Time: 下午10:08
 * To change this template use File | Settings | File Templates.
 */
public class CatchData {

    private static final Logger logger = LoggerFactory.getLogger(CatchData.class);

    /**
     * 获取比赛编号
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMatchNo(String srcStr) {
        // String str = "<td class=\"td_2\" style=\"background-color:#FFFF00;\">联盟杯</td>";
        String compileStr = "<td class=\"td_4\"><span id=\"(.*?)st\">(.*?)</span></td>";
        int pos = 1;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取比赛名称
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMatchName(String srcStr) {
       // String str = "<td class=\"td_2\" style=\"background-color:#FFFF00;\">联盟杯</td>";
        String compileStr = "<td class=\"td_2\" style=\"background-color:(.*?);\">(.*?)</td>";
        int pos = 2;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取比赛时间
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMatchTime(String srcStr) {
        // String str = "<td class="td_3">11-01 00:00</td>";
        String compileStr = "<td class=\"td_3\">(.*?)</td>";
        int pos = 1;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取比赛状态
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMatchFinal(String srcStr) {
        // String str = "<td class="td_3">11-01 00:00</td>";
        String compileStr = "<td class=\"td_4\"><span id=\"(.*?)st\">(.*?)</span></td>";
        int pos = 2;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取主队编号
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMasterTeamNo(String srcStr) {
        // String str = "<td class="td_5"><span id="887089r1"></span><a href="http://match.sports.sina.com.cn/football/team.php?id=3967" target="_blank" style='text-decoration:none'>莫斯科中央陆军</a></td>";
        String compileStr = "<td class=\"td_5\"><span id=\"(.*?)\">(.*?)</span><a href=\"(.*?)id=(.*?)\" " +
                "target=\"_blank\" style='text-decoration:none'>(.*?)</a></td>";

        int pos = 4;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取主队名称
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMasterTeamName(String srcStr) {
        // String str = "<td class="td_5"><span id="887089r1"></span><a href="http://match.sports.sina.com.cn/football/team.php?id=3967" target="_blank" style='text-decoration:none'>莫斯科中央陆军</a></td>";
        String compileStr = "<td class=\"td_5\"><span id=\"(.*?)\">(.*?)</span><a href=\"(.*?)id=(.*?)\" " +
                "target=\"_blank\" style='text-decoration:none'>(.*?)</a></td>";
        int pos = 5;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取比分
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getScore(String srcStr,String matchNo) {
//         String str = "<td class=\"td_6\" style=\"cursor:pointer\"><a  onclick=\"opens('887089','2012-11-01')\" target=\"_blank\" style=\"text-decoration:none\">\n" +
//                 "\t\t\t<span id=\"887089s1\">3-0</span></a></td>\n";
        String compileStr = "<span id=\""+matchNo+"s1\">(.*?)</span></a></td>";
        int pos = 1;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取比分
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getScore(String srcStr) {
//         String str = "<td class=\"td_6\" style=\"cursor:pointer\"><a  onclick=\"opens('887089','2012-11-01')\" target=\"_blank\" style=\"text-decoration:none\">\n" +
//                 "\t\t\t<span id=\"887089s1\">3-0</span></a></td>\n";
//        String compileStr = "<td class=\"td_6\"(.*?)s1\">(.*?)</span></a></td>";
//        int pos = 2;
//        return getData(srcStr,compileStr,pos);

        String compileStr = "<span id=\"(.*?)s1\">(.*?)</span></a></td>";
        int pos = 2;
        return getData(srcStr,compileStr,pos);
    }


    /**
     * 获取客队编号
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getGuestTeamNo(String srcStr) {
        // String str = "<td class="td_5"><span id="887089r1"></span><a href="http://match.sports.sina.com.cn/football/team.php?id=3967" target="_blank" style='text-decoration:none'>莫斯科中央陆军</a></td>";
        String compileStr = "<td class=\"td_7\"><a href=\"(.*?)id=(.*?)\" target=\"_blank\" style='text-decoration:none'>(.*?)</a><span id=\"(.*?)\">";

        int pos = 2;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取客队名称
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getGuestTeamName(String srcStr) {
        // String str = "<td class="td_5"><span id="887089r1"></span><a href="http://match.sports.sina.com.cn/football/team.php?id=3967" target="_blank" style='text-decoration:none'>莫斯科中央陆军</a></td>";
        String compileStr = "<td class=\"td_7\"><a href=\"(.*?)id=(.*?)\" target=\"_blank\" style='text-decoration:none'>(.*?)</a><span id=\"(.*?)\">";
        int pos = 3;
        return getData(srcStr,compileStr,pos);
    }

    /**
     * 获取半场比分
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getHalfScore(String srcStr) {
//         String str = "<td class="td_8"><span id="845685s2">1-0</span>";
        String compileStr = "<td class=\"td_8\"><span id=\"(.*?)s2\">(.*?)</span>";
        int pos = 2;
        return getData(srcStr,compileStr,pos);
    }

    //公用data
    private  ArrayList<String> getData(String srcStr,String compileStr, int pos) {
        Pattern p = Pattern.compile(compileStr);
        Matcher m = p.matcher(srcStr);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(pos));
        }
//        for (String s : strs){
//            System.out.println(s);
//        }
        return strs;
    }


    //例子
    private  void getStrings() {
        String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
        Pattern p = Pattern.compile("qq(.*?)qq(.*?)fd");
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));
            strs.add(m.group(2));
        }
        for (String s : strs){
            System.out.println(s);
        }
    }

    //=====================================================================================================
    /**
     * 主队进球时间
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMasterGoalTime(String srcStr) {
        String compileStr = "<p align='left'><img src='http://image2.sina.com.cn/ty/footballdata/live/bfzb_mj_018.gif' width=12 height=12 />(.*?)’(.*?)</p>";
//        int pos = 1;
//        return getData(srcStr,compileStr,pos);

        //重复处理
        ArrayList<String> results = new ArrayList<String>();

        ArrayList<String> times = getData(srcStr,compileStr,1);
        ArrayList<String> names = getData(srcStr,compileStr,2);

        for(int i=0; i<times.size(); i++){
            if(i==0){
                results.add(times.get(i));
                continue;
            }

            if(times.get(i).trim().equalsIgnoreCase(times.get(i-1).trim()) && names.get(i).trim().equalsIgnoreCase(names.get(i-1).trim())){
                continue;
            }else {
                results.add(times.get(i));
            }
        }
        return results;
    }

    /**
     * 主队进球球员
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getMasterGoalName(String srcStr) {
        String compileStr = "<p align='left'><img src='http://image2.sina.com.cn/ty/footballdata/live/bfzb_mj_018.gif' width=12 height=12 />(.*?)’(.*?)</p>";
//        int pos = 2;
//        return getData(srcStr,compileStr,pos);

        //重复处理
        ArrayList<String> results = new ArrayList<String>();

        ArrayList<String> times = getData(srcStr,compileStr,2);
        ArrayList<String> names = getData(srcStr,compileStr,1);

        for(int i=0; i<times.size(); i++){
            if(i==0){
                results.add(times.get(i));
                continue;
            }

            if(times.get(i).trim().equalsIgnoreCase(times.get(i-1).trim()) && names.get(i).trim().equalsIgnoreCase(names.get(i-1).trim())){
                continue;
            }else {
                results.add(times.get(i));
            }
        }
        return results;
    }

    /**
     * 客队进球时间
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getGuestGoalTime(String srcStr) {
        String compileStr = "<p align='right'><img src='http://image2.sina.com.cn/ty/footballdata/live/bfzb_mj_018.gif' width=12 height=12 />(.*?)’(.*?)</p>";
//        int pos = 1;
//        return getData(srcStr,compileStr,pos);

        //重复处理
        ArrayList<String> results = new ArrayList<String>();

        ArrayList<String> times = getData(srcStr,compileStr,1);
        ArrayList<String> names = getData(srcStr,compileStr,2);

        for(int i=0; i<times.size(); i++){
            if(i==0){
                results.add(times.get(i));
                continue;
            }

           if(times.get(i).trim().equalsIgnoreCase(times.get(i-1).trim()) && names.get(i).trim().equalsIgnoreCase(names.get(i-1).trim())){
               continue;
            }else {
               results.add(times.get(i));
           }
        }
        return results;
    }

    /**
     * 客队进球队员
     * @param srcStr
     * @return
     */
    private  ArrayList<String> getGuestGoalName(String srcStr) {
        String compileStr = "<p align='right'><img src='http://image2.sina.com.cn/ty/footballdata/live/bfzb_mj_018.gif' width=12 height=12 />(.*?)’(.*?)</p>";
//        int pos = 2;
//        return getData(srcStr,compileStr,pos);

        //重复处理
        ArrayList<String> results = new ArrayList<String>();

        ArrayList<String> times = getData(srcStr,compileStr,2);
        ArrayList<String> names = getData(srcStr,compileStr,1);

        for(int i=0; i<times.size(); i++){
            if(i==0){
                results.add(times.get(i));
                continue;
            }

            if(times.get(i).trim().equalsIgnoreCase(times.get(i-1).trim()) && names.get(i).trim().equalsIgnoreCase(names.get(i-1).trim())){
                continue;
            }else {
                results.add(times.get(i));
            }
        }
        return results;
    }

   public void test(){
       String matchListStr = HttpClientUtils.get("http://data.sports.sina.com.cn/live/matchlist.php?day=2012-11-07");
       this.getScore(matchListStr);
   }

    public  void parseList(String dateStr){

        String matchListStr = null;

        while (matchListStr==null){
            try{
                matchListStr = HttpClientUtils.get("http://data.sports.sina.com.cn/live/matchlist.php?day="+dateStr);
            }catch (Exception e){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        List<String> matchNos = this.getMatchNo(matchListStr);
        List<String> matchNames = this.getMatchName(matchListStr);
        List<String> matchTimes = this.getMatchTime(matchListStr);
        List<String> matchFinal = this.getMatchFinal(matchListStr);
        List<String> masterTeamNos = this.getMasterTeamNo(matchListStr);
        List<String> masterTeamNames = this.getMasterTeamName(matchListStr);
        List<String> guestTeamNos = this.getGuestTeamNo(matchListStr);
        List<String> guestTeamNames = this.getGuestTeamName(matchListStr);
        List<String> scores = this.getScore(matchListStr);
        List<String> halfScores = this.getHalfScore(matchListStr);



          for(int i=0; i< matchNos.size(); i++){

              if(StringUtils.isBlank(matchFinal.get(i))||!matchFinal.get(i).equalsIgnoreCase("完场")){
                  logger.error("未完场，跳过～"+matchNos.get(i));
                  continue;
              }

              FootballMatch footballMatch = new FootballMatch();
              footballMatch.setMatchNo(matchNos.get(i));
              footballMatch.setMatchName(matchNames.get(i));
              footballMatch.setMatchTime(matchTimes.get(i));
              footballMatch.setMasterTeamNo(masterTeamNos.get(i));
              footballMatch.setMasterTeamName(masterTeamNames.get(i));
              footballMatch.setGuestTeamNo(guestTeamNos.get(i));
              footballMatch.setGuestTeamName(guestTeamNames.get(i));
              footballMatch.setScore(scores.get(i));
              footballMatch.setHalfScore(halfScores.get(i));
              if(footballMatch.getScore().length()<3){
                  logger.error("比分错误，跳过～"+footballMatch.toString());
                  continue;
              }
              String[] scoreArray = footballMatch.getScore().split("-");
              footballMatch.setMasterGoalNum(Integer.parseInt(scoreArray[0].trim()));
              footballMatch.setGuestGoalNum(Integer.parseInt(scoreArray[1].trim()));



              String goalListStr = null;

              while (goalListStr==null){
                  try{
                      goalListStr = HttpClientUtils.get("http://data.sports.sina.com.cn/live/goal.php?mid="+footballMatch.getMatchNo()+"&date="+dateStr);
                  }catch (Exception e){
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e1) {
                          e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                      }
                  }
              }

              List<String> masterGoalTimes = this.getMasterGoalTime(goalListStr);
              List<String> masterGoalNames = this.getMasterGoalName(goalListStr);

              for(int m=0; m<masterGoalTimes.size(); m++){
                  FootballGoal footballGoal = new FootballGoal();
                  footballGoal.setMatchNo(footballMatch.getMatchNo());
                  footballGoal.setGoalTime(Integer.parseInt(masterGoalTimes.get(m).trim()));
                  footballGoal.setGoalName(masterGoalNames.get(m));
                  footballGoal.setMaster(1);

                  logger.info(footballGoal.toString());
                  SinaDataDao.getInstance().insertFootballGoal(footballGoal);
              }

              List<String> guestGoalTimes = this.getGuestGoalTime(goalListStr);
              List<String> guestGoalNames = this.getGuestGoalName(goalListStr);

              for(int m=0; m<guestGoalTimes.size(); m++){
                  FootballGoal footballGoal = new FootballGoal();
                  footballGoal.setMatchNo(footballMatch.getMatchNo());
                  footballGoal.setGoalTime(Integer.parseInt(guestGoalTimes.get(m).trim()));
                  footballGoal.setGoalName(guestGoalNames.get(m));
                  footballGoal.setMaster(0);

                  logger.info(footballGoal.toString());
                  SinaDataDao.getInstance().insertFootballGoal(footballGoal);
              }

              if(masterGoalTimes.size()==footballMatch.getMasterGoalNum() && guestGoalTimes.size()==footballMatch.getGuestGoalNum()){
                  footballMatch.setStatus(1);
              } else{
                  footballMatch.setStatus(0);
              }

              footballMatch.setCatchTime(new Date());
              footballMatch.setMatchDate(dateStr);
              logger.info(footballMatch.toString());
              SinaDataDao.getInstance().insertFootballMatch(footballMatch);
          }

        //System.out.println(matchListStr);

        // getScore(matchListStr,"876678");
        //  getScore(matchListStr);

//        String goalListStr = HttpClientUtils.get("http://data.sports.sina.com.cn/live/goal.php?mid=856332&date=2012-11-11");
//        System.out.println(goalListStr);
//        getGuestGoalName(goalListStr);
    }

    public void run(){
        String startDate = "2008-01-01";
        String endDate = "2014-06-12" ;
        while (!startDate.equalsIgnoreCase(endDate)){
            logger.info("=================="+startDate+"============================");
            this.parseList(startDate);
            startDate = DateTools.getNextDate(startDate);
        }
    }

    public static   void main(String[] args) throws Exception {
        //http://data.sports.sina.com.cn/live/matchlist.php?day=2012-11-07
        //获取比赛列表

//        CatchData catchData = new CatchData();
//        catchData.run();

    }

}
