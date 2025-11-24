package services;

import java.util.List;
import java.util.Scanner;

import models.pesanan.Pesanan;
import models.pesanan.StatusPesanan;
import models.transaksi.CardPayment;
import models.transaksi.CashPayment;
import models.transaksi.Pembayaran;
import models.transaksi.QRISPayment;
import models.transaksi.Struk;

public class PembayaranService {
    private PesananService pesananService;
    private Scanner scanner;

    public PembayaranService(PesananService pesananService, Scanner scanner) {
        this.pesananService = pesananService;
        this.scanner = scanner;
    }

    public void prosesPembayaran() {
        System.out.println("\n=== PROSES PEMBAYARAN ===");
        
        List<Pesanan> pesananSelesai = pesananService.getPesananSelesai();
        if (pesananSelesai.isEmpty()) {
            System.out.println("❌ Tidak ada pesanan yang siap dibayar.");
            return;
        }
    
        for (Pesanan p : pesananSelesai) {
            System.out.println(p);
            System.out.println("-------------------");
        }
    
        System.out.print("Masukkan ID Pesanan untuk dibayar: ");
        int idPesanan = scanner.nextInt();
        scanner.nextLine();
    
        Pesanan pesananBayar = pesananService.cariPesananById(idPesanan);
        
        if (pesananBayar == null || pesananBayar.getStatus() != StatusPesanan.SELESAI) {
            System.out.println("❌ Pesanan tidak ditemukan / belum siap dibayar.");
            return;
        }
    
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
                System.out.println("❌ Metode tidak valid");
                return;
        }
        boolean pembayaranBerhasil = pembayaran.prosesPembayaran();
        pembayaran.prosesPembayaran();
        
        if(pembayaranBerhasil){
            System.out.println("✅ Pembayaran berhasil!");
            pesananService.setPesananLunas(idPesanan);
            Struk struk = new Struk(pesananBayar, pembayaran);
            struk.cetak();

        }else{
            System.out.println("Pembayaran gagal!");
        }
    
        
    }


}