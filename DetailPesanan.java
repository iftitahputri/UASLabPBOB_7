package PROJECT UAS;

public class DetailPesanan {
    private MenuItem item; // Relasi Asosiasi/Komposisi ke MenuItem
    private int jumlah;
    private String catatan;

    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = jumlah;
        this.catatan = catatan;
    }

    // Getter
    public MenuItem getItem() {
        return item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getCatatan() {
        return catatan;
    }

    // Metode untuk menghitung subtotal per item
    public double getSubtotal() {
        return item.getHarga() * jumlah;
    }
}
