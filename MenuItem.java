package PROJECT UAS;

public abstract class MenuItem {
    private String nama;
    private double harga;

    public MenuItem(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    // Encapsulation: Getter
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    /**
     * Abstract method: 
     * Harus di-implementasikan oleh kelas anak (Makanan, Minuman).
     * Ini adalah contoh Polymorphism (Override).
     */
    public abstract String getInfo();
}
