package models.menu;

/**
 * Class yang merepresentasikan minuman dalam menu restoran.
 * Extends MenuItem dan menambahkan atribut spesifik untuk minuman seperti ukuran dan suhu.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see MenuItem
 * @see Makanan
 */
public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    /**
     * Constructor untuk Minuman.
     * 
     * @param id ID unik minuman
     * @param nama nama minuman
     * @param harga harga minuman
     * @param tipe tipe minuman (harus "minuman")
     * @param tersedia status ketersediaan minuman
     * @param ukuran ukuran minuman (Small, Medium, Large)
     * @param suhu suhu minuman (Dingin, Panas, Hangat)
     */
    public Minuman(String id, String nama, double harga, String tipe, boolean tersedia, String ukuran, String suhu) {
        super(id, nama, harga, tipe, tersedia); // superclass constructor
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    /**
     * Mendapatkan ukuran minuman.
     * 
     * @return ukuran minuman
     */
    public String getUkuran() { return ukuran; }
    
    /**
     * Mendapatkan suhu minuman.
     * 
     * @return suhu minuman
     */
    public String getSuhu() { return suhu; }

    /**
     * Menampilkan informasi lengkap minuman.
     * 
     * @return string berisi informasi minuman termasuk nama, ukuran, suhu, dan harga
     */
    @Override
    public String getInfo() {
        return String.format("%s (Ukuran: %s, Suhu: %s) - Rp %.0f",
                getNama(), ukuran, suhu, getHarga());
    }
}