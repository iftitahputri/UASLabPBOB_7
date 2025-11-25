package models.transaksi;

/**
 * Implementasi pembayaran menggunakan kartu (debit/kredit).
 * Mengasumsikan pembayaran kartu selalu berhasil untuk simulasi.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pembayaran
 * @see CashPayment
 * @see QRISPayment
 */
public class CardPayment implements Pembayaran {
    private double jumlahBayar;

    /**
     * Constructor untuk CardPayment.
     * 
     * @param jumlahBayar jumlah yang harus dibayar
     */
    public CardPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    /**
     * Mendapatkan jumlah yang harus dibayar.
     * 
     * @return jumlah pembayaran
     */
    @Override
    public double getJumlahBayar() {return jumlahBayar;}

    /**
     * Memproses pembayaran dengan kartu.
     * Untuk simulasi, pembayaran kartu diasumsikan selalu berhasil.
     * 
     * @return true (pembayaran berhasil)
     */
    @Override
    public boolean prosesPembayaran() {
        System.out.println("Pembayaran Card diproses...");
        return true; // pembayaran via card diasumsikan selalu berhasil
    }
}