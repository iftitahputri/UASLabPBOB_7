package DetailPesanan;

// class untuk menyimpan catatan tambahan
public class Catatan {
    private String keterangan;

    // constructor
    public Catatan(String keterangan) {
        this.keterangan = (keterangan == null) ? "" : keterangan;
    }

    // method untuk mengecek apakah catatan kosong
    public boolean isEmpty() { return keterangan.isEmpty(); }

    // getter
    public String getKeterangan() { return keterangan; }
}
