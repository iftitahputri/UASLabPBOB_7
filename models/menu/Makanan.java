package models.menu;

/**
 * Class yang merepresentasikan makanan dalam menu restoran.
 * Extends MenuItem dan menambahkan atribut spesifik untuk makanan seperti tingkat pedas dan kategori.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see MenuItem
 * @see Minuman
 */
public class Makanan extends MenuItem {
    private String tingkatPedas;
    private String kategori;

    /**
     * Constructor untuk Makanan.
     * 
     * @param id ID unik makanan
     * @param nama nama makanan
     * @param harga harga makanan
     * @param tipe tipe makanan (harus "makanan")
     * @param tersedia status ketersediaan makanan
     * @param tingkatPedas tingkat kepedasan makanan (Tidak Pedas, Sedang, Pedas)
     * @param kategori kategori makanan (Appetizer, Main Course, Dessert, dll)
     */
    public Makanan(String id, String nama, double harga, String tipe, boolean tersedia, String tingkatPedas, String kategori) {
        super(id, nama, harga, tipe, tersedia); // superclass constructor
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    /**
     * Mendapatkan tingkat kepedasan makanan.
     * 
     * @return tingkat kepedasan makanan
     */
    public String getTingkatPedas() { return tingkatPedas; }
    
    /**
     * Mendapatkan kategori makanan.
     * 
     * @return kategori makanan
     */
    public String getKategoriMakan() { return kategori; }

    /**
     * Menampilkan informasi lengkap makanan.
     * 
     * @return string berisi informasi makanan termasuk nama, tingkat pedas, kategori, dan harga
     */
    @Override
    public String getInfo() {
        return String.format("%s (Pedas: %s, Kategori: %s) - Rp %.0f",
                getNama(), tingkatPedas, kategori, getHarga());
    }
}