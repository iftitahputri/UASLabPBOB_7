package services;

import models.meja.KebersihanMeja;
import models.meja.KetersediaanMeja;
import models.meja.Meja;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Service untuk mengelola data dan operasi meja restoran.
 * Menangani inisialisasi, status kebersihan, ketersediaan, dan operasi meja.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Meja
 * @see KebersihanMeja
 * @see KetersediaanMeja
 */
public class MejaService {
    private List<Meja> daftarMeja;
    private Scanner scanner;

    /**
     * Constructor untuk MejaService.
     * 
     * @param scanner scanner untuk input user
     */
    public MejaService(Scanner scanner) {
        this.daftarMeja = new ArrayList<>();
        this.scanner = scanner;
        initMeja(); // buat 15 meja saat service dibuat
    }

    /**
     * Menginisialisasi 15 meja dengan nomor 1-15.
     * Meja dibuat dengan status default: BERSIH dan TERSEDIA.
     */
    private void initMeja() {
        for (int i = 1; i <= 15; i++) {
            daftarMeja.add(new Meja(i));
        }
    }

    /**
     * Mendapatkan meja berdasarkan nomor meja.
     * 
     * @param nomor nomor meja yang dicari (1-15)
     * @return objek Meja jika ditemukan, null jika nomor tidak valid
     */
    public Meja getMejaByNomor(int nomor) {
        if (nomor < 1 || nomor > daftarMeja.size()) {
            return null;
        }
        return daftarMeja.get(nomor - 1);
    }

    /**
     * Menampilkan daftar meja yang bersih dan tersedia.
     * Hanya menampilkan meja dengan status BERSIH dan TERSEDIA.
     */
    public void tampilkanMejaTersedia() {
        System.out.println("=== Meja Bersih & Tersedia ===");
        for (Meja m : daftarMeja) {
            if (m.getKebersihan() == KebersihanMeja.BERSIH &&
                    m.getKetersediaan() == KetersediaanMeja.TERSEDIA) {

                System.out.println("Meja " + m.getNomorMeja());
            }
        }
    }

    /**
     * Mengubah status meja menjadi TERSEDIA.
     * Digunakan ketika meja sudah siap menerima pelanggan baru.
     */
    public void setMejaTersedia() {
        System.out.print("Masukkan nomor meja yang ingin diubah statusnya: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKetersediaan(KetersediaanMeja.TERSEDIA);

        System.out.println("Status meja " + nomorMeja + " telah diubah menjadi TERSEDIA.");
    }

    /**
     * Membersihkan meja dan mengubah status kebersihan menjadi BERSIH.
     * Digunakan setelah pelanggan selesai dan meja perlu dibersihkan.
     */
    public void bersihkanMeja() {
        System.out.print("Masukkan nomor meja yang ingin dibersihkan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKebersihan(KebersihanMeja.BERSIH);

        System.out.println("Meja " + nomorMeja + " telah dibersihkan.");
    }

    /**
     * Mengubah status meja menjadi DIPESAN.
     * Digunakan ketika meja sedang digunakan oleh pelanggan.
     * 
     * @param nomorMeja nomor meja yang dipesan
     */
    public void setMejaDipesan(int nomorMeja) {
        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKetersediaan(KetersediaanMeja.DIPESAN);

        System.out.println("Meja " + nomorMeja + " telah dipesan.");
    }

    /**
     * Membersihkan meja via GUI interface.
     * Sama dengan bersihkanMeja() tetapi tanpa input scanner.
     * 
     * @param nomorMeja nomor meja yang akan dibersihkan
     */
    public void bersihkanMejaGUI(int nomorMeja) {
        Meja meja = daftarMeja.get(nomorMeja - 1);
        meja.setKebersihan(KebersihanMeja.BERSIH);
        System.out.println("Meja " + nomorMeja + " sudah dibersihkan (GUI).");
    }

    /**
     * Mendapatkan daftar semua meja.
     * 
     * @return List berisi semua objek Meja
     */
    public List<Meja> getDaftarMeja() {return daftarMeja; }
}