package Transaksi;

import DetailPesanan.Pesanan;
import DetailPesanan.DetailPesanan;

// class untuk struk pembayaran
public class Struk {
    private Pesanan pesanan;
    private Pembayaran pembayaran;

    // constructor
    public Struk(Pesanan pesanan, Pembayaran pembayaran) {
        this.pesanan = pesanan;
        this.pembayaran = pembayaran;
    }

    // method mencetak struk pembayaran
    public void cetak() {
        System.out.println("=== STRUK PEMBAYARAN ===");
        // info pesanan
        System.out.println("ID Pesanan: " + pesanan.getIdPesanan());
        System.out.println("Meja: " + pesanan.getMeja().getNomorMeja());
        System.out.println("Status Pesanan: " + pesanan.getStatus());
        System.out.println("\nDaftar Pesanan:");

        // detail pesanan (jumlah dan subtotal)
        for (DetailPesanan detail : pesanan.getDetailPesanan()) {
            System.out.printf(" - %s x %d | Subtotal: Rp %.0f\n",
                    detail.getItem().getNama(),
                    detail.getJumlah().getValue(),
                    detail.getSubtotal().getValue());
        }

        // total dan info pembayaran
        System.out.println("\nTotal: Rp " + pesanan.hitungTotal());
        System.out.println("Metode Pembayaran: " + pembayaran.getClass().getSimpleName());
        System.out.printf("Jumlah Dibayar: Rp %.0f\n", pembayaran.getJumlahBayar());
        System.out.println("=========================");
    }
}
