package jhu.ff.models;

public class Team {
    private int offenseTeam;
    private int defenseTeam;
    private int score;

    public Team(int offenseTeam, int defenseTeam, int score) {
        this.offenseTeam = offenseTeam;
        this.defenseTeam = defenseTeam;
        this.score = score;
    }

    public int getOffenseTeam() {
        return offenseTeam;
    }

    public void setOffenseTeam(int offenseTeam) {
        this.offenseTeam = offenseTeam;
    }

    public int getDefenseTeam() {
        return defenseTeam;
    }

    public void setDefenseTeam(int defenseTeam) {
        this.defenseTeam = defenseTeam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Team{" +
                "offenseTeam=" + offenseTeam +
                ", defenseTeam=" + defenseTeam +
                ", score=" + score +
                '}';
    }
}
