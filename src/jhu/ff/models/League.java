package jhu.ff.models;

import java.util.HashMap;
import java.util.Map;

public class League {
    private int id;
    private String name;
    private Map<User, Team> playerRosters = new HashMap<User, Team>();
    private String owner;
    private String publicID;

    public League(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public League(int id, String name, String owner, String publicID) {
        this(id, name);
        this.owner = owner;
        this.publicID = publicID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<User, Team> getPlayerRosters() {
        return playerRosters;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playerRosters=" + playerRosters +
                ", owner='" + owner + '\'' +
                '}';
    }

    public void addPlayerRoster(User user, Team roster) {
        playerRosters.put(user, roster);
    }

    public String getPublicID() {
        return publicID;
    }

    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }
}
