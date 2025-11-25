package models.menu;

/**
 * Abstract class dasar untuk semua item menu di restoran.
 * Menyediakan properti umum yang dimiliki oleh semua menu seperti ID, nama, harga, dan ketersediaan.
 * Class ini harus di-extend oleh class spesifik seperti Makanan dan Minuman.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Makanan
 * @see Minuman
 */
public abstract class MenuItem {
    private String id;
    private String nama;
    private double harga;
    private String tipe;
    private boolean tersedia;
    
    /**
     * Constructor untuk MenuItem.
     * 
     * @param id ID unik untuk menu
     * @param nama nama menu
     * @param harga harga menu
     * @param tipe tipe menu (makanan/minuman)
     * @param tersedia status ketersediaan menu
     */
    public MenuItem(String id, String nama, double harga, String tipe, boolean tersedia) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
        this.tersedia = tersedia;
    }
    
    /**
     * Mendapatkan ID menu.
     * 
     * @return ID menu
     */
    public String getId() { return id; }
    
    /**
     * Mendapatkan nama menu.
     * 
     * @return nama menu
     */
    public String getNama() { return nama; }
    
    /**
     * Mendapatkan harga menu.
     * 
     * @return harga menu
     */
    public double getHarga() { return harga; }
    
    /**
     * Mendapatkan tipe menu.
     * 
     * @return tipe menu
     */
    public String getTipe() { return tipe; }
    
    /**
     * Mengecek status ketersediaan menu.
     * 
     * @return true jika menu tersedia, false jika tidak
     */
    public boolean isTersedia() { return tersedia; }
    
    /**
     * Abstract method untuk mendapatkan informasi spesifik menu.
     * Harus diimplementasikan oleh subclass.
     * 
     * @return informasi detail menu
     */
    public abstract String getInfo();
}