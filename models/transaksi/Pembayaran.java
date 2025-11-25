package models.transaksi;

/**
 * Interface untuk semua metode pembayaran dalam sistem restoran.
 * Setiap class yang mengimplementasi interface ini harus menyediakan
 * implementasi untuk proses pembayaran dan mendapatkan jumlah bayar.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see CashPayment
 * @see CardPayment
 * @see QRISPayment
 */
public interface Pembayaran {
    
    /**
     * Memproses pembayaran berdasarkan metode yang diimplementasi.
     * 
     * @return true jika pembayaran berhasil, false jika gagal
     */
    boolean prosesPembayaran();
    
    /**
     * Mendapatkan jumlah yang harus dibayar.
     * 
     * @return jumlah pembayaran
     */
    double getJumlahBayar();
}