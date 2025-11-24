package Transaksi;

// interface untuk metode pembayaran
public interface Pembayaran {
    void prosesPembayaran(); // semua tipe pembayaran wajib implementasi
    double getJumlahBayar();
}
