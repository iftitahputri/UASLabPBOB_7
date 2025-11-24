import javax.swing.*;
import java.awt.*;

import models.meja.Meja;
import services.MejaService;

public class BersihkanMejaFrame extends JFrame {
    private MejaService mejaService;

    public BersihkanMejaFrame(MejaService mejaService) {
        this.mejaService = mejaService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pilih Meja untuk Dibersihkan");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));

        var list = mejaService.getDaftarMeja();

        for (var meja : list) {
            JButton btn = new JButton("Meja " + meja.getNomorMeja());
            Color warna = getWarna(meja);
            btn.setBackground(warna);

            btn.addActionListener(e -> {
                mejaService.bersihkanMejaGUI(meja.getNomorMeja());
                JOptionPane.showMessageDialog(this,
                        "Meja " + meja.getNomorMeja() + " berhasil dibersihkan!");
                this.dispose();
                new BersihkanMejaFrame(mejaService).setVisible(true);
            });

            panel.add(btn);
        }

        add(panel);
    }

    private Color getWarna(Meja m) {
        boolean tersedia = (m.getKetersediaan().toString().equals("TERSEDIA"));
        boolean bersih = (m.getKebersihan().toString().equals("BERSIH"));

        if (tersedia && bersih) return Color.GREEN;
        if (tersedia && !bersih) return Color.ORANGE;
        if (!tersedia && bersih) return Color.YELLOW;
        return Color.RED;  // DIPESAN + KOTOR
    }
}