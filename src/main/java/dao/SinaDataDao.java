package dao;
import domain.FootballGoal;
import domain.FootballMatch;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import utils.DBConnection;

import java.sql.*;

public class SinaDataDao {
	private static final Logger LOG = Logger.getLogger(SinaDataDao.class
            .getName());

	private static final SinaDataDao instance = new SinaDataDao();

	private SinaDataDao() {

	}

	public static SinaDataDao getInstance() {
		return instance;
	}

	
	public void insertFootballMatch(FootballMatch footballMatch){
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 int num=0;
		 try {
				conn = DBConnection.getInstance().getConnection();
				pstmt = conn
						.prepareStatement("insert into football_match(" +
                                "match_no,match_name,match_time,master_team_no,master_team_name," +
                                "guest_team_no,guest_team_name,score,half_score,master_goal_num," +
                                "guest_goal_num,status,catch_time,half_master_goal_num,half_guest_goal_num,match_date"+
                                ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

             LOG.info(pstmt);

			 pstmt.setString(1,footballMatch.getMatchNo().trim());
             pstmt.setString(2,footballMatch.getMatchName().trim());
             pstmt.setString(3,footballMatch.getMatchTime().trim());
             pstmt.setString(4,footballMatch.getMasterTeamNo().trim());
             pstmt.setString(5,footballMatch.getMasterTeamName().trim());

             pstmt.setString(6,footballMatch.getGuestTeamNo().trim());
             pstmt.setString(7,footballMatch.getGuestTeamName().trim());
             pstmt.setString(8,footballMatch.getScore().trim());
             pstmt.setString(9,footballMatch.getHalfScore().trim());
             pstmt.setInt(10, footballMatch.getMasterGoalNum());

             pstmt.setInt(11, footballMatch.getGuestGoalNum());
             pstmt.setInt(12, footballMatch.getStatus());
             pstmt.setTimestamp(13, new Timestamp(footballMatch.getCatchTime().getTime()));

             pstmt.setInt(14, NumberUtils.toInt(footballMatch.getHalfScore().trim().split("-")[0],0));
             pstmt.setInt(15, NumberUtils.toInt(footballMatch.getHalfScore().trim().split("-")[1],0));

             pstmt.setString(16,footballMatch.getMatchDate().trim());

			 num=pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException quiet) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException quiet) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException quiet) {
				}
			}
		}
		 
	}


    public void insertFootballGoal(FootballGoal footballGoal){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int num=0;
        try {
            conn = DBConnection.getInstance().getConnection();
            pstmt = conn
                    .prepareStatement("insert into football_goal(" +
                            "match_no,goal_time,goal_name,master"+
                            ") values(?,?,?,?)");

            LOG.info(pstmt);

            pstmt.setString(1,footballGoal.getMatchNo().trim());
            pstmt.setInt(2,footballGoal.getGoalTime());
            pstmt.setString(3,footballGoal.getGoalName().trim());
            pstmt.setInt(4,footballGoal.getMaster());

            num=pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException quiet) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException quiet) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException quiet) {
                }
            }
        }

    }

}
