import java.util.Scanner;
import models.auth.Akun;
import services.*;

public class RestaurantSystem {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;
    private AuthService authService;
    private PembayaranService pembayaranService;
    private Scanner scanner;
    
    public RestaurantSystem() {
        this.scanner = new Scanner(System.in);
        this.menuService = new MenuService();
        this.mejaService = new MejaService(scanner);
        this.pesananService = new PesananService(menuService, mejaService, scanner);
        this.authService = new AuthService(scanner);
        this.pembayaranService = new PembayaranService(pesananService, scanner);
    }
    
    // ==================== MAIN MENU ====================
    public void mulai() {
        while (true) {
            System.out.println("\n=== SISTEM RESTORAN ===");
            System.out.println("1. User (Pelanggan)");
            System.out.println("2. Daftar Pegawai");
            System.out.println("3. Login Pegawai");
            System.out.println("4. Login Manager");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    menuUser();
                    break;
                case 2:
                    daftarAkunPegawai();
                    break;
                case 3:
                    loginPegawai();
                    break;
                case 4:
                    loginManager();
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    menuService.saveMenuToCSV();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    // ==================== MENU USER (PELANGGAN) ====================
    private void menuUser() {
        System.out.println("\nMasukkan nama Anda:");
        String nama = scanner.nextLine();
        
        System.out.println("\n=== MEJA TERSEDIA ===");
        mejaService.tampilkanMejaTersedia();
        
        System.out.print("\nMasukkan Nomor Meja: ");
        int nomorMeja = scanner.nextInt();
        scanner.nextLine();
                
        mejaService.setMejaDipesan(nomorMeja);
        
        System.out.println("Selamat datang, " + nama + " di Meja " + nomorMeja);
        
        System.out.println("Berikut adalah menu kami:");
        menuService.tampilkanMenu();
        
        System.out.println("Apakah Anda ingin membuat pesanan? (ya/tidak)");
        String jawaban = scanner.nextLine();
        if (jawaban.equalsIgnoreCase("ya")) {
            System.out.println("Pelanggan dipanggil untuk membuat pesanan. Silakan sebutkan pesanan Anda kepada staf.");
        } else if (jawaban.equalsIgnoreCase("tidak")) {
            System.out.println("Terima kasih. Silakan hubungi staf jika Anda membutuhkan sesuatu.");
        }
    }
    
    // ==================== MENU PELAYAN ====================
    private void menuPelayan() {
        while (true) {
            System.out.println("\n=== MENU PELAYAN ===");
            System.out.println("1. Bersihkan Meja");
            System.out.println("2. Set Meja Tersedia");
            System.out.println("3. Buat Pesanan Baru");
            System.out.println("4. Lihat Daftar Pesanan");
            System.out.println("5. Update Status Pesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();
            
            switch (pilihan) {
                case 1:
                    mejaService.bersihkanMeja();
                    break;
                case 2:
                    mejaService.setMejaTersedia();
                    break;
                case 3:
                    pesananService.buatPesanan();
                    break;
                case 4:
                    pesananService.lihatPesananPelayan();
                    break;
                case 5:
                    System.out.print("Masukkan ID Pesanan yang sudah selesai: ");
                    int idPesanan = scanner.nextInt();
                    scanner.nextLine();
                    pesananService.updateStatusPelayan(idPesanan);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }
    
    // ==================== MENU KOKI ====================
    private void menuKoki() {
        while (true) {
            System.out.println("\n=== MENU KOKI ===");
            System.out.println("1. Lihat Pesanan untuk Dimasak");
            System.out.println("2. Update Status Pesanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    pesananService.lihatPesananKoki();
                    break;
                case 2:
                    System.out.print("Masukkan ID Pesanan yang sudah dimasak: ");
                    int idPesanan = scanner.nextInt();
                    scanner.nextLine();
                    pesananService.updateStatusKoki(idPesanan);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }
    
    // ==================== MENU KASIR ====================
    private void menuKasir() {
        while (true) {
            System.out.println("\n=== MENU KASIR ===");
            System.out.println("1. Proses Pembayaran");
            System.out.println("2. Cetak Ulang Struk");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    pembayaranService.prosesPembayaran();
                    break;
                case 2:
                    pembayaranService.cetakUlangStruk(); 
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Pilihan tidak valid");
            }
        }
    }
    
    // ==================== AUTHENTICATION ====================
    private void loginManager() {
        System.out.print("Username: ");
        String u = scanner.nextLine();
        System.out.print("Password: ");
        String p = scanner.nextLine();

        if (authService.loginManager(u, p)) {
            menuManager();
        } else {
            System.out.println("Login gagal.");
        }
    }
    
    private void menuManager() {
        while (true) {
            System.out.println("\n=== MENU MANAGER ===");
            System.out.println("1. Tambah Data Pegawai");
            System.out.println("2. Lihat Data Pegawai");
            System.out.println("3. Ganti Username/Password Manager");
            System.out.println("0. Logout");
            System.out.print("> ");
            int p = scanner.nextInt(); 
            scanner.nextLine();

            switch (p) {
                case 1:
                    authService.tambahPegawai();
                    break;
                case 2:
                    authService.lihatDataPegawai();
                    break;
                case 3:
                    ubahAkunManager();
                    break;
                case 0:
                    return;
            }
        }
    }
    
    private void ubahAkunManager() {
        System.out.print("Username baru: ");
        String u = scanner.nextLine();
        System.out.print("Password baru: ");
        String p = scanner.nextLine();

        authService.updateAkunManager(u, p);
        System.out.println("Akun manager diperbarui.");
    }
    
    private void daftarAkunPegawai() {
        System.out.println("\n=== DAFTAR AKUN PEGAWAI ===");

        System.out.print("Masukkan ID Pegawai: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("Buat Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Buat Password: ");
        String password = scanner.nextLine();

        authService.daftarAkunPegawai(id, username, password);
    }
    
    private void loginPegawai() {
        System.out.print("Username: ");
        String u = scanner.nextLine();
        System.out.print("Password: ");
        String p = scanner.nextLine();

        Akun akun = authService.loginPegawai(u, p);
        if (akun == null) {
            System.out.println("Login gagal!");
            return;
        }

        String role = authService.getRoleFromId(akun.getIdPegawai());
        switch (role) {
            case "PELAYAN": 
                menuPelayan(); 
                break;
            case "KOKI": 
                menuKoki(); 
                break;
            case "KASIR": 
                menuKasir(); 
                break;
            default: 
                System.out.println("Role tidak dikenal pada ID ini!");
        }
    }
    
    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        RestaurantSystem system = new RestaurantSystem();
        system.mulai();
    }
}