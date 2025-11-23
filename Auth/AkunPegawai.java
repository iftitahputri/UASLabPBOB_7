package Auth;

import Util.CSVUtils;

public class AkunPegawai {

    private static final String FILE = "akun_pegawai.csv";

    public void daftarAkun(String idPegawai, String username, String password) {
        CSVUtils.appendCSV(FILE, idPegawai, username, password);
    }

    public Akun login(String username, String password) {
        for (String[] row : CSVUtils.readCSV(FILE)) {
            if (row[1].equals(username) && row[2].equals(password)) {
                return new Akun(row[0], row[1], row[2]);
            }
        }
        return null;
    }
}
