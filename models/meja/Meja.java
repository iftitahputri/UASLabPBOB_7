package models.meja;

/**
 * Merepresentasikan meja di restoran dengan informasi nomor, ketersediaan, dan kebersihan.
 * Setiap meja memiliki status ketersediaan (TERSEDIA/DIPESAN) dan kebersihan (BERSIH/KOTOR).
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see KetersediaanMeja
 * @see KebersihanMeja
 */
public class Meja {
    private int nomor;
    private KetersediaanMeja ketersediaan;
    private KebersihanMeja kebersihan;

    /**
     * Constructor untuk membuat objek Meja baru.
     * 
     * @param nomor nomor meja (1-15)
     */
    public Meja(int nomor) {
        this.nomor = nomor;
        this.ketersediaan = KetersediaanMeja.TERSEDIA; // default
        this.kebersihan = KebersihanMeja.BERSIH;       // default
    }

    /**
     * Mendapatkan nomor meja.
     * 
     * @return nomor meja
     */
    public int getNomorMeja() { return nomor; }
    
    /**
     * Mendapatkan status ketersediaan meja.
     * 
     * @return status ketersediaan meja
     */
    public KetersediaanMeja getKetersediaan() { return ketersediaan; }
    
    /**
     * Mendapatkan status kebersihan meja.
     * 
     * @return status kebersihan meja
     */
    public KebersihanMeja getKebersihan() { return kebersihan; }
    
    /**
     * Mengubah status ketersediaan meja.
     * 
     * @param statusBaru status ketersediaan baru
     */
    public void setKetersediaan(KetersediaanMeja statusBaru) { this.ketersediaan = statusBaru; }
    
    /**
     * Mengubah status kebersihan meja.
     * 
     * @param statusBaru status kebersihan baru
     */
    public void setKebersihan (KebersihanMeja statusBaru) { this.kebersihan = statusBaru; }

    /**
     * Menampilkan informasi meja dalam format string.
     * 
     * @return string berisi informasi lengkap meja
     */
    @Override
    public String toString() {
        return "Meja " + nomor + " [" +
                ketersediaan + ", " +
                kebersihan + "]";
    }
}