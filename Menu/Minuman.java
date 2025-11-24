package Menu;

// class menyimpan data minuman dari menu item
public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    // constructor
    public Minuman(String id, String nama, double harga, String tipe, boolean tersedia, String ukuran, String suhu) {
        super(id, nama, harga, tipe, tersedia); // superclass constructor
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    // getter
    public String getUkuran() { return ukuran; }
    public String getSuhu() { return suhu; }

    // method menampilkan info minuman
    @Override
    public String getInfo() {
        return String.format("%s (Ukuran: %s, Suhu: %s) - Rp %.0f",
                getNama(), ukuran, suhu, getHarga());
    }
}
