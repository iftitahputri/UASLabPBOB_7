package Auth;

import java.util.*;

import Util.CSVUtils;

// class untuk mengelola akun manager
public class AkunManager {

    // file CSV untuk menyimpan data akun manager
    private static final String FILE = "akun_manager.csv";

    // constructor
    public AkunManager() {
        initDefault();
    }

    // buat akun manager default jika belum ada
    private void initDefault() {
        List<String[]> rows = CSVUtils.readCSV(FILE);
        // cek isi file CSV (kosong atau tidak)
        if (rows.isEmpty()) {
            CSVUtils.appendCSV(FILE, "admin", "admin123");
            System.out.println("Akun manager default dibuat: admin/admin123");
        }
    }

    // method login untuk akun manager
    public boolean login(String username, String password) {
        List<String[]> rows = CSVUtils.readCSV(FILE);
        if (rows.isEmpty()) return false; // login gagal jika file kosong
        // ambil data akun pertama
        String[] r = rows.get(0);
        //bandingkan username dan password
        return r[0].equals(username) && r[1].equals(password);
    }

    // method update data akun manager
    public void updateAkun(String usernameBaru, String passwordBaru) {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{usernameBaru, passwordBaru}); // buat data baru
        CSVUtils.overwriteCSV(FILE, list); //overwrite file CSV dengan data baru
    }
}