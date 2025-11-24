package models.transaksi;

import java.util.Scanner;

// class untuk metode pembayaran cash
public class CashPayment implements Pembayaran {
    private double jumlahBayar;
    private String status = "Pending";

    // constructor
    public CashPayment(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
        this.status = "Pending";
    }

    // getter implementasi dari interface
    @Override
    public double getJumlahBayar() { return jumlahBayar; }

    // method proses pembayaran implementasi dari interface    
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
            System.out.println("✅ Pembayaran berhasil!");
            this.status = "Success";
            return true;  // Return true kalau berhasil
        } else {
            System.out.println("Uang tidak cukup. Pembayaran gagal!");
            this.status = "Failed";
            return false; // Return false kalau gagal
        }
    }
}
