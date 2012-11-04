package jhu.ff.models;

public class Roster {
    private String offenseTeam;
    private String defenseTeam;

    public Roster(String offenseTeam, String defenseTeam) {
        this.offenseTeam = offenseTeam;
        this.defenseTeam = defenseTeam;
    }

    public String getOffenseTeam() {
        return offenseTeam;
    }

    public void setOffenseTeam(String offenseTeam) {
        this.offenseTeam = offenseTeam;
    }

    public String getDefenseTeam() {
        return defenseTeam;
    }

    public void setDefenseTeam(String defenseTeam) {
        this.defenseTeam = defenseTeam;
    }

    @Override
    public String toString() {
        return "Roster{" +
                "offenseTeam='" + offenseTeam + '\'' +
                ", defenseTeam='" + defenseTeam + '\'' +
                '}';
    }
}