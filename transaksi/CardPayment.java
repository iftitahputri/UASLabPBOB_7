package Transaksi;

import java.util.Scanner;

public class CardPayment implements Pembayaran {
    private double jumlahBayar;

    public CardPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    @Override
    public double getJumlahBayar() {
        return jumlahBayar;
    }

    @Override
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
