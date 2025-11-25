import java.util.Scanner;
import models.auth.Akun;
import services.*;

/**
 * Sistem utama restoran yang mengkoordinasi semua service dan menyediakan interface CLI.
 * Class ini bertindak sebagai facade untuk seluruh operasi sistem restoran.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @since 2025
 */
public class RestaurantSystem {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;
    private AuthService authService;
    private PembayaranService pembayaranService;
    private Scanner scanner;

    /**
     * Constructor utama yang menginisialisasi semua service yang diperlukan.
     * Service-service ini akan digunakan throughout the application.
     */
    public RestaurantSystem() {
        this.scanner = new Scanner(System.in);
        this.menuService = new MenuService();
        this.mejaService = new MejaService(scanner);
        this.pesananService = new PesananService(menuService, mejaService, scanner);
        this.authService = new AuthService(scanner);
        this.pembayaranService = new PembayaranService(pesananService, scanner);
    }

    // ==================== MAIN MENU ====================
    
    /**
     * Method utama yang menjalankan sistem restoran.
     * Menampilkan menu utama dan menangani navigasi berdasarkan pilihan user.
     * Loop terus menerus sampai user memilih untuk keluar.
     */
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

    // ==================== MENU PELANGGAN ====================
    
    /**
     * Menu untuk pengguna biasa (pelanggan).
     * Menampilkan status meja dan menu restoran.
     * Pelanggan dapat memanggil pelayan jika diperlukan.
     */
    private void menuUser() {
        System.out.println("\n=== MEJA BERSIH & TERSEDIA ===");
        mejaService.tampilkanMejaTersedia();

        System.out.println("\nPilih nomor meja di tempat kepada staf.");
        System.out.println("Sistem ini tidak melakukan booking otomatis.");

        System.out.println("\n=== MENU RESTORAN ===");
        menuService.tampilkanMenu();

        System.out.println("\nApakah Anda ingin memanggil pelayan? (ya/tidak)");
        String jawab = scanner.nextLine();

        if (jawab.equalsIgnoreCase("ya")) {
            System.out.println("Pelayan akan segera datang.");
        } else {
            System.out.println("Silakan lihat menu dengan santai.");
        }
    }

    // ==================== MENU PELAYAN ====================
    
    /**
     * Menu khusus untuk staff pelayan.
     * Memberikan akses ke fungsi-fungsi yang berkaitan dengan pelayanan meja.
     */
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
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }

    // ==================== MENU KOKI ====================
    
    /**
     * Menu khusus untuk koki.
     * Memungkinkan koki melihat pesanan yang perlu dimasak dan update status.
     */
    private void menuKoki() {
        while (true) {
            System.out.println("\n=== MENU KOKI ===");
            System.out.println("1. Lihat Pesanan untuk Dimasak");
            System.out.println("2. Update Status Pesanan Dimasak");
            System.out.println("3. Update Status Pesanan Selesai");
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
                    int idPesananMasak = scanner.nextInt();
                    scanner.nextLine();
                    pesananService.updateStatusKokiMasak(idPesananMasak);
                    break;
                case 3:
                    System.out.print("Masukkan ID Pesanan yang sudah selesai: ");
                    int idPesananSelesai = scanner.nextInt();
                    scanner.nextLine();
                    pesananService.updateStatusKokiSelesai(idPesananSelesai);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }

    // ==================== MENU KASIR ====================
    
    /**
     * Menu khusus untuk kasir.
     * Menangani proses pembayaran dan pencetakan struk.
     */
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
                    System.out.println("Pilihan tidak valid");
            }
        }
    }

    // ==================== AUTHENTICATION ====================
    
    /**
     * Proses login untuk manager.
     * 
     * @implNote Memvalidasi kredensial manager dan memberikan akses ke menu manager jika berhasil
     */
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

    /**
     * Menu khusus untuk manager dengan akses ke fungsi administrasi.
     */
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

    /**
     * Mengubah kredensial akun manager.
     */
    private void ubahAkunManager() {
        System.out.print("Username baru: ");
        String u = scanner.nextLine();
        System.out.print("Password baru: ");
        String p = scanner.nextLine();

        authService.updateAkunManager(u, p);
        System.out.println("Akun manager diperbarui.");
    }

    /**
     * Proses pendaftaran akun baru untuk pegawai.
     */
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

    /**
     * Proses login untuk pegawai (pelayan, koki, kasir).
     * Mengarahkan ke menu yang sesuai berdasarkan role pegawai.
     */
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

    // ==================== AUTHENTICATION FOR GUI ====================
    
    /**
     * Method login manager untuk integrasi dengan GUI.
     * 
     * @param u username manager
     * @param p password manager
     * @return true jika login berhasil, false jika gagal
     */
    public boolean loginManagerGUI(String u, String p) {
        return authService.loginManager(u, p);
    }

    /**
     * Method login pegawai untuk integrasi dengan GUI.
     * 
     * @param u username pegawai
     * @param p password pegawai
     * @return objek Akun jika login berhasil, null jika gagal
     */
    public Akun loginPegawaiGUI(String u, String p) {
        return authService.loginPegawai(u, p);
    }

    /**
     * Mendapatkan role pegawai berdasarkan ID.
     * 
     * @param idPegawai ID pegawai yang dicari
     * @return role pegawai sebagai String
     */
    public String getRolePegawai(String idPegawai) {
        return authService.getRoleFromId(idPegawai);
    }

    /**
     * Mengupdate kredensial manager dari GUI.
     * 
     * @param u username baru
     * @param p password baru
     */
    public void updateAkunManagerGUI(String u, String p) {
        authService.updateAkunManager(u, p);
    }

    // ==================== GETTERS FOR GUI INTEGRATION ====================
    
    /**
     * @return service untuk manajemen menu
     */
    public MenuService getMenuService() { return menuService; }
    
    /**
     * @return service untuk manajemen meja
     */
    public MejaService getMejaService() { return mejaService; }
    
    /**
     * @return service untuk manajemen pesanan
     */
    public PesananService getPesananService() { return pesananService; }
    
    /**
     * @return service untuk authentication
     */
    public AuthService getAuthService() { return authService; }
    
    /**
     * @return service untuk proses pembayaran
     */
    public PembayaranService getPembayaranService() { return pembayaranService; }

    /**
     * Menambah pegawai baru dari GUI.
     * 
     * @param roleStr role pegawai (KOKI, PELAYAN, KASIR)
     * @param nama nama lengkap pegawai
     * @param email email pegawai
     * @param hp nomor telepon pegawai
     * @return true jika berhasil, false jika gagal
     */
    public boolean tambahPegawaiGUI(String roleStr, String nama, String email, String hp) {
        return authService.tambahPegawaiGUI(roleStr, nama, email, hp);
    }

    // ==================== MAIN METHOD ====================
    
    /**
     * Method utama untuk menjalankan aplikasi restoran.
     * 
     * @param args command line arguments (tidak digunakan)
     */
    public static void main(String[] args) {
        RestaurantSystem system = new RestaurantSystem();
        system.mulai();
    }
}