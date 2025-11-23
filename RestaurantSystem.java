import java.io.*;
import java.util.*;

import DetailPesanan.DetailPesanan;
import DetailPesanan.Pesanan;
import DetailPesanan.StatusPesanan;
import Meja.KebersihanMeja;
import Meja.KetersediaanMeja;
import Meja.Meja;
import Menu.MenuItem;
import Menu.Makanan;
import Menu.Minuman;
import Transaksi.CardPayment;
import Transaksi.CashPayment;
import Transaksi.QRISPayment;
import Transaksi.Pembayaran;
import Transaksi.Struk;

public class RestaurantSystem {
    private List<MenuItem> daftarMenu;
    private List<Meja> daftarMeja;
    private List<Pesanan> daftarPesanan = new ArrayList<>();
    private Scanner scanner;
    private static final String FILE_MENU = "menu.csv";

    public RestaurantSystem() {
        this.daftarMenu = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadData();

        daftarMeja = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            daftarMeja.add(new Meja(i));
        }
    }

    private Meja getMejaByNomor(int nomor) {
        if (nomor < 1 || nomor > daftarMeja.size()) {
            return null;
        }
        return daftarMeja.get(nomor - 1);
    }

    private void loadData() {
        loadMenuFromCSV();

        // Jika setelah load, menu masih kosong → bikin default dan load ulang
        if (daftarMenu.isEmpty()) {
            System.out.println("CSV kosong atau tidak ditemukan — membuat default...");
            createDefaultCSVMenu();
            daftarMenu.clear();
            loadMenuFromCSV();
        }
    }

    private void loadMenuFromCSV() {
        daftarMenu.clear(); // selalu bersihkan list sebelum load

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_MENU))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                if (data.length >= 7) {
                    String id = data[0].trim();
                    String nama = data[1].trim();
                    double harga = Double.parseDouble(data[2].trim());
                    String tipe = data[3].trim();
                    boolean tersedia = Boolean.parseBoolean(data[4].trim());
                    String attrib1 = data[5].trim();
                    String attrib2 = data[6].trim();

                    MenuItem item;

                    if (tipe.equalsIgnoreCase("makanan")) {
                        item = new Makanan(id, nama, harga, tipe, tersedia, attrib1, attrib2);
                    } else if (tipe.equalsIgnoreCase("minuman")) {
                        item = new Minuman(id, nama, harga, tipe, tersedia, attrib1, attrib2);
                    } else {
                        System.out.println("Tipe tidak dikenal: " + tipe);
                        continue;
                    }

                    daftarMenu.add(item);

                } else {
                    System.out.println("Data tidak lengkap: " + line);
                }
            }

            System.out.println("Menu loaded: " + daftarMenu.size() + " items");

        } catch (IOException e) {
            // Tidak error di sini — biarkan loadData() yang menangani
        }
    }

    private void createDefaultCSVMenu() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_MENU))) {

            writer.println("ID,Nama,Harga,Tipe,Tersedia,Attrib1,Attrib2");

            String[][] defaultMenu = {
                    { "F01", "Nasi Goreng", "25000", "makanan", "true", "Sedang", "Main Course" },
                    { "F02", "Mie Goreng", "20000", "makanan", "true", "Pedas", "Main Course" },
                    { "F03", "Ayam Goreng", "18000", "makanan", "true", "Tidak Pedas", "Main Course" },
                    { "D01", "Es Teh", "5000", "minuman", "true", "Medium", "Dingin" },
                    { "D02", "Jus Jeruk", "8000", "minuman", "true", "Large", "Dingin" },
                    { "D03", "Kopi", "12000", "minuman", "true", "Small", "Panas" }
            };

            for (String[] menu : defaultMenu) {
                writer.println(String.join(",", menu));
            }

            System.out.println("CSV default dibuat.");

        } catch (IOException e) {
            System.out.println("Error membuat CSV default: " + e.getMessage());
        }
    }

    private void saveMenuToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_MENU))) {
            writer.println("ID,Nama,Harga,Tipe,Tersedia,Attrib1,Attrib2");

            for (MenuItem menu : daftarMenu) {
                if (menu instanceof Makanan m) {
                    writer.println(m.getId() + "," +
                            m.getNama() + "," +
                            m.getHarga() + "," +
                            "makanan" + "," +
                            m.isTersedia() + "," +
                            m.getTingkatPedas() + "," +
                            m.getKategoriMakan());

                } else if (menu instanceof Minuman m) {
                    writer.println(m.getId() + "," +
                            m.getNama() + "," +
                            m.getHarga() + "," +
                            "minuman" + "," +
                            m.isTersedia() + "," +
                            m.getUkuran() + "," +
                            m.getSuhu());
                }
            }

            System.out.println("Menu disimpan ke CSV.");

        } catch (IOException e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    private void tampilkanMenu() {
        System.out.println("\n=== DAFTAR MENU ===");
        System.out.printf("%-5s %-20s %-10s %-10s %-10s %-15s %s\n",
                "ID", "Nama", "Harga", "Tipe", "Tersedia", "Attrib1", "Attrib2");

        for (MenuItem menu : daftarMenu) {
            if (menu instanceof Makanan m) {
                System.out.printf("%-5s %-20s %-10.0f %-10s %-10s %-15s %s\n",
                        m.getId(), m.getNama(), m.getHarga(), "Makanan",
                        m.isTersedia() ? "Ya" : "Tidak",
                        m.getTingkatPedas(), m.getKategoriMakan());

            } else if (menu instanceof Minuman m) {
                System.out.printf("%-5s %-20s %-10.0f %-10s %-10s %-15s %s\n",
                        m.getId(), m.getNama(), m.getHarga(), "Minuman",
                        m.isTersedia() ? "Ya" : "Tidak",
                        m.getUkuran(), m.getSuhu());
            }
        }
    }

    private MenuItem cariMenuById(String id) {
        for (MenuItem menu : daftarMenu) {
            if (menu.getId().equalsIgnoreCase(id)) {
                return menu;
            }
        }
        return null;
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

    private void setMejaTersedia() {
        System.out.print("Masukkan nomor meja yang ingin diubah statusnya: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.setKetersediaan(KetersediaanMeja.TERSEDIA);

        System.out.println("Status meja " + nomorMeja + " telah diubah menjadi TERSEDIA.");

    }

    private void bersihkanMeja() {
        System.out.print("Masukkan nomor meja yang ingin dibersihkan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        mejaDipilih.kebersihan(KebersihanMeja.BERSIH);

        System.out.println("Meja " + nomorMeja + " telah dibersihkan.");
    }

    private void buatPesanan() {
        System.out.print("Masukkan nomor meja yang ingin memesan: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);
        if (mejaDipilih == null) {
            System.out.println("[X] Meja tidak ditemukan!");
            return;
        }

        Pesanan pesananBaru = new Pesanan(mejaDipilih);

        System.out.println("\n=== BUAT PESANAN ===");
        tampilkanMenu();

        while (true) {
            System.out.print("\nMasukkan ID Menu (0 untuk selesai): ");
            String idMenu = scanner.nextLine().trim();
            if (idMenu.equals("0")) break;

            MenuItem menu = cariMenuById(idMenu);
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

        daftarPesanan.add(pesananBaru); // simpan ke list pesanan
        mejaDipilih.kebersihan(KebersihanMeja.KOTOR);

        System.out.println("\n[OK] Pesanan berhasil dibuat!");
        System.out.println(pesananBaru);
    }

    private void lihatPesananPelayan() {
    System.out.println("=== Semua Pesanan ===");
    for (Pesanan p : daftarPesanan) {
        System.out.println(p);
    }
    }

    private void lihatPesananKoki() {
        System.out.println("=== Pesanan untuk Dimasak ===");
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.DIPESAN) {
                System.out.println(p);
            }
        }
    }

    private void updateStatusKoki(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatus() == StatusPesanan.DIPESAN) {
                p.setStatus(StatusPesanan.DIMASAK);
                System.out.println("Status diupdate menjadi DIMASAK");
            }
        }
    }

    private void updateStatusPelayan(int idPesanan) {
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatus() == StatusPesanan.DIMASAK) {
                p.setStatus(StatusPesanan.SELESAI);
                System.out.println("Status diupdate menjadi SELESAI");
            }
        }
    }

    public void mulai() {
        while (true) {
            System.out.println("\n=== SISTEM RESTORAN ===");
            System.out.println("1. User (Pelanggan)");
            System.out.println("2. Pekerja (Staff)");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    menuUser();
                    break;
                case 2:
                    menuPegawai();
                    break;
                case 3:
                    saveMenuToCSV();
                    break;
                case 0: {
                    System.out.println("Terima kasih!");
                    saveMenuToCSV();
                    return;
                }
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void menuUser() {
        System.out.println("\nMasukkan nama Anda:");
        String nama = scanner.nextLine();
        System.out.println("\n=== MEJA TERSEDIA ===");

        for (Meja m : daftarMeja) {
            if ((m.getKebersihan() == KebersihanMeja.BERSIH && m.getKetersediaan() == KetersediaanMeja.TERSEDIA)) {
                System.out.println("Meja " + m.getNomorMeja());
            }
        }

        System.out.print("\nMasukkan Nomor Meja: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();

        Meja mejaDipilih = getMejaByNomor(nomorMeja);

        if (mejaDipilih == null) {
            System.out.println("Meja tidak ditemukan.");
            return;
        }

        if (mejaDipilih.getKebersihan() != KebersihanMeja.BERSIH
                || mejaDipilih.getKetersediaan() != KetersediaanMeja.TERSEDIA) {
            System.out.println("Meja tidak dapat digunakan.");
            return;
        }

        mejaDipilih.setKetersediaan(KetersediaanMeja.DIPESAN);

        System.out.println("Selamat datang, " + nama + " di Meja " + nomorMeja);

        System.out.println("Berikut adalah menu kami:");
        tampilkanMenu();
        System.out.println("Apakah Anda ingin membuat pesanan? (ya/tidak)");
        String jawaban = scanner.nextLine();
        if (jawaban.equalsIgnoreCase("ya")) {
            System.out.println("Pelanggan dipanggil untuk membuat pesanan. Silakan sebutkan pesanan Anda kepada staf.");
        } else if (jawaban.equalsIgnoreCase("tidak")) {
            System.out.println("Terima kasih. Silakan hubungi staf jika Anda membutuhkan sesuatu.");
        }

        return;
    }

    private void menuPegawai() {
        while (true) {
            System.out.println("\n=== MENU PEGAWAI ===");
            System.out.println("1. Pelayan");
            System.out.println("2. Koki");
            System.out.println("3. Kasir");
            System.out.println("4. Manager");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    menuPelayan();
                    break;
                case 2:
                    menuKoki();
                    break;
                case 3:
                    menuKasir();
                    break;
                case 4:
                    menuManager();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }

    }

    private void menuPelayan() {

        while (true) {
            System.out.println("=== MENU PELAYAN ===");
            System.out.println("1. Bersihkan Meja");
            System.out.println("2. Set Meja Tersedia/Tidak Tersedia");
            System.out.println("3. Buat Pesanan Baru");
            System.out.println("4. Lihat Daftar Pesanan");
            System.out.println("5. Update Status Pesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();
            switch (pilihan) {
                case 1:
                    bersihkanMeja();
                    break;
                case 2:
                    setMejaTersedia();
                    break;
                case 3:
                    buatPesanan();
                    break;
                case 4:
                    lihatPesananPelayan();
                    break;
                case 5:
                    System.out.print("Masukkan ID Pesanan yang sudah selesai: ");
                    int idPesanan = scanner.nextInt();
                    scanner.nextLine();
                    updateStatusPelayan(idPesanan);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }

    }

    private void menuKoki() {
        while (true) {
            System.out.println("=== MENU KOKI ===");
            System.out.println("1. Lihat Pesanan untuk Dimasak");
            System.out.println("2. Update Status Pesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    lihatPesananKoki();
                    break;
                case 2:
                    System.out.print("Masukkan ID Pesanan yang sudah dimasak: ");
                    int idPesanan = scanner.nextInt();
                    scanner.nextLine();
                    updateStatusKoki(idPesanan);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }

    private void menuKasir() {
        System.out.println("\n=== MENU KASIR ===");

        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.SELESAI) {
                System.out.println(p);
            }
        }

        System.out.print("Masukkan ID Pesanan untuk dibayar: ");
        int idPesanan = scanner.nextInt();
        scanner.nextLine();

        Pesanan pesananBayar = null;
        for (Pesanan p : daftarPesanan) {
            if (p.getIdPesanan() == idPesanan && p.getStatus() == StatusPesanan.SELESAI) {
                pesananBayar = p;
                break;
            }
        }

        if (pesananBayar == null) {
            System.out.println("Pesanan tidak ditemukan / belum siap dibayar.");
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
                {
                System.out.println("Metode tidak valid");
                return;
            }
        }

        pembayaran.prosesPembayaran();
        pesananBayar.setStatus(StatusPesanan.LUNAS);

        Struk struk = new Struk(pesananBayar, pembayaran);
        struk.cetak();
    }

    public void menuManager() {
    }
    public static void main(String[] args) {
        RestaurantSystem system = new RestaurantSystem();
        system.mulai();
    }
}