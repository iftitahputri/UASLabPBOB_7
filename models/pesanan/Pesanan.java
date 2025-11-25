package models.pesanan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.meja.Meja;

/**
 * Class yang merepresentasikan pesanan dalam sistem restoran.
 * Setiap pesanan terkait dengan meja tertentu dan memiliki daftar detail pesanan.
 * ID pesanan digenerate secara otomatis dan increment.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see DetailPesanan
 * @see StatusPesanan
 * @see Meja
 */
public class Pesanan {
    // counter untuk ID pesanan
    private static final AtomicInteger counter = new AtomicInteger(1);

    private int idPesanan;
    private StatusPesanan status; 
    private List<DetailPesanan> detailPesanan;
    private Meja meja;

    /**
     * Constructor untuk membuat pesanan baru.
     * 
     * @param meja meja yang membuat pesanan
     */
    public Pesanan(Meja meja) {
        this.idPesanan = counter.getAndIncrement();
        this.meja = meja;
        this.status = StatusPesanan.DIPESAN;
        this.detailPesanan = new ArrayList<>();
    }

    /**
     * Menambahkan detail pesanan ke dalam pesanan.
     * 
     * @param detail detail pesanan yang akan ditambahkan
     */
    public void tambahDetailPesanan(DetailPesanan detail) {
        this.detailPesanan.add(detail);
    }

    /**
     * Menghitung total harga pesanan.
     * 
     * @return total harga dari semua item dalam pesanan
     */
    public double hitungTotal() {
        double total = 0;
        for (DetailPesanan detail : detailPesanan) {
            total += detail.getSubtotal().getValue();
        }
        return total;
    }

    /**
     * Mendapatkan ID pesanan.
     * 
     * @return ID pesanan
     */
    public int getIdPesanan() { return idPesanan; }
    
    /**
     * Mendapatkan status pesanan.
     * 
     * @return status pesanan
     */
    public StatusPesanan getStatus() { return status; }
    
    /**
     * Mendapatkan meja yang terkait dengan pesanan.
     * 
     * @return objek Meja
     */
    public Meja getMeja() { return meja; }
    
    /**
     * Mendapatkan daftar detail pesanan.
     * 
     * @return list berisi detail pesanan
     */
    public List<DetailPesanan> getDetailPesanan() { return detailPesanan; }
    
    /**
     * Mengubah status pesanan.
     * 
     * @param status status baru untuk pesanan
     */
    public void setStatus(StatusPesanan status) { this.status = status; }

    /**
     * Menampilkan informasi lengkap pesanan dalam format string.
     * 
     * @return string berisi ID pesanan, meja, status, detail item, dan total harga
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID Pesanan: ").append(idPesanan)
          .append(" | Meja: ").append(meja.getNomorMeja())
          .append(" | Status: ").append(status).append("\n");

        for (DetailPesanan detail : detailPesanan) {
            sb.append("   - ").append(detail).append("\n");
        }

        sb.append("Total: Rp ").append(hitungTotal());
        return sb.toString();
    }
}