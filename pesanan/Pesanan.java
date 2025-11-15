package pesanan UAS;

// Diperlukan untuk menggunakan Collection (List/ArrayList)
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger; // Untuk ID otomatis

/
public class Pesanan {
    // ID statis untuk auto-increment (agar tiap pesanan punya ID unik)
    private static final AtomicInteger counter = new AtomicInteger(1);

    private int idPesanan;
    private String status; // Misal: "Dipesan", "Selesai dimasak", "Lunas"
    private List<DetailPesanan> detailPesanan; // Collection (Komposisi)
    private Meja meja; // Asosiasi dengan Meja

   
    public Pesanan(Meja meja) {
        this.idPesanan = counter.getAndIncrement();
        this.meja = meja;
        this.status = "Dipesan"; // Status awal saat dibuat
        this.detailPesanan = new ArrayList<>(); // Inisialisasi Collection
    }

    // Metode untuk menambah item ke dalam pesanan
    public void tambahDetailPesanan(DetailPesanan detail) {
        this.detailPesanan.add(detail);
    }

    // Metode untuk menghitung total harga pesanan
    public double hitungTotal() {
        double total = 0;
        // Loop setiap item di dalam Collection
        for (DetailPesanan detail : detailPesanan) {
            total += detail.getSubtotal();
        }
        return total;
    }

    // --- Getter dan Setter (Encapsulation) ---
    public int getIdPesanan() {
        return idPesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Meja getMeja() {
        return meja;
    }

    public List<DetailPesanan> getDetailPesanan() {
        return detailPesanan;
    }
}