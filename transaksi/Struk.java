package transaksi;
import java.time.LocalDateTime;

public class Struk {
    private String idStruk;
    private String idPembayaran;
    private double amount;
    private LocalDateTime tanggalTransaksi;
    private String detailPembayaran;

    public Struk(String idStruk, String idPembayaran, double amount, LocalDateTime tanggalTransaksi, String detailPembayaran) {
        this.idStruk = idStruk;
        this.idPembayaran = idPembayaran;
        this.amount = amount;
        this.tanggalTransaksi = tanggalTransaksi;
        this.detailPembayaran = detailPembayaran;
    }

    public String getIdStruk() {
        return idStruk;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public String getDetailPembayaran() {
        return detailPembayaran;
    }

    @Override
    public String toString() {
        return String.format(
            "Struk Transaksi\n" +
            "ID Struk: %s\n" +
            "ID Pembayaran: %s\n" +
            "Jumlah: Rp%,.2f\n" +
            "Tanggal: %s\n" +
            "Detail: %s",
            idStruk, idPembayaran, amount, tanggalTransaksi, detailPembayaran
        );
    }
}
