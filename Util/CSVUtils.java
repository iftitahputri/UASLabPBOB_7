package Util;

import java.io.*;
import java.util.*;

public class CSVUtils {

    public static List<String[]> readCSV(String filename) {
        List<String[]> rows = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) return rows;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static void appendCSV(String filename, String... data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(String.join(",", data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
