// File: BuatPesananFrame.java
import javax.swing.*;
import java.awt.*;
import services.MenuService;
import services.MejaService;
import services.PesananService;

public class BuatPesananFrame extends JFrame {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;

    public BuatPesananFrame(MenuService menuService, MejaService mejaService, PesananService pesananService) {
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.pesananService = pesananService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Buat Pesanan");
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Bagian pilih meja
        JPanel panelMeja = new JPanel(new GridLayout(0, 5, 5, 5));
        for (var meja : mejaService.getDaftarMeja()) {
            JButton btn = new JButton("Meja " + meja.getNomorMeja());

            if (meja.getKebersihan().toString().equals("KOTOR")) {
                btn.setBackground(Color.RED);
            } else {
                btn.setBackground(Color.GREEN);
            }

            btn.addActionListener(e -> pilihMenu(meja.getNomorMeja()));
            panelMeja.add(btn);
        }

        add(new JLabel("Pilih Meja:"), BorderLayout.NORTH);
        add(panelMeja, BorderLayout.CENTER);
        setVisible(true);
    }

    private void pilihMenu(int nomorMeja) {
        new PilihMenuFrame(menuService, mejaService, pesananService, nomorMeja);
    }
}