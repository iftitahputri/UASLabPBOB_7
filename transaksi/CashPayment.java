package Transaksi;

import java.util.Scanner;

public class CashPayment implements Pembayaran {
    private double jumlahBayar;

    public CashPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    @Override
    public double getJumlahBayar() {
        return jumlahBayar;
    }

    @Override
    public void prosesPembayaran() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Pembayaran Cash ===");
        System.out.printf("Total: Rp %.0f\n", jumlahBayar);
        System.out.print("Masukkan uang tunai: ");
        double uang = scanner.nextDouble();
        if (uang >= jumlahBayar) {
            System.out.printf("Kembalian: Rp %.0f\n", uang - jumlahBayar);
            System.out.println("Pembayaran berhasil!");
        } else {
            System.out.println("Uang tidak cukup. Pembayaran gagal!");
        }
    }
}
