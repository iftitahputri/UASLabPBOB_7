package Meja;

public class Meja {
    private int nomor;
    private KetersediaanMeja ketersediaan;
    private KebersihanMeja kebersihan;

    public Meja(int nomor) {
        this.nomor = nomor;
        this.ketersediaan = KetersediaanMeja.TERSEDIA; // default
        this.kebersihan = KebersihanMeja.BERSIH;       // default
    }

    public int getNomorMeja() {
        return nomor;
    }

    public KetersediaanMeja getKetersediaan() {
        return ketersediaan;
    }

    public KebersihanMeja getKebersihan() {
        return kebersihan;
    }

    public void setKetersediaan(KetersediaanMeja statusBaru) {
        this.ketersediaan = statusBaru;
    }

    public void kebersihan (KebersihanMeja statusBaru) {
        this.kebersihan = statusBaru;
    }


    @Override
    public String toString() {
        return "Meja " + nomor + " [" +
                ketersediaan + ", " +
                kebersihan + "]";
    }
}
