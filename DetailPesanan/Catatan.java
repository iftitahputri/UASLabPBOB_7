package DetailPesanan;

public class Catatan {
    private String keterangan;

    public Catatan(String keterangan) {
        this.keterangan = (keterangan == null) ? "" : keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public boolean isEmpty() {
        return keterangan.isEmpty();
    }
}
