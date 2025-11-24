package models.pesanan;

import models.menu.MenuItem;

// class untuk menyimpan subtotal pesanan
public class Subtotal {
    private MenuItem item;
    private Jumlah jumlah;

    // constructor
    public Subtotal(MenuItem item, Jumlah jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    // getter
    public double getValue() { return item.getHarga() * jumlah.getValue(); }
}
