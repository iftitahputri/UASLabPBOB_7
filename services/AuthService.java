package services;

import models.auth.Akun;
import models.pekerja.RolePegawai;
import Util.CSVUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AuthService {
    private static final String FILE_MANAGER = "akun_manager.csv";
    private static final String FILE_PEGAWAI = "akun_pegawai.csv";
    private static final String FILE_DATA_PEGAWAI = "pegawai.csv";
    private Scanner scanner;

    public AuthService(Scanner scanner) {
        this.scanner = scanner;
        initDefaultManager(); //  akun manager default kalau belum ada
    }

    // buat akun manager default kalau file kosong
    private void initDefaultManager() {
        List<String[]> rows = CSVUtils.readCSV(FILE_MANAGER);
        if (rows.isEmpty()) {
            CSVUtils.appendCSV(FILE_MANAGER, "admin", "admin123");
            System.out.println("✅ Akun manager default dibuat: admin/admin123");
        }
    }

    public boolean loginManager(String username, String password) {
        List<String[]> rows = CSVUtils.readCSV(FILE_MANAGER);
        if (rows.isEmpty()) return false;
        
        String[] akun = rows.get(0); // Ambil data pertama
        return akun[0].equals(username) && akun[1].equals(password);
    }

    public void updateAkunManager(String usernameBaru, String passwordBaru) {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{usernameBaru, passwordBaru});
        CSVUtils.overwriteCSV(FILE_MANAGER, list);
        System.out.println("✅ Akun manager berhasil diupdate");
    }

    public boolean daftarAkunPegawai(String idPegawai, String username, String password) {
        if (!isIdPegawaiValid(idPegawai)) {
            System.out.println("❌ ID Pegawai tidak ditemukan. Minta ID dari Manager.");
            return false;
        }

        if (isAkunPegawaiTerdaftar(idPegawai)) {
            System.out.println("❌ Akun untuk ID ini sudah terdaftar!");
            return false;
        }

        if (isUsernameTerpakai(username)) {
            System.out.println("❌ Username sudah digunakan! Coba yang lain.");
            return false;
        }

        CSVUtils.appendCSV(FILE_PEGAWAI, idPegawai, username, password);
        System.out.println("✅ Akun berhasil dibuat! Silakan login.");
        return true;
    }

    public Akun loginPegawai(String username, String password) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[1].equals(username) && row[2].equals(password)) {
                return new Akun(row[0], row[1], row[2]);
            }
        }
        return null; 
    }

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
            System.out.println("❌ Pilihan role tidak valid");
            return;
        }

        String id = generateIdPegawai(role);
        
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("No HP: ");
        String hp = scanner.nextLine();

        CSVUtils.appendCSV(FILE_DATA_PEGAWAI, id, role.name(), nama, email, hp);
        System.out.println("✅ Pegawai berhasil ditambahkan dengan ID: " + id);
    }

    public void lihatDataPegawai() {
        System.out.println("\n=== DATA PEGAWAI ===");

        List<String[]> data = CSVUtils.readCSV(FILE_DATA_PEGAWAI);

        if (data.isEmpty()) {
            System.out.println("Belum ada data pegawai.");
            return;
        }

        System.out.printf("%-10s %-12s %-20s %-20s %-15s\n",
                "ID", "ROLE", "NAMA", "EMAIL", "NO HP");
        System.out.println("--------------------------------------------------------------------------");

        for (String[] row : data) {
            System.out.printf("%-10s %-12s %-20s %-20s %-15s\n",
                    row[0], row[1], row[2], row[3], row[4]);
        }
    }

    private boolean isIdPegawaiValid(String idPegawai) {
        for (String[] row : CSVUtils.readCSV(FILE_DATA_PEGAWAI)) {
            if (row[0].equals(idPegawai)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAkunPegawaiTerdaftar(String idPegawai) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[0].equals(idPegawai)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameTerpakai(String username) {
        for (String[] row : CSVUtils.readCSV(FILE_PEGAWAI)) {
            if (row[1].equals(username)) {
                return true;
            }
        }
        return false;
    }

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
        return role.getKode() + "_" + String.format("%02d", max + 1);
    }

    public String getRoleFromId(String idPegawai) {
        if (idPegawai.startsWith("PEL")) return "PELAYAN";
        else if (idPegawai.startsWith("KOK")) return "KOKI";
        else if (idPegawai.startsWith("KAS")) return "KASIR";
        else return "UNKNOWN";
    }
}