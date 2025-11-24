package models.transaksi;

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
    public boolean prosesPembayaran() {
        System.out.println("Pembayaran Card diproses...");
        return true; // pembayaran via card diasumsikan selalu berhasil
    }
}
