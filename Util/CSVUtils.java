package Util;

import java.io.*;
import java.util.*;

// utility class untuk CSV
public class CSVUtils {

    // baca semua data CSV
    public static List<String[]> readCSV(String filename) {
        List<String[]> rows = new ArrayList<>(); // list simpan hasil data

        File file = new File(filename);
        if (!file.exists()) return rows; // return kosong jika file tidak ada

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // baca tiap baris
            while ((line = br.readLine()) != null) {
                rows.add(line.split(",")); // pisah berdasarkan koma
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // tambah data ke CSV
    public static void appendCSV(String filename, String... data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(String.join(",", data)); // gabung data dengan koma
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // overwrite data CSV
    public static void overwriteCSV(String filename, List<String[]> rows) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String[] row : rows) {
                pw.println(String.join(",", row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
