package models.transaksi;

// interface untuk metode pembayaran
public interface Pembayaran {
    boolean prosesPembayaran(); // semua tipe pembayaran wajib implementasi
    double getJumlahBayar();
}
