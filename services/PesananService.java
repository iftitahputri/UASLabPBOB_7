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
import models.pesanan.StatusPesananKoki;

public class PesananService {
    private List<Pesanan> daftarPesanan;
    private MenuService menuService;
    private MejaService mejaService;
    private Scanner scanner;

    public PesananService(MenuService menuService, MejaService mejaService, Scanner scanner) {
        this.daftarPesanan = new ArrayList<>();
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.scanner = scanner;
    }

    public PesananService(MenuService menuService, MejaService mejaService) {
        this.daftarPesanan = new ArrayList<>();
        this.menuService = menuService;
        this.mejaService = mejaService;
    }


    public void buatPesanan() {
        System.out.print("Masukkan nomor meja yang ingin memesan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = mejaService.getMejaByNomor(nomorMeja);
        if (mejaDipilih == null) {
            System.out.println("[X] Meja tidak ditemukan!");
            return;
        }

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

                System.out.println("✔ Ditambahkan: " + detail);
            } else {
                System.out.println("[X] Menu tidak ditemukan / tidak tersedia.");
            }
        }

        if (pesananBaru.getDetailPesanan().isEmpty()) {
            System.out.println("[X] Pesanan kosong, batal dibuat.");
            return;
        }

        daftarPesanan.add(pesananBaru); 
        mejaDipilih.setKebersihan(KebersihanMeja.KOTOR);

        System.out.println("\n[OK] Pesanan berhasil dibuat!");
        System.out.println(pesananBaru);
    }

    public void lihatPesananPelayan() {
        System.out.println("=== Semua Pesanan ===");
        for (Pesanan p : daftarPesanan) {
            System.out.println(p);
        }
    }

    public void lihatPesananKoki() {
        System.out.println("=== Pesanan untuk Dimasak ===");
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.DIPESAN) {
                System.out.println(p);
            }
        }
    }

    public void lihatPesananKasir() {
        System.out.println("=== Pesanan Siap Bayar ===");
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.SELESAI) {
                System.out.println(p);
            }
        }
    }

    public List<Pesanan> getPesananSelesai() {
        List<Pesanan> pesananSelesai = new ArrayList<>();
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.SELESAI) {
                pesananSelesai.add(p);
            }
        }
        return pesananSelesai;  
    }


    public void updateStatusKoki(int idPesanan, StatusPesananKoki statusBaru) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan) {
                p.setStatusKoki(statusBaru);
                return;
            }
        }
    }
    
    

    public void updateStatusPelayan(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatusKoki() == StatusPesananKoki.DIMASAK) {
                p.setStatus(StatusPesanan.SELESAI);
                System.out.println("Status diupdate menjadi SELESAI");
            }
        }
    }

    public Pesanan cariPesananById(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan) {
                return p;
            }
        }
        return null;
    }

    public void setPesananLunas(int idPesanan) {
        Pesanan pesanan = cariPesananById(idPesanan);
        if (pesanan != null) {
            pesanan.setStatus(StatusPesanan.LUNAS);
            System.out.println("✅ Status pesanan " + idPesanan + " diupdate menjadi LUNAS");
        }
    }

    public List<Pesanan> getDaftarPesanan() { return daftarPesanan; }
}
