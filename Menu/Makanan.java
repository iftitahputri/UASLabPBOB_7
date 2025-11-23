package Menu;

public class Makanan extends MenuItem {
    private String tingkatPedas;
    private String kategori;

    public Makanan(String id, String nama, double harga, String tipe, boolean tersedia, String tingkatPedas, String kategori) {
        super(id, nama, harga, tipe, tersedia);
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    public String getTingkatPedas() { return tingkatPedas; }
    public String getKategoriMakan() { return kategori; }

    @Override
    public String getInfo() {
        return String.format("%s (Pedas: %s, Kategori: %s) - Rp %.0f",
                getNama(), tingkatPedas, kategori, getHarga());
    }
}
