package Auth;

import Util.CSVUtils;
import java.util.*;

public class AkunManager {

    private static final String FILE = "akun_manager.csv";

    public AkunManager() {
        initDefault();
    }

    private void initDefault() {
        List<String[]> rows = CSVUtils.readCSV(FILE);

        if (rows.isEmpty()) {
            CSVUtils.appendCSV(FILE, "admin", "admin123");
            System.out.println("Akun manager default dibuat: admin/admin123");
        }
    }

    public boolean login(String username, String password) {
        List<String[]> rows = CSVUtils.readCSV(FILE);
        if (rows.isEmpty()) return false;

        String[] r = rows.get(0);
        return r[0].equals(username) && r[1].equals(password);
    }

    public void updateAkun(String usernameBaru, String passwordBaru) {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{usernameBaru, passwordBaru});
        CSVUtils.overwriteCSV(FILE, list);
    }
}
