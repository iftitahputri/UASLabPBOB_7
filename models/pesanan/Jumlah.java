package models.pesanan;

/**
 * Value object untuk menyimpan jumlah pesanan dengan validasi.
 * Memastikan jumlah pesanan selalu valid (minimal 1).
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see DetailPesanan
 * @see Pesanan
 */
public class Jumlah {
    private int value;

    /**
     * Constructor untuk Jumlah.
     * 
     * @param value nilai jumlah pesanan
     * @throws IllegalArgumentException jika nilai jumlah kurang dari 1
     */
    public Jumlah(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("Jumlah harus >= 1");
        }
        this.value = value;
    }

    /**
     * Mendapatkan nilai jumlah.
     * 
     * @return nilai jumlah pesanan
     */
    public int getValue() {return value;}
}