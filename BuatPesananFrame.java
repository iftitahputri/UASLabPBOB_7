/**
 * Frame untuk memilih meja sebelum membuat pesanan.
 * Menampilkan daftar meja dengan status kebersihan dan ketersediaan.
 * 
 * @author Kelompok_7 
 * @version 1.0
 * @see BuatPesananFrame
 * @see MejaService
 */

import javax.swing.*;
import java.awt.*;

import services.MenuService;
import services.MejaService;
import services.PesananService;

// frame untuk buat pesanan
public class BuatPesananFrame extends JFrame {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;

    // constructor
    public BuatPesananFrame(MenuService menuService, MejaService mejaService, PesananService pesananService) {
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.pesananService = pesananService;
        initializeUI();
    }

    // inisialisasi UI
    private void initializeUI() {
        setTitle("Buat Pesanan");
        setSize(500, 400);
        setLayout(new BorderLayout());

        // pilih meja
        JPanel panelMeja = new JPanel(new GridLayout(0, 5, 5, 5));
        for (var meja : mejaService.getDaftarMeja()) {
            JButton btn = new JButton("Meja " + meja.getNomorMeja());

            // set warna berdasarkan status meja
            if (meja.getKebersihan().toString().equals("KOTOR")) {
                btn.setBackground(Color.RED);
                btn.setEnabled(false);
            } else {
                btn.setBackground(Color.GREEN);
            }

        btn.addActionListener(e -> {
            pilihMenu(meja.getNomorMeja());
            this.dispose(); 
        });
        panelMeja.add(btn);
    }
        // label pilih meja
        add(new JLabel("Pilih Meja:"), BorderLayout.NORTH);
        add(panelMeja, BorderLayout.CENTER);
        setVisible(true);
    }

    // method pilih menu 
    private void pilihMenu(int nomorMeja) {
        new PilihMenuFrame(menuService, mejaService, pesananService, nomorMeja);
    }
}