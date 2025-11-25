package models.transaksi;

import java.util.Scanner;

/**
 * Implementasi pembayaran menggunakan uang tunai (cash).
 * Meminta input uang dari user dan menghitung kembalian.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pembayaran
 * @see CardPayment
 * @see QRISPayment
 */
public class CashPayment implements Pembayaran {
    private double jumlahBayar;

    /**
     * Constructor untuk CashPayment.
     * 
     * @param jumlahBayar jumlah yang harus dibayar
     */
    public CashPayment(double jumlahBayar) {
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
     * Memproses pembayaran dengan uang tunai.
     * Meminta input uang dari user dan memvalidasi kecukupan pembayaran.
     * Menghitung dan menampilkan kembalian jika uang cukup.
     * 
     * @return true jika uang cukup, false jika tidak cukup
     */
    @Override
    @SuppressWarnings("resource") // nonactive scanner warning
    public boolean prosesPembayaran() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Pembayaran Cash ===");
        System.out.printf("Total: Rp %.0f\n", jumlahBayar);
        System.out.print("Masukkan uang tunai: ");
        double uang = scanner.nextDouble();
        
        if (uang >= jumlahBayar) {
            double kembalian = uang - jumlahBayar;
            System.out.printf("Kembalian: Rp %.0f\n", kembalian);
            System.out.println("Pembayaran berhasil!");
            return true;  // return true kalau berhasil
        } else {
            System.out.println("Uang tidak cukup. Pembayaran gagal!");
            return false; // return false kalau gagal
        }
    }
}