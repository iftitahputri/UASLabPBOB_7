package models.transaksi;

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
    public boolean prosesPembayaran() {
        System.out.println("Pembayaran QRIS diproses...");
        return true; // pembayaran via QRIS diasumsikan selalu berhasil
    }
}
