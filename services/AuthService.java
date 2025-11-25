package services;

import models.auth.Akun;
import models.pekerja.RolePegawai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Util.CSVUtils;

/**
 * Service untuk mengelola autentikasi dan data pegawai.
 * Menangani login manager, pendaftaran akun pegawai, dan manajemen data pegawai.
 * Data disimpan dalam file CSV terpisah untuk manager, akun pegawai, dan data pegawai.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see Akun
 * @see RolePegawai
 * @see CSVUtils
 */
public class AuthService {
    // masing masing data disimpan di csv
    private static final String FILE_MANAGER = "akun_manager.csv";
    private static final String FILE_PEGAWAI = "akun_pegawai.csv";
    private static final String FILE_DATA_PEGAWAI = "pegawai.csv";
    private Scanner scanner;

    /**
     * Constructor untuk AuthService.
     * 
     * @param scanner scanner untuk input user
     */
    public AuthService(Scanner scanner) {
        this.scanner = scanner;
        initDefaultManager(); // akun manager default kalau belum ada
    }

    /**
     * Menambahkan pegawai baru melalui GUI interface.
     * 
     * @param roleStr role pegawai (PELAYAN, KOKI, KASIR)
     * @param nama nama lengkap pegawai
     * @param email email pegawai
     * @param hp nomor telepon pegawai
     * @return true jika berhasil, false jika gagal
     */
    public boolean tambahPegawaiGUI(String roleStr, String nama, String email, String hp) {
        try {
            RolePegawai role;
            switch (roleStr) {
                case "PELAYAN": role = RolePegawai.PELAYAN; break;
                case "KOKI": role = RolePegawai.KOKI; break;
                case "KASIR": role = RolePegawai.KASIR; break;
                default: return false;
            }

            String id = generateIdPegawai(role);
            CSVUtils.appendCSV(FILE_DATA_PEGAWAI, id, role.name(), nama, email, hp);
            System.out.println("✅ Pegawai berhasil ditambahkan dengan ID: " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Membuat akun manager default jika file manager kosong.
     * Akun default: username="admin", password="admin123"
     */
    private void initDefaultManager() {
        List<String[]> rows = CSVUtils.readCSV(FILE_MANAGER);
        if (rows.isEmpty()) {
            CSVUtils.appendCSV(FILE_MANAGER, "admin", "admin123");
            System.out.println("Akun manager default dibuat: admin/admin123");
        }
    }

    /**
     * Memvalidasi login manager.
     * 
     * @param username username manager
     * @param password password manager
     * @return true jika kredensial valid, false jika tidak valid
     */
    public boolean loginManager(String username, String password) {
        List<String[]> rows = CSVUtils.readCSV(FILE_MANAGER);
        if (rows.isEmpty()) return false;
        
        String[] akun = rows.get(0); // ambil data pertama
        return akun[0].equals(username) && akun[1].equals(password);
    }

    /**
     * Mengupdate kredensial akun manager.
     * 
     * @param usernameBaru username baru untuk manager
     * @param passwordBaru password baru untuk manager
     */
    public void updateAkunManager(String usernameBaru, String passwordBaru) {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{usernameBaru, passwordBaru});
        CSVUtils.overwriteCSV(FILE_MANAGER, list);
        System.out.println("Akun manager berhasil diupdate");
    }

    /**
     * Mendaftarkan akun baru untuk pegawai.
     * 
     * @param idPegawai ID pegawai yang valid
     * @param username username untuk login
     * @param password password untuk login
     * @return true jika pendaftaran berhasil, false jika gagal
     */
    public boolean daftarAkunPegawai(String idPegawai, String username, String password) {
        // cek validasi id
        if (!isIdPegawaiValid(idPegawai)) {
            System.out.println("ID Pegawai tidak ditemukan. Minta ID dari Manager.");
            return false;
        }

        // cek id dan username sudah terpakai
        if (isAkunPegawaiTerdaftar(idPegawai)) {
            System.out.println("Akun untuk ID ini sudah terdaftar!");
            return false;
        }

        if (isUsernameTerpakai(username)) {
            System.out.println("Username sudah digunakan! Coba yang lain.");
            return false;
        }

        // simpan data akun baru
        CSVUtils.appendCSV(FILE_PEGAWAI, idPegawai, username, password);
        System.out.println("Akun berhasil dibuat! Silakan login.");
        return true;
    }

    /**
     * Memvalidasi login pegawai.
     * 
     * @param username username pegawai
     * @param password password pegawai
     * @return objek Akun jika login berhasil, null jika gagal
     */
    public Akun loginPegawai(String username, String password) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[1].equals(username) && row[2].equals(password)) {
                return new Akun(row[0], row[1], row[2]);
            }
        }
        return null; 
    }

    /**
     * Menambahkan data pegawai baru melalui CLI interface.
     * Men-generate ID otomatis berdasarkan role.
     */
    public void tambahPegawai() {
        System.out.println("Pilih Role:");
        System.out.println("1. Pelayan");
        System.out.println("2. Koki");
        System.out.println("3. Kasir");
        System.out.print("> ");
        int pilih = scanner.nextInt();
        scanner.nextLine();

        RolePegawai role = switch (pilih) {
            case 1 -> RolePegawai.PELAYAN;
            case 2 -> RolePegawai.KOKI;
            case 3 -> RolePegawai.KASIR;
            default -> null;
        };

        if (role == null) {
            System.out.println("Pilihan role tidak valid");
            return;
        }

        // generate id pegawai
        String id = generateIdPegawai(role);
        
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("No HP: ");
        String hp = scanner.nextLine();

        // simpan data pegawai
        CSVUtils.appendCSV(FILE_DATA_PEGAWAI, id, role.name(), nama, email, hp);
        System.out.println("Pegawai berhasil ditambahkan dengan ID: " + id);
    }

    /**
     * Menampilkan semua data pegawai dalam format tabel.
     */
    public void lihatDataPegawai() {
        System.out.println("\n=== DATA PEGAWAI ===");

        // baca data dari csv
        List<String[]> data = CSVUtils.readCSV(FILE_DATA_PEGAWAI);

        if (data.isEmpty()) {
            System.out.println("Belum ada data pegawai.");
            return;
        }

        // print header
        System.out.printf("%-10s %-12s %-20s %-20s %-15s\n",
                "ID", "ROLE", "NAMA", "EMAIL", "NO HP");
        System.out.println("--------------------------------------------------------------------------");

        // print data
        for (String[] row : data) {
            System.out.printf("%-10s %-12s %-20s %-20s %-15s\n",
                    row[0], row[1], row[2], row[3], row[4]);
        }
    }

    /**
     * Memvalidasi apakah ID pegawai ada dalam database.
     * 
     * @param idPegawai ID pegawai yang akan divalidasi
     * @return true jika ID valid, false jika tidak valid
     */
    private boolean isIdPegawaiValid(String idPegawai) {
        for (String[] row : CSVUtils.readCSV(FILE_DATA_PEGAWAI)) {
            if (row[0].equals(idPegawai)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mengecek apakah akun pegawai sudah terdaftar.
     * 
     * @param idPegawai ID pegawai yang akan dicek
     * @return true jika sudah terdaftar, false jika belum
     */
    private boolean isAkunPegawaiTerdaftar(String idPegawai) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[0].equals(idPegawai)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mengecek apakah username sudah digunakan.
     * 
     * @param username username yang akan dicek
     * @return true jika username sudah digunakan, false jika masih tersedia
     */
    private boolean isUsernameTerpakai(String username) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[1].equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Men-generate ID pegawai baru berdasarkan role.
     * Format ID: [KODE ROLE]_[NOMOR URUT]
     * 
     * @param role role pegawai
     * @return ID pegawai yang baru
     */
    private String generateIdPegawai(RolePegawai role) {
        int max = 0;
        for (String[] row : CSVUtils.readCSV(FILE_DATA_PEGAWAI)) {
            if (row[1].equals(role.name())) {
                try {
                    String num = row[0].split("_")[1];
                    max = Math.max(max, Integer.parseInt(num));
                } catch (Exception e) {
                    // skip jika format ID tidak valid
                }
            }
        }
        // return id dengan format ROLE_XX
        return role.getKode() + "_" + String.format("%02d", max + 1);
    }

    /**
     * Mendapatkan role pegawai berdasarkan ID.
     * 
     * @param idPegawai ID pegawai
     * @return role pegawai sebagai String
     */
    public String getRoleFromId(String idPegawai) {
        if (idPegawai.startsWith("PEL")) return "PELAYAN";
        else if (idPegawai.startsWith("KOK")) return "KOKI";
        else if (idPegawai.startsWith("KAS")) return "KASIR";
        else return "UNKNOWN";
    }
}