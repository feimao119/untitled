package dao;

import domain.MsTyMatch;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.LoggerFactory;
import utils.DBConnection;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: heroc
 * Date: 18-8-18
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class MsTyMatchDao {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MsTyMatchDao.class);

    private static final MsTyMatchDao instance = new MsTyMatchDao();

    private MsTyMatchDao() {
    }

    public static MsTyMatchDao getInstance() {
        return instance;
    }
/*    private int id;
    private int matchNo;
    private String leagueName;
    private int matchTime = 0;
    private String masterTeamName;
    private String guestTeamName;
    private float baseOdds;
    private float betOdds;
//    private String score;
//    private String halfScore;
    private int masterGoalNum;
    private int guestGoalNum;
    private int matchStep;
    private String matchData = "";
    private String baseMatchData="";
    private String capfix;
    private String halfCapfix;
    private float handicap;        //盘口
    private float halfHandicap;
    private float upperOdds;     //赔率
    private float downOdds;
    private float halfUpperOdds;
    private float halfDownOdds;

    //比赛数据                           //射门:客射门_射正:客射正_控球率:客控球率_主赔率:客赔率_盘口_比分_比赛时间|
  //  private String halfMatchData = "";   //7:10_4:9_54:46_1.075:0.725_0_1:0_40|

    // private boolean isUpMatchData = false;
  //  private String betData = "";//投注数据
   // private int betCount = 0;
    private boolean isBetH; //上下盘
    private boolean hasRedCard;
    private int money;
    private int maxMoney;
    private String matchDate;
    private java.util.Date saveTime;
    private int status;*/

    public void insertMsTyMatch(MsTyMatch msTyMatch){
        msTyMatch.setSaveTime(new Date());
        Connection conn = null;
        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        int num=0;
        try {
            conn = DBConnection.getInstance().getConnection();
            pstmt = conn
                    .prepareStatement("insert into msty_match(" +
                            "match_no,league_name,match_time,master_team_name,guest_team_name,bet_odds," +
                            "base_odds,master_goal_num,guest_goal_num,match_step,match_data,base_match_data," +
                            "capfix,half_capfix,handicap,half_handicap,upper_odds,down_odds,half_upper_odds,half_down_odds,"+
                            "is_bet_h,has_red_card,money,max_money,match_date,save_time,status,bet_money"+
                            ") values(?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?,?,?," +
                            "?,?,?,?,?,?,?,?)");

           // logger.info(pstmt.toString());

            pstmt.setInt(1,msTyMatch.getMatchNo());
            pstmt.setString(2,msTyMatch.getLeagueName());
            pstmt.setInt(3,msTyMatch.getMatchTime());
            pstmt.setString(4,msTyMatch.getMasterTeamName());
            pstmt.setString(5,msTyMatch.getGuestTeamName());
            pstmt.setFloat(6,msTyMatch.getBetOdds());
            
            pstmt.setFloat(7,msTyMatch  .getBaseOdds());
            pstmt.setInt(8,msTyMatch  .getMasterGoalNum());
            pstmt.setInt(9,msTyMatch  .getGuestGoalNum());
            pstmt.setInt(10, msTyMatch  .getMatchStep());
            pstmt.setString(11, msTyMatch  .getMatchData());
            pstmt.setString(12, msTyMatch  .getBaseMatchData());
            
            pstmt.setString(13,msTyMatch  .getCapfix());
            pstmt.setString(14,msTyMatch  .getHalfCapfix());
            pstmt.setFloat(15,msTyMatch  .getHandicap());
            pstmt.setFloat(16,msTyMatch  .getHalfHandicap());
            pstmt.setFloat(17,msTyMatch  .getUpperOdds());
            pstmt.setFloat(18,msTyMatch  .getDownOdds());
            pstmt.setFloat(19,msTyMatch  .getHalfUpperOdds());
            pstmt.setFloat(20,msTyMatch  .getHalfDownOdds());

            pstmt.setBoolean(21,msTyMatch  .isBetH());
            pstmt.setBoolean(22,msTyMatch  .isHasRedCard());
            pstmt.setInt(23,msTyMatch  .getMoney());
            pstmt.setFloat(24,msTyMatch  .getMaxMoney());
            pstmt.setString(25,msTyMatch  .getMatchDate());
            pstmt.setTimestamp(26,new Timestamp(msTyMatch  .getSaveTime().getTime()));
            pstmt.setInt(27,msTyMatch  .getStatus());
            pstmt.setInt(28,msTyMatch  .getBetMoney());

            pstmt.executeUpdate();

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally{
          //  DBConnection.close(rs);
            DBConnection.close(pstmt);
            DBConnection.close(conn);
        }

    }

    public void updataStatus(int matchNo, int status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            String sql = "update  msty_match set status=? "
                    + " where match_no =? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,status);
            pstmt.setInt(2,matchNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close(conn);
        }

    }

    public int getBetMoney(int matchNo,int matchStep) {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            int betMoney = 0;
            try {
                conn = DBConnection.getInstance().getConnection();

                String sql = " SELECT  " +
                        "            SUM(bet_money)  " +
                        "    FROM msty_match  " +
                        "    WHERE match_no = ? and match_step = ? ";

                ps = conn.prepareStatement(sql);
                ps.setInt(1, matchNo);
                ps.setInt(2, matchStep);
                rs = ps.executeQuery();

                while (rs.next()) {
                    betMoney = rs.getInt(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                DBConnection.close(rs);
                DBConnection.close(ps);
                DBConnection.close(conn);
            }
            return betMoney;
        }


    public String selectMatchData(int matchNo,int baseTime, int matchStep) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String matchData = null;
        try {
            conn = DBConnection.getInstance().getConnection();

            String sql = " SELECT  " +
                    "            match_data  " +
                    "    FROM msty_match  " +
                    "    WHERE match_no = ?  " +
                    "    AND match_time >= ?  " +
                    "    AND match_step = ?  " +
                    "    ORDER BY match_time ASC  " +
                    "    LIMIT 1";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, matchNo);
            ps.setInt(2, baseTime);
            ps.setInt(3, matchStep);
            rs = ps.executeQuery();

            while (rs.next()) {
                matchData = rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(ps);
            DBConnection.close(conn);
        }
        return matchData;
    }

}
