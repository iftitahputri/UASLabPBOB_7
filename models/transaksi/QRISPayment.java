package models.transaksi;

/**
 * Implementasi pembayaran menggunakan QRIS (Quick Response Code Indonesian Standard).
 * Mengasumsikan pembayaran QRIS selalu berhasil untuk simulasi.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pembayaran
 * @see CashPayment
 * @see CardPayment
 */
public class QRISPayment implements Pembayaran {
    private double jumlahBayar;

    /**
     * Constructor untuk QRISPayment.
     * 
     * @param jumlahBayar jumlah yang harus dibayar
     */
    public QRISPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    /**
     * Mendapatkan jumlah yang harus dibayar.
     * 
     * @return jumlah pembayaran
     */
    @Override
    public double getJumlahBayar() { return jumlahBayar; }

    /**
     * Memproses pembayaran dengan QRIS.
     * Untuk simulasi, pembayaran QRIS diasumsikan selalu berhasil.
     * 
     * @return true (pembayaran berhasil)
     */
    @Override
    public boolean prosesPembayaran() {
        System.out.println("Pembayaran QRIS diproses...");
        return true; // pembayaran via QRIS diasumsikan selalu berhasil
    }
}