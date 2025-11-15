package DetailPesanan;

public class Subtotal {
    private MenuItem item;
    private Jumlah jumlah;

    public Subtotal(MenuItem item, Jumlah jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    public double getValue() {
        return item.getHarga() * jumlah.getValue();
    }
}
