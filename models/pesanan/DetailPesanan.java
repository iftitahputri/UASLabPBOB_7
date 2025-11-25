package models.pesanan;

import models.menu.MenuItem;

/**
 * Class yang merepresentasikan detail dari sebuah pesanan.
 * Setiap detail pesanan berisi item menu, jumlah, catatan khusus, dan subtotal.
 * Menggunakan value objects untuk jumlah, catatan, dan subtotal.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pesanan
 * @see MenuItem
 * @see Jumlah
 * @see Catatan
 * @see Subtotal
 */
public class DetailPesanan { 
    private MenuItem item;      
    private Jumlah jumlah;
    private Catatan catatan;
    private Subtotal subtotal;

    /**
     * Constructor untuk membuat detail pesanan.
     * 
     * @param item item menu yang dipesan
     * @param jumlah jumlah item yang dipesan
     * @param catatan catatan khusus untuk item (bisa kosong)
     */
    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = new Jumlah(jumlah);
        this.catatan = new Catatan(catatan);
        this.subtotal = new Subtotal(item, this.jumlah);
    }

    /**
     * Mendapatkan item menu.
     * 
     * @return objek MenuItem
     */
    public MenuItem getItem() { return item; }
    
    /**
     * Mendapatkan objek jumlah.
     * 
     * @return objek Jumlah
     */
    public Jumlah getJumlah() { return jumlah; }
    
    /**
     * Mendapatkan nilai jumlah sebagai integer.
     * 
     * @return nilai jumlah
     */
    public int getJumlahValue() { return jumlah.getValue(); }
    
    /**
     * Mendapatkan catatan pesanan.
     * 
     * @return objek Catatan
     */
    public Catatan getCatatan() { return catatan; }
    
    /**
     * Mendapatkan subtotal untuk item ini.
     * 
     * @return objek Subtotal
     */
    public Subtotal getSubtotal() { return subtotal; }

    /**
     * Menampilkan informasi detail pesanan dalam format string.
     * 
     * @return string berisi nama item, jumlah, dan catatan (jika ada)
     */
    @Override
    public String toString() {
        if (catatan.isEmpty()) {
            return item.getNama() + " x " + jumlah.getValue();
        } else {
            return item.getNama() + " x " + jumlah.getValue() + " (" + catatan.getKeterangan() + ")";
        }
    }
}