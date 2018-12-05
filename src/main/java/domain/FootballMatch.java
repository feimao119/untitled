package domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chenzx
 * Date: 13-11-12
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class FootballMatch {
    private int id;
    private String matchNo;

    private String matchName;
    private String matchTime;

    private String masterTeamNo;
    private String masterTeamName;
    private String guestTeamNo;
    private String guestTeamName;

    private String score;
    private String halfScore;

    private int masterGoalNum;

    private int guestGoalNum;

    private int status;

    private Date catchTime;

    private String matchDate;

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMasterTeamNo() {
        return masterTeamNo;
    }

    public void setMasterTeamNo(String masterTeamNo) {
        this.masterTeamNo = masterTeamNo;
    }

    public String getMasterTeamName() {
        return masterTeamName;
    }

    public void setMasterTeamName(String masterTeamName) {
        this.masterTeamName = masterTeamName;
    }

    public String getGuestTeamNo() {
        return guestTeamNo;
    }

    public void setGuestTeamNo(String guestTeamNo) {
        this.guestTeamNo = guestTeamNo;
    }

    public String getGuestTeamName() {
        return guestTeamName;
    }

    public void setGuestTeamName(String guestTeamName) {
        this.guestTeamName = guestTeamName;
    }

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
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "FootballMatch{" +
                "id=" + id +
                ", matchNo='" + matchNo + '\'' +
                ", matchName='" + matchName + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", masterTeamNo='" + masterTeamNo + '\'' +
                ", masterTeamName='" + masterTeamName + '\'' +
                ", guestTeamNo='" + guestTeamNo + '\'' +
                ", guestTeamName='" + guestTeamName + '\'' +
                ", score='" + score + '\'' +
                ", halfScore='" + halfScore + '\'' +
                ", masterGoalNum=" + masterGoalNum +
                ", guestGoalNum=" + guestGoalNum +
                ", status=" + status +
                ", catchTime=" + catchTime +
                '}';
    }

}
