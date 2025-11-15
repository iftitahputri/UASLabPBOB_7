package pesanan;

public class Meja {
    private int nomor;
    private String status; // Misal: "Tersedia", "Terisi"

    public Meja(int nomor, String status) {
        this.nomor = nomor;
        this.status = status;
    }

    // Getter
    public int getNomor() {
        return nomor;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }
}
