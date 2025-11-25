package models.pesanan;

import models.menu.MenuItem;

/**
 * Value object untuk menghitung dan menyimpan subtotal pesanan.
 * Subtotal dihitung berdasarkan harga item dan jumlah pesanan.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see DetailPesanan
 * @see MenuItem
 * @see Jumlah
 */
public class Subtotal {
    private MenuItem item;
    private Jumlah jumlah;

    /**
     * Constructor untuk Subtotal.
     * 
     * @param item item menu yang dipesan
     * @param jumlah jumlah item yang dipesan
     */
    public Subtotal(MenuItem item, Jumlah jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    /**
     * Menghitung dan mendapatkan nilai subtotal.
     * 
     * @return subtotal yang dihitung dari harga item dikali jumlah
     */
    public double getValue() { return item.getHarga() * jumlah.getValue(); }
}