package models.pesanan;

/**
 * Value object untuk menyimpan catatan tambahan pada pesanan.
 * Menangani catatan khusus untuk item menu seperti permintaan khusus atau modifikasi.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see DetailPesanan
 * @see Pesanan
 */
public class Catatan {
    private String keterangan;

    /**
     * Constructor untuk Catatan.
     * 
     * @param keterangan teks catatan (bisa null atau kosong)
     */
    public Catatan(String keterangan) {
        this.keterangan = (keterangan == null) ? "" : keterangan;
    }

    /**
     * Mengecek apakah catatan kosong.
     * 
     * @return true jika catatan kosong, false jika berisi teks
     */
    public boolean isEmpty() { return keterangan.isEmpty(); }

    /**
     * Mendapatkan teks catatan.
     * 
     * @return teks catatan (string kosong jika tidak ada catatan)
     */
    public String getKeterangan() { return keterangan; }
}