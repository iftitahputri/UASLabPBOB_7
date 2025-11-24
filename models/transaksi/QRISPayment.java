package models.transaksi;

import java.util.Scanner;

// class untuk metode pembayaran QRIS
public class QRISPayment implements Pembayaran {
    private double jumlahBayar;

    // constructor
    public QRISPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    // getter implementasi dari interface
    @Override
    public double getJumlahBayar() { return jumlahBayar; }

    // method proses pembayaran implementasi dari interface
    @Override
    @SuppressWarnings("resource") // nonactive scanner warning
    public boolean prosesPembayaran() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Pembayaran QRIS ===");
        System.out.printf("Total: Rp %.0f\n", jumlahBayar);
        System.out.print("Scan QR Code (tekan enter setelah scan)...");
        scanner.nextLine();
        System.out.println("Pembayaran berhasil via QRIS!");

        return true; // pembayaran via QRIS diasumsikan selalu berhasil
    }
}
