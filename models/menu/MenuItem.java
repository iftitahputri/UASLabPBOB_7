package models.menu;

// abstract class MenuItem
public abstract class MenuItem {
    private String id;
    private String nama;
    private double harga;
    private String tipe;
    private boolean tersedia;
    
    // constructor
    public MenuItem(String id, String nama, double harga, String tipe, boolean tersedia) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
        this.tersedia = tersedia;
    }
    
    // getter
    public String getId() { return id; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getTipe() { return tipe; }
    public boolean isTersedia() { return tersedia; }
    
    // asbtract
    public abstract String getInfo();
}
