package Transaksi;

public interface Pembayaran {
    void prosesPembayaran(); // semua tipe pembayaran wajib implementasi
    double getJumlahBayar();
}
