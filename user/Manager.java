package User;

// class data manager
public class Manager {
    private String username;
    private String password;

    // constructor
    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getter and setter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
