package models.pesanan;

import models.menu.MenuItem;
 
// class untuk menyimpan detail pesanan
public class DetailPesanan { 
    private MenuItem item;      
    private Jumlah jumlah;
    private Catatan catatan;
    private Subtotal subtotal;

    // constructor
    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = new Jumlah(jumlah);
        this.catatan = new Catatan(catatan);
        this.subtotal = new Subtotal(item, this.jumlah);
    }

    // getter
    public MenuItem getItem() { return item; }
    public Jumlah getJumlah() { return jumlah; }
    public int getJumlahValue() { return jumlah.getValue(); }
    public Catatan getCatatan() { return catatan; }
    public Subtotal getSubtotal() { return subtotal; }

    // method untuk menampilkan detail pesanan
    @Override
    public String toString() {
        if (catatan.isEmpty()) {
            return item.getNama() + " x " + jumlah.getValue();
        } else {
            return item.getNama() + " x " + jumlah.getValue() + " (" + catatan.getKeterangan() + ")";
        }
    }
}
