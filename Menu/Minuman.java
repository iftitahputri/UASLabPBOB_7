package Makanan;


public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu; // Panas/Dingin

    public Minuman(String nama, double harga, String ukuran, String suhu) {
        super(nama, harga); // Memanggil constructor induk (MenuItem)
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    // Getter spesifik untuk Minuman
    public String getUkuran() {
        return ukuran;
    }

    public String getSuhu() {
        return suhu;
    }

    /**
     * Implementasi (Override) dari abstract method getInfo().
     * Ini adalah contoh Polymorphism.
     */
    @Override
    public String getInfo() {
        // Format string untuk menampilkan info
        return String.format("%s (Ukuran: %s, Suhu: %s) - Rp %.0f",
                getNama(), ukuran, suhu, getHarga());
    }
}