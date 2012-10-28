package jhu.ff.models;

public enum Roles {
    Player("player"),
    Admin("admin");

    private String roleName;

    private Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
