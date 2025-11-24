package models.menu;

// class menyimpan data makanan dari menu item
public class Makanan extends MenuItem {
    private String tingkatPedas;
    private String kategori;

    // constructor
    public Makanan(String id, String nama, double harga, String tipe, boolean tersedia, String tingkatPedas, String kategori) {
        super(id, nama, harga, tipe, tersedia); // superclass constructor
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    // getter
    public String getTingkatPedas() { return tingkatPedas; }
    public String getKategoriMakan() { return kategori; }

    // method menampilkan info makanan
    @Override
    public String getInfo() {
        return String.format("%s (Pedas: %s, Kategori: %s) - Rp %.0f",
                getNama(), tingkatPedas, kategori, getHarga());
    }
}
