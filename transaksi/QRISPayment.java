package Transaksi;

import java.util.Scanner;

public class QRISPayment implements Pembayaran {
    private double jumlahBayar;

    public QRISPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    @Override
    public double getJumlahBayar() {
        return jumlahBayar;
    }

    @Override
    public void prosesPembayaran() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Pembayaran QRIS ===");
        System.out.printf("Total: Rp %.0f\n", jumlahBayar);
        System.out.print("Scan QR Code (tekan enter setelah scan)...");
        scanner.nextLine();
        System.out.println("Pembayaran berhasil via QRIS!");
    }
}
