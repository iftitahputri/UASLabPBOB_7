package Auth;

import Util.CSVUtils; 

// class untuk mengelola akun pegawai
public class AkunPegawai {

    // file CSV untuk menyimpan data akun pegawai
    private static final String FILE = "akun_pegawai.csv";

    // method daftar akun pegawai
    public void daftarAkun(String idPegawai, String username, String password) {
        CSVUtils.appendCSV(FILE, idPegawai, username, password); // tambah data akun baru ke CSV
    }

    // method login untuk akun pegawai
    public Akun login(String username, String password) {
        // baca data akun dari file CSV sampai ketemu yang cocok
        for (String[] row : CSVUtils.readCSV(FILE)) {
            if (row[1].equals(username) && row[2].equals(password)) {
                return new Akun(row[0], row[1], row[2]);
            }
        }
        return null;
    }
}
