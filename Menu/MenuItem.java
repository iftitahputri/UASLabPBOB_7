package Makanan;

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

   
    public abstract String getInfo();
}
