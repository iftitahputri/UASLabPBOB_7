package user;

public abstract class User { 
    protected String id;
    protected String nama;
    protected String peran;

    public User(String id, String nama, String peran) {
        this.id = id;
        this.nama = nama;
    }
        
    public String getNama() { 
        return nama; 
    }
    public String getRole() { 
        return peran; 
    }
    
}