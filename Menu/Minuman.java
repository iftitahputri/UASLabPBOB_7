package Menu;

public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    public Minuman(String id, String nama, double harga, String tipe, boolean tersedia, String ukuran, String suhu) {
        super(id, nama, harga, tipe, tersedia);
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    public String getUkuran() { return ukuran; }
    public String getSuhu() { return suhu; }

    @Override
    public String getInfo() {
        return String.format("%s (Ukuran: %s, Suhu: %s) - Rp %.0f",
                getNama(), ukuran, suhu, getHarga());
    }
}
