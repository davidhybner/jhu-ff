package jhu.ff.models;

import java.util.HashMap;
import java.util.Map;

public class League {
    private int id;
    private String name;
    private Map<User, Roster> playerRosters = new HashMap<User, Roster>();
    private String owner;

    public League(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public League(int id, String name, String owner) {
        this(id, name);
        this.owner = owner;
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

    public Map<User, Roster> getPlayerRosters() {
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

    public void addPlayerRoster(User user, Roster roster) {
        playerRosters.put(user, roster);
    }
}
