package user;

import java.util.ArrayList;
import java.util.List;

import Menu.MenuItem;
import pesanan.Meja;
import pesanan.DetailPesanan;
import pesanan.Pesanan;
import transaksi.Pembayaran;
import transaksi.Struk;
import transaksi.Transaksi;

public class Customer extends User {
    private String alamat;
    private List<Transaksi> historyTransaksi;
    private List<Pesanan> historyPesanan;
    
    public Customer(String id, String nama, String email, String username, String password, String alamat) {
        super(id, nama, email, username, password, "customer");
        this.alamat = alamat;
        this.historyTransaksi = new ArrayList<>();
        this.historyPesanan = new ArrayList<>();
    }
    
    @Override
    public void displayProfile() {
        System.out.println("=== PROFIL CUSTOMER ===");
        System.out.println("ID: " + id);
        System.out.println("Nama: " + nama);
        System.out.println("Email: " + email);
        System.out.println("Alamat: " + alamat);
        System.out.println("Total Transaksi: " + historyTransaksi.size());
    }
    
    
    public void tambahTransaksi(Transaksi transaksi) {
        historyTransaksi.add(transaksi);
    }
    
    public void tambahPesanan(Pesanan pesanan) {
        historyPesanan.add(pesanan);
    }
    
    public void lihatHistoryTransaksi() {
        System.out.println("=== HISTORY TRANSAKSI " + nama.toUpperCase() + " ===");
        for (Transaksi transaksi : historyTransaksi) {
            System.out.println("- " + transaksi.getIdTransaksi() + 
                             " | Rp" + transaksi.getTotalAmount() + 
                             " | " + transaksi.getStatus());
        }
    }
    
    public void lihatMenu(List<MenuItem> daftarMenu) {
        System.out.println("=== DAFTAR MENU ===");
        for (MenuItem menu : daftarMenu) {
            System.out.println(menu.getInfo());
        }
    }

    public Pesanan buatPesanan(Meja meja, List<DetailPesanan> items) {
        Pesanan pesanan = new Pesanan(meja);
        for (DetailPesanan item : items) {
            pesanan.tambahDetailPesanan(item);
        }
        historyPesanan.add(pesanan);
        System.out.println("Pesanan berhasil dibuat dengan ID: " + pesanan.getIdPesanan());
        return pesanan;
    }

    public Transaksi lakukanPembayaran(Pesanan pesanan, Pembayaran metodePembayaran) {
        Transaksi transaksi = new Transaksi(pesanan, this, null); // Kasir is null for now
        transaksi.setMetodePembayaran(metodePembayaran);
        boolean success = transaksi.prosesTransaksi();

        if (success) {
            historyTransaksi.add(transaksi);
            System.out.println("Pembayaran berhasil untuk pesanan ID: " + pesanan.getIdPesanan());
        } else {
            System.out.println("Pembayaran gagal untuk pesanan ID: " + pesanan.getIdPesanan());
        }
        return transaksi;
    }

    public void lihatStruk(Transaksi transaksi) {
        Struk struk = new Struk(
            transaksi.getIdTransaksi(),
            transaksi.getIdPembayaran(),
            transaksi.getTotalAmount(),
            transaksi.getTanggal(),
            transaksi.getDetailPembayaran()
        );
        System.out.println(struk);
    }

    // Getter
    public List<Transaksi> getHistoryTransaksi() { return historyTransaksi; }
}