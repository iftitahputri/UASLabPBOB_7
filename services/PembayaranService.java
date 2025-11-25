package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import models.pesanan.Pesanan;
import models.pesanan.StatusPesanan;
import models.transaksi.CardPayment;
import models.transaksi.CashPayment;
import models.transaksi.Pembayaran;
import models.transaksi.QRISPayment;
import models.transaksi.Struk;

// class untuk service pembayaran
public class PembayaranService {
    private PesananService pesananService;
    private Scanner scanner;
    private Map<Integer, Struk> daftarStruk;

    // constructor
    public PembayaranService(PesananService pesananService, Scanner scanner) {
        this.pesananService = pesananService;
        this.scanner = scanner;
        this.daftarStruk = new HashMap<>();
    }

    // method proses pembayaran
    public void prosesPembayaran() {
        System.out.println("\n=== PROSES PEMBAYARAN ===");
        
        // tampilkan pesanan yang siap dibayar
        List<Pesanan> pesananSelesai = pesananService.getPesananSelesai();
        if (pesananSelesai.isEmpty()) {
            System.out.println("Tidak ada pesanan yang siap dibayar.");
            return;
        }
    
        for (Pesanan p : pesananSelesai) {
            System.out.println(p);
            System.out.println("-------------------");
        }
    
        System.out.print("Masukkan ID Pesanan untuk dibayar: ");
        int idPesanan = scanner.nextInt();
        scanner.nextLine();
        
        // cari pesanan berdasarkan id
        Pesanan pesananBayar = pesananService.cariPesananById(idPesanan);
        
        if (pesananBayar == null || pesananBayar.getStatus() != StatusPesanan.SELESAI) {
            System.out.println("Pesanan tidak ditemukan / belum siap dibayar.");
            return;
        }
        
        // pilih metode pembayaran
        System.out.println("Pilih metode pembayaran: ");
        System.out.println("1. Cash");
        System.out.println("2. Card"); 
        System.out.println("3. QRIS");
        int metode = scanner.nextInt();
        scanner.nextLine();
    
        Pembayaran pembayaran = null;
        switch (metode) {
            case 1:
                pembayaran = new CashPayment(pesananBayar.hitungTotal());
                break;
            case 2:
                pembayaran = new CardPayment(pesananBayar.hitungTotal());
                break;
            case 3:
                pembayaran = new QRISPayment(pesananBayar.hitungTotal());
                break;
            default:
                System.out.println("Metode tidak valid");
                return;
        }
        boolean pembayaranBerhasil = pembayaran.prosesPembayaran();
        
        if(pembayaranBerhasil){
            System.out.println("Pembayaran berhasil!");
            pesananService.setPesananLunas(idPesanan);
            Struk struk = new Struk(pesananBayar, pembayaran);
            struk.cetak();
            daftarStruk.put(idPesanan, struk);

        }else{
            System.out.println("Pembayaran gagal!");
        }
    
    }

    // method cetak ulang struk
    public void cetakUlangStruk() {
        System.out.println("\n=== CETAK ULANG STRUK ===");
        
        System.out.print("Masukkan ID Pesanan: ");
        int idPesanan = scanner.nextInt();
        scanner.nextLine();

        Pesanan pesanan = pesananService.cariPesananById(idPesanan);
        if (pesanan == null || pesanan.getStatus() != StatusPesanan.LUNAS) {
            System.out.println("Pesanan tidak ditemukan / belum lunas.");
            return;
        }

        // dapatkan struk dari daftar struk
        Struk strukAsli = daftarStruk.get(idPesanan);
        if (strukAsli == null) {
            System.out.println("Data struk tidak ditemukan untuk pesanan ini.");
            return;
        }

        // cetak ulang struk
        System.out.println("MENCETAK ULANG STRUK...");
        strukAsli.cetak();
        
        System.out.println("Struk berhasil dicetak ulang!");
    }
}
