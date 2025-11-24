package Transaksi;

import java.util.Scanner;

// class untuk metode pembayaran card
public class CardPayment implements Pembayaran {
    private double jumlahBayar;

    // constructor
    public CardPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    // getter implementasi dari interface
    @Override
    public double getJumlahBayar() {return jumlahBayar;}

    // method proses pembayaran implementasi dari interface
    @Override
    @SuppressWarnings("resource") // nonactive scanner warning
    public void prosesPembayaran() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Pembayaran Card ===");
        System.out.printf("Total: Rp %.0f\n", jumlahBayar);
        System.out.print("Masukkan nomor kartu: ");
        String noKartu = scanner.nextLine();
        System.out.println("Memproses kartu " + noKartu + "...");
        System.out.println("Pembayaran berhasil!");
    }
}
