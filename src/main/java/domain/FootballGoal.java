package domain;

/**
 * Created with IntelliJ IDEA.
 * User: chenzx
 * Date: 13-11-12
 * Time: 下午10:29
 * To change this template use File | Settings | File Templates.
 */
public class FootballGoal {
    private int id;
    private String matchNo;

    private int goalTime;
    private String goalName;

    private int master;

    private int isFirst;

    private int isLast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public int getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(int goalTime) {
        this.goalTime = goalTime;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public int getMaster() {
        return master;
    }

    public void setMaster(int master) {
        this.master = master;
    }

    public int getFirst() {
        return isFirst;
    }

    public void setFirst(int first) {
        isFirst = first;
    }

    public int getLast() {
        return isLast;
    }

    public void setLast(int last) {
        isLast = last;
    }

    @Override
    public String toString() {
        return "FootballGoal{" +
                "id=" + id +
                ", matchNo='" + matchNo + '\'' +
                ", goalTime=" + goalTime +
                ", goalName='" + goalName + '\'' +
                ", master=" + master +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                '}';
    }
}
