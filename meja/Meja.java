package Meja;

// class meyimpan informasi meja
public class Meja {
    private int nomor;
    private KetersediaanMeja ketersediaan;
    private KebersihanMeja kebersihan;

    // constructor
    public Meja(int nomor) {
        this.nomor = nomor;
        this.ketersediaan = KetersediaanMeja.TERSEDIA; // default
        this.kebersihan = KebersihanMeja.BERSIH;       // default
    }

    // getter dan setter
    public int getNomorMeja() { return nomor; }
    public KetersediaanMeja getKetersediaan() { return ketersediaan; }
    public KebersihanMeja getKebersihan() { return kebersihan; }
    public void setKetersediaan(KetersediaanMeja statusBaru) { this.ketersediaan = statusBaru; }
    public void setKebersihan (KebersihanMeja statusBaru) { this.kebersihan = statusBaru; }

    // method menampilkan info meja
    @Override
    public String toString() {
        return "Meja " + nomor + " [" +
                ketersediaan + ", " +
                kebersihan + "]";
    }
}
