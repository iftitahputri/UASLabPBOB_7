package models.pekerja;

/**
 * Class yang merepresentasikan data manager/administrator sistem.
 * Manager memiliki akses khusus untuk mengelola sistem restoran.
 * 
 * @author Kelompok_7
 * @version 1.0
 */
public class Manager {
    private String username;
    private String password;

    /**
     * Constructor untuk membuat objek Manager.
     * 
     * @param username username untuk login manager
     * @param password password untuk login manager
     */
    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Mendapatkan username manager.
     * 
     * @return username manager
     */
    public String getUsername() { return username; }

    /**
     * Mendapatkan password manager.
     * 
     * @return password manager
     */
    public String getPassword() { return password; }

    /**
     * Mengatur username manager.
     * 
     * @param username username baru untuk manager
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Mengatur password manager.
     * 
     * @param password password baru untuk manager
     */
    public void setPassword(String password) { this.password = password; }
}