package jhu.ff.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<String> roles;

    public User(String username) {
        this.username = username;
        roles = new ArrayList<String>();
    }

    public User(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }

    public void addRole(String role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
