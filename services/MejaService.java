package services;
 
 import models.meja.KebersihanMeja;
 import models.meja.KetersediaanMeja;
 import models.meja.Meja;
 
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class MejaService {
    private List<Meja> daftarMeja;
    private Scanner scanner;

    public MejaService(Scanner scanner) {
        this.daftarMeja = new ArrayList<>();
        this.scanner = scanner;
        initMeja(); // buat 15 meja saat service dibuat
    }

    private void initMeja() {
        for (int i = 1; i <= 15; i++) {
            daftarMeja.add(new Meja(i));
        }
    }

    public Meja getMejaByNomor(int nomor) {
        if (nomor < 1 || nomor > daftarMeja.size()) {
            return null;
        }
        return daftarMeja.get(nomor - 1);
    }

    public void tampilkanMejaTersedia() {
        System.out.println("=== Meja Bersih & Tersedia ===");
        for (Meja m : daftarMeja) {
            if (m.getKebersihan() == KebersihanMeja.BERSIH &&
                    m.getKetersediaan() == KetersediaanMeja.TERSEDIA) {

                System.out.println("Meja " + m.getNomorMeja());
            }
        }
    }

    public void setMejaTersedia() {
        System.out.print("Masukkan nomor meja yang ingin diubah statusnya: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKetersediaan(KetersediaanMeja.TERSEDIA);

        System.out.println("Status meja " + nomorMeja + " telah diubah menjadi TERSEDIA.");

    }

    public void bersihkanMeja() {
        System.out.print("Masukkan nomor meja yang ingin dibersihkan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKebersihan(KebersihanMeja.BERSIH);

        System.out.println("Meja " + nomorMeja + " telah dibersihkan.");
    }

    public void setMejaDipesan(int nomorMeja) {
        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKetersediaan(KetersediaanMeja.DIPESAN);

        System.out.println("Meja " + nomorMeja + " telah dipesan.");
    }

    public void bersihkanMejaGUI(int nomorMeja) {
    Meja meja = daftarMeja.get(nomorMeja - 1);
    meja.setKebersihan(KebersihanMeja.BERSIH);
    System.out.println("Meja " + nomorMeja + " sudah dibersihkan (GUI).");
    }

    public List<Meja> getDaftarMeja() {return daftarMeja; }


}