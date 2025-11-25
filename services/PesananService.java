package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.meja.KebersihanMeja;
import models.meja.Meja;
import models.menu.MenuItem;
import models.pesanan.DetailPesanan;
import models.pesanan.Pesanan;
import models.pesanan.StatusPesanan;

/**
 * Service untuk mengelola semua operasi terkait pesanan.
 * Menangani pembuatan, pencarian, update status, dan retrieval pesanan.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Pesanan
 * @see DetailPesanan
 * @see StatusPesanan
 */
public class PesananService {
    private List<Pesanan> daftarPesanan;
    private MenuService menuService;
    private MejaService mejaService;
    private Scanner scanner;

    /**
     * Constructor dengan scanner untuk input user.
     * 
     * @param menuService service untuk mengakses data menu
     * @param mejaService service untuk mengakses data meja
     * @param scanner scanner untuk input user
     */
    public PesananService(MenuService menuService, MejaService mejaService, Scanner scanner) {
        this.daftarPesanan = new ArrayList<>();
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.scanner = scanner;
    }

    /**
     * Constructor tanpa scanner, digunakan untuk GUI.
     * 
     * @param menuService service untuk mengakses data menu
     * @param mejaService service untuk mengakses data meja
     */
    public PesananService(MenuService menuService, MejaService mejaService) {
        this.daftarPesanan = new ArrayList<>();
        this.menuService = menuService;
        this.mejaService = mejaService;
    }

    /**
     * Membuat pesanan baru untuk meja tertentu.
     * Proses meliputi pemilihan meja, pemilihan menu, dan input detail pesanan.
     * Setelah pesanan dibuat, status meja diubah menjadi KOTOR.
     */
    public void buatPesanan() {
        System.out.print("Masukkan nomor meja yang ingin memesan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        // dapatkan meja berdasarkan nomor
        Meja mejaDipilih = mejaService.getMejaByNomor(nomorMeja);
        if (mejaDipilih == null) {
            System.out.println("[X] Meja tidak ditemukan!");
            return;
        }

        // buat pesanan baru
        Pesanan pesananBaru = new Pesanan(mejaDipilih);

        System.out.println("\n=== BUAT PESANAN ===");
        menuService.tampilkanMenu();

        while (true) {
            System.out.print("\nMasukkan ID Menu (0 untuk selesai): ");
            String idMenu = scanner.nextLine().trim();
            if (idMenu.equals("0")) break;

            MenuItem menu = menuService.cariMenuById(idMenu);
            if (menu != null && menu.isTersedia()) {
                System.out.print("Jumlah: ");
                int jumlah = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Catatan (bisa kosong): ");
                String catatan = scanner.nextLine();

                DetailPesanan detail = new DetailPesanan(menu, jumlah, catatan);
                pesananBaru.tambahDetailPesanan(detail);

                System.out.println("Ditambahkan: " + detail);
            } else {
                System.out.println("Menu tidak ditemukan / tidak tersedia.");
            }
        }

        if (pesananBaru.getDetailPesanan().isEmpty()) {
            System.out.println("Pesanan kosong, batal dibuat.");
            return;
        }

        // simpan pesanan baru
        daftarPesanan.add(pesananBaru); 
        mejaDipilih.setKebersihan(KebersihanMeja.KOTOR);

        System.out.println("\nPesanan berhasil dibuat!");
        System.out.println(pesananBaru);
    }

    /**
     * Menampilkan semua pesanan yang ada dalam sistem.
     * Digunakan oleh pelayan untuk melihat seluruh pesanan.
     */
    public void lihatPesananPelayan() {
        System.out.println("=== Semua Pesanan ===");
        for (Pesanan p : daftarPesanan) {
            System.out.println(p);
        }
    }

    /**
     * Menampilkan pesanan yang perlu dimasak oleh koki.
     * Hanya menampilkan pesanan dengan status DIPESAN atau DIMASAK.
     */
    public void lihatPesananKoki() {
        System.out.println("=== Pesanan untuk Dimasak ===");
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.DIPESAN || p.getStatus() == StatusPesanan.DIMASAK) {
                System.out.println(p);
            }
        }
    }

    /**
     * Menampilkan pesanan yang siap untuk dibayar.
     * Hanya menampilkan pesanan dengan status SELESAI.
     */
    public void lihatPesananKasir() {
        System.out.println("=== Pesanan Siap Bayar ===");
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.SELESAI) {
                System.out.println(p);
            }
        }
    }

    /**
     * Mendapatkan daftar pesanan yang sudah selesai (status SELESAI).
     * 
     * @return List berisi pesanan dengan status SELESAI
     */
    public List<Pesanan> getPesananSelesai() {
        List<Pesanan> pesananSelesai = new ArrayList<>();
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.SELESAI) {
                pesananSelesai.add(p);
            }
        }
        return pesananSelesai;  
    }

    /**
     * Mengupdate status pesanan dari DIPESAN menjadi DIMASAK.
     * Hanya bisa diupdate jika status saat ini adalah DIPESAN.
     * 
     * @param idPesanan ID pesanan yang akan diupdate
     */
    public void updateStatusKokiMasak(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatus() == StatusPesanan.DIPESAN) {
                p.setStatus(StatusPesanan.DIMASAK);
                System.out.println("Status diupdate menjadi DIMASAK");
                return;
            }
        }
    }
    
    /**
     * Mengupdate status pesanan dari DIMASAK menjadi SELESAI.
     * Hanya bisa diupdate jika status saat ini adalah DIMASAK.
     * 
     * @param idPesanan ID pesanan yang akan diupdate
     */
    public void updateStatusKokiSelesai(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatus() == StatusPesanan.DIMASAK) {
                p.setStatus(StatusPesanan.SELESAI);
                System.out.println("Status diupdate menjadi SELESAI");
            }
        }
    }

    /**
     * Mencari pesanan berdasarkan ID.
     * 
     * @param idPesanan ID pesanan yang dicari
     * @return objek Pesanan jika ditemukan, null jika tidak ditemukan
     */
    public Pesanan cariPesananById(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan) {
                return p;
            }
        }
        return null;
    }

    /**
     * Mengupdate status pesanan ke status baru.
     * 
     * @param idPesanan ID pesanan yang akan diupdate
     * @param statusBaru status baru untuk pesanan
     */
    public void updateStatus(int idPesanan, StatusPesanan statusBaru) {
        Pesanan pesanan = cariPesananById(idPesanan);
        if (pesanan != null) {
            pesanan.setStatus(statusBaru);
        }
    }

    /**
     * Mengubah status pesanan menjadi LUNAS.
     * Digunakan setelah pembayaran berhasil.
     * 
     * @param idPesanan ID pesanan yang sudah dibayar
     */
    public void setPesananLunas(int idPesanan) {
        Pesanan pesanan = cariPesananById(idPesanan);
        if (pesanan != null) {
            pesanan.setStatus(StatusPesanan.LUNAS);
            System.out.println("Status pesanan " + idPesanan + " diupdate menjadi LUNAS");
        }
    }

    /**
     * Mendapatkan daftar semua pesanan dalam sistem.
     * 
     * @return List berisi semua pesanan
     */
    public List<Pesanan> getDaftarPesanan() { return daftarPesanan; }
}