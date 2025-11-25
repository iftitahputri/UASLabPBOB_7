package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import models.menu.Makanan;
import models.menu.MenuItem;
import models.menu.Minuman;

/**
 * Service untuk mengelola data menu restoran.
 * Menangani loading, penyimpanan, dan retrieval data menu dari file CSV.
 * Mendukung kedua tipe menu: Makanan dan Minuman.
 * 
 * @author Kelompok_7 
 * @version 1.0
 * @see MenuItem
 * @see Makanan
 * @see Minuman
 */
public class MenuService {
    private List<MenuItem> daftarMenu;
    private static final String FILE_MENU = "menu.csv";

    /**
     * Constructor untuk MenuService.
     * Menginisialisasi daftar menu dan memuat data dari CSV.
     * Jika CSV tidak ada, akan membuat menu default.
     */
    public MenuService() {
        this.daftarMenu = new ArrayList<>();
        loadData();
    }

    /**
     * Memuat data menu dari sistem.
     * Pertama mencoba load dari CSV, jika gagal akan membuat menu default.
     */
    private void loadData() {
        loadMenuFromCSV();

        if (daftarMenu.isEmpty()) {
            System.out.println("CSV kosong atau tidak ditemukan — membuat default...");
            createDefaultCSVMenu();
            daftarMenu.clear();
            loadMenuFromCSV();
        }
    }

    /**
     * Memuat data menu dari file CSV.
     * Membaca file line by line dan mengkonversi ke objek MenuItem.
     * 
     * @implNote Format CSV: ID,Nama,Harga,Tipe,Tersedia,Attrib1,Attrib2
     * @throws IOException jika terjadi error membaca file
     */
    private void loadMenuFromCSV() {
        daftarMenu.clear(); 

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_MENU))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                if (data.length >= 7) {
                    String id = data[0].trim();
                    String nama = data[1].trim();
                    double harga = Double.parseDouble(data[2].trim());
                    String tipe = data[3].trim();
                    boolean tersedia = Boolean.parseBoolean(data[4].trim());
                    String attrib1 = data[5].trim();
                    String attrib2 = data[6].trim();

                    MenuItem item;

                    if (tipe.equalsIgnoreCase("makanan")) {
                        item = new Makanan(id, nama, harga, tipe, tersedia, attrib1, attrib2);
                    } else if (tipe.equalsIgnoreCase("minuman")) {
                        item = new Minuman(id, nama, harga, tipe, tersedia, attrib1, attrib2);
                    } else {
                        System.out.println("Tipe tidak dikenal: " + tipe);
                        continue;
                    }

                    daftarMenu.add(item);

                } else {
                    System.out.println("Data tidak lengkap: " + line);
                }
            }

            System.out.println("Menu loaded: " + daftarMenu.size() + " items");

        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
    }

    /**
     * Membuat file CSV dengan menu default jika file tidak ditemukan.
     * Menu default termasuk beberapa makanan dan minuman umum.
     */
    private void createDefaultCSVMenu() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_MENU))) {

            writer.println("ID,Nama,Harga,Tipe,Tersedia,Attrib1,Attrib2");

            String[][] defaultMenu = {
                    { "F01", "Nasi Goreng", "25000", "makanan", "true", "Sedang", "Main Course" },
                    { "F02", "Mie Goreng", "20000", "makanan", "true", "Pedas", "Main Course" },
                    { "F03", "Ayam Goreng", "18000", "makanan", "true", "Tidak Pedas", "Main Course" },
                    { "D01", "Es Teh", "5000", "minuman", "true", "Medium", "Dingin" },
                    { "D02", "Jus Jeruk", "8000", "minuman", "true", "Large", "Dingin" },
                    { "D03", "Kopi", "12000", "minuman", "true", "Small", "Panas" }
            };

            for (String[] menu : defaultMenu) {
                writer.println(String.join(",", menu));
            }

            System.out.println("CSV default dibuat.");

        } catch (IOException e) {
            System.out.println("Error membuat CSV default: " + e.getMessage());
        }
    }

    /**
     * Menyimpan data menu ke file CSV.
     * Menulis semua item menu ke file dengan format yang sesuai.
     * 
     * @throws IOException jika terjadi error menulis file
     */
    public void saveMenuToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_MENU))) {
            writer.println("ID,Nama,Harga,Tipe,Tersedia,Attrib1,Attrib2");

            for (MenuItem menu : daftarMenu) {
                if (menu instanceof Makanan m) {
                    writer.println(m.getId() + "," +
                            m.getNama() + "," +
                            m.getHarga() + "," +
                            "makanan" + "," +
                            m.isTersedia() + "," +
                            m.getTingkatPedas() + "," +
                            m.getKategoriMakan());

                } else if (menu instanceof Minuman m) {
                    writer.println(m.getId() + "," +
                            m.getNama() + "," +
                            m.getHarga() + "," +
                            "minuman" + "," +
                            m.isTersedia() + "," +
                            m.getUkuran() + "," +
                            m.getSuhu());
                }
            }

            System.out.println("Menu disimpan ke CSV.");

        } catch (IOException e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    /**
     * Menampilkan daftar menu ke console dengan format tabel.
     * Menampilkan semua informasi menu termasuk atribut khusus.
     */
    public void tampilkanMenu() {
        System.out.println("\n=== DAFTAR MENU ===");
        System.out.printf("%-5s %-20s %-10s %-10s %-10s %-15s %s\n",
                "ID", "Nama", "Harga", "Tipe", "Tersedia", "Attrib1", "Attrib2");

        for (MenuItem menu : daftarMenu) {
            if (menu instanceof Makanan m) {
                System.out.printf("%-5s %-20s %-10.0f %-10s %-10s %-15s %s\n",
                        m.getId(), m.getNama(), m.getHarga(), "Makanan",
                        m.isTersedia() ? "Ya" : "Tidak",
                        m.getTingkatPedas(), m.getKategoriMakan());

            } else if (menu instanceof Minuman m) {
                System.out.printf("%-5s %-20s %-10.0f %-10s %-10s %-15s %s\n",
                        m.getId(), m.getNama(), m.getHarga(), "Minuman",
                        m.isTersedia() ? "Ya" : "Tidak",
                        m.getUkuran(), m.getSuhu());
            }
        }
    }

    /**
     * Mencari menu berdasarkan ID.
     * 
     * @param id ID menu yang dicari
     * @return MenuItem jika ditemukan, null jika tidak ditemukan
     */
    public MenuItem cariMenuById(String id) {
        for (MenuItem menu : daftarMenu) {
            if (menu.getId().equalsIgnoreCase(id)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * Mendapatkan daftar semua menu.
     * 
     * @return List berisi semua MenuItem
     */
    public List<MenuItem> getDaftarMenu() { return daftarMenu; }
}