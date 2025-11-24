package models.pesanan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.meja.Meja;
 
// class untuk menyimpan pesanan
public class Pesanan {
    // counter untuk ID pesanan
    private static final AtomicInteger counter = new AtomicInteger(1);

    private int idPesanan;
    private StatusPesanan status; 
    private List<DetailPesanan> detailPesanan;
    private Meja meja;

    // constructor
    public Pesanan(Meja meja) {
        this.idPesanan = counter.getAndIncrement();
        this.meja = meja;
        this.status = StatusPesanan.DIPESAN;
        this.detailPesanan = new ArrayList<>();
    }

    // method tambah detail pesanan
    public void tambahDetailPesanan(DetailPesanan detail) {
        this.detailPesanan.add(detail);
    }

    // metod hitung total pesanan
    public double hitungTotal() {
        double total = 0;
        for (DetailPesanan detail : detailPesanan) {
            total += detail.getSubtotal().getValue();
        }
        return total;
    }

    // getter dan setter
    public int getIdPesanan() { return idPesanan; }
    public StatusPesanan getStatus() { return status; }
    public Meja getMeja() { return meja; }
    public List<DetailPesanan> getDetailPesanan() { return detailPesanan; }
    public void setStatus(StatusPesanan status) { this.status = status; }


    // method untuk menampilkan pesanan
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
