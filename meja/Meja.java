package meja;

public class Meja {
    private NomorMeja nomor;
    private StatusMeja status;
    private KapasitasMeja kapasitas;

    public Meja(int nomor, int kapasitas) {
        this.nomor = new NomorMeja(nomor);
        this.status = new StatusMeja();
        this.kapasitas = new KapasitasMeja(kapasitas);
    }

    public void pesan() {
        if (status.getStatus().equalsIgnoreCase("Kosong")) {
            status.setDipesan();
        } else {
            System.out.println("Meja " + nomor.getNomor() + " sudah " + status.getStatus());
        }
    }

    public void kosongkan() {
        if (status.getStatus().equalsIgnoreCase("Dipesan")) {
            status.setKosong();
        } else {
            System.out.println("Meja " + nomor.getNomor() + " sudah kosong");
        }
    }

    @Override
    public String toString() {
        return "Meja " + nomor.getNomor() + " (" + status.getStatus() + ", kapasitas: " + kapasitas.getKapasitas() + ")";
    }
}
