package user;

import java.time.LocalDateTime;

public abstract class User { 
    protected String id;
    protected String nama;
    protected String email;
    protected String username;
    protected String password;
    protected String peran; 
    protected LocalDateTime createdAt;
    protected boolean isActive;
    
    public User(String id, String nama, String email, String username, String password, String peran) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.peran = peran;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }
    
    public abstract void displayProfile();
    
    
    public void updateProfile(String nama, String username, String email) {
        this.nama = nama;
        this.username = username;   
        this.email = email;
        System.out.println("Profile updated successfully!");
    }
        
    // Getter methods
    public String getId() { 
        return id; 
    }
    public String getNama() { 
        return nama; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getRole() { 
        return peran; 
    }
    public String getUsername() { 
        return username; 
    }
    public boolean isActive() { 
        return isActive; 
    }
    
    // Setter methods
    public void setActive(boolean active) { 
        isActive = active; 
    }
}