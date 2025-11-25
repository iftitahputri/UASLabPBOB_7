package models.transaksi;

import models.pesanan.DetailPesanan;
import models.pesanan.Pesanan;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Kelas Struk untuk merepresentasikan struk pembayaran transaksi.
 * Menyimpan informasi detail tentang transaksi yang dilakukan dan
 * menyediakan fungsi untuk mencetak struk dalam format yang rapi.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pesanan
 * @see Pembayaran
 * @see DetailPesanan
 */
public class Struk {
    private Pesanan pesanan;
    private Pembayaran pembayaran;

    /**
     * Constructor untuk membuat objek Struk.
     * 
     * @param pesanan objek Pesanan yang terkait dengan struk
     * @param pembayaran objek Pembayaran yang digunakan
     */
    public Struk(Pesanan pesanan, Pembayaran pembayaran) {
        this.pesanan = pesanan;
        this.pembayaran = pembayaran;
    }

    /**
     * Mendapatkan objek Pesanan dari struk.
     * 
     * @return pesanan yang terkait dengan struk
     */
    public Pesanan getPesanan() {
        return pesanan;
    }

    /**
     * Mendapatkan objek Pembayaran dari struk.
     * 
     * @return metode pembayaran yang digunakan
     */
    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    /**
     * Mencetak struk pembayaran dalam format yang rapi ke konsol.
     * Menampilkan informasi lengkap tentang transaksi termasuk:
     * - Informasi pesanan (ID, meja, status)
     * - Detail item yang dipesan
     * - Total pembayaran dan metode pembayaran
     * - Waktu transaksi
     */
    public void cetak() {
        // Format tanggal dan waktu saat ini
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timestamp = formatter.format(new Date());

        System.out.println("\n==========================================");
        System.out.println("           STRUK PEMBAYARAN");
        System.out.println("==========================================");
        
        // Informasi pesanan
        System.out.println("ID Pesanan    : " + pesanan.getIdPesanan());
        System.out.println("Meja          : " + pesanan.getMeja().getNomorMeja());
        System.out.println("Status        : " + pesanan.getStatus());
        System.out.println("Waktu         : " + timestamp);
        
        System.out.println("\n------------------------------------------");
        System.out.println("             DETAIL PESANAN");
        System.out.println("------------------------------------------");

        // Detail pesanan (jumlah dan subtotal)
        int nomor = 1;
        for (DetailPesanan detail : pesanan.getDetailPesanan()) {
            System.out.printf("%2d. %-20s x %-3d | Rp %10.0f\n",
                    nomor++,
                    detail.getItem().getNama(),
                    detail.getJumlah().getValue(),
                    detail.getSubtotal().getValue());
        }

        // Total dan informasi pembayaran
        System.out.println("------------------------------------------");
        System.out.printf("TOTAL         : Rp %10.0f\n", pesanan.hitungTotal());
        System.out.println("------------------------------------------");
        
        // Informasi pembayaran
        String metodeBayar = pembayaran.getClass().getSimpleName()
                .replace("Payment", "") // Menghilangkan kata "Payment"
                .replace("QRIS", "QRIS") // Tetap QRIS
                .replace("Cash", "Tunai")
                .replace("Card", "Kartu");
                
        System.out.printf("Metode Bayar  : %s\n", metodeBayar);
        System.out.printf("Jumlah Bayar  : Rp %10.0f\n", pembayaran.getJumlahBayar());
        
        System.out.println("==========================================");
        System.out.println("     TERIMA KASIH ATAS KUNJUNGAN ANDA");
        System.out.println("==========================================\n");
    }
}