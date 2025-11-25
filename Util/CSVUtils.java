package Util;

import java.io.*;
import java.util.*;

/**
 * Utility class untuk operasi baca/tulis file CSV.
 * Menyediakan fungsi-fungsi dasar untuk manipulasi data dalam format CSV.
 * 
 * @author Kelompok_7
 * @version 1.0
 */
public class CSVUtils {

    /**
     * Membaca seluruh data dari file CSV dan mengembalikan sebagai list of string arrays.
     * Setiap baris dalam CSV akan dipecah menjadi array of string berdasarkan koma.
     * 
     * @param filename path file CSV yang akan dibaca
     * @return List of string arrays dimana setiap array merepresentasikan satu baris data
     *         Mengembalikan list kosong jika file tidak ditemukan atau terjadi error
     */
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

    /**
     * Menambahkan data baru ke akhir file CSV.
     * Data akan ditambahkan sebagai baris baru tanpa menghapus data yang sudah ada.
     * 
     * @param filename path file CSV tujuan
     * @param data data yang akan ditambahkan (varargs string)
     */
    public static void appendCSV(String filename, String... data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(String.join(",", data)); // gabung data dengan koma
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menimpa seluruh isi file CSV dengan data baru.
     * File yang lama akan dihapus dan digantikan dengan data yang baru.
     * 
     * @param filename path file CSV yang akan ditimpa
     * @param rows List of string arrays yang akan ditulis ke file
     */
    public static void overwriteCSV(String filename, List<String[]> rows) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String[] row : rows) {
                pw.println(String.join(",", row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Menghapus baris tertentu dari file CSV berdasarkan kriteria.
     * Baris yang dihapus adalah baris dimana nilai pada kolom tertentu sama dengan nilai yang dicari.
     * 
     * @param filename path file CSV
     * @param columnIndex index kolom yang akan dicek (0-based)
     * @param value nilai yang dicari pada kolom tersebut
     * @return true jika berhasil menghapus, false jika tidak ditemukan atau error
     */
    public static boolean deleteRowFromCSV(String filename, int columnIndex, String value) {
        List<String[]> allRows = readCSV(filename);
        List<String[]> updatedRows = new ArrayList<>();
        boolean found = false;
        
        for (String[] row : allRows) {
            if (row.length > columnIndex && row[columnIndex].equals(value)) {
                found = true; // skip baris yang akan dihapus
            } else {
                updatedRows.add(row); // tambahkan baris yang tidak dihapus
            }
        }
        
        if (found) {
            overwriteCSV(filename, updatedRows);
        }
        return found;
    }
    
    /**
     * Mencari baris dalam file CSV berdasarkan nilai pada kolom tertentu.
     * 
     * @param filename path file CSV
     * @param columnIndex index kolom yang akan dicari (0-based)
     * @param value nilai yang dicari
     * @return List of string arrays yang memenuhi kriteria pencarian
     */
    public static List<String[]> findInCSV(String filename, int columnIndex, String value) {
        List<String[]> allRows = readCSV(filename);
        List<String[]> results = new ArrayList<>();
        
        for (String[] row : allRows) {
            if (row.length > columnIndex && row[columnIndex].equals(value)) {
                results.add(row);
            }
        }
        return results;
    }
}