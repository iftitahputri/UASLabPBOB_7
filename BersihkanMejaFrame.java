import javax.swing.*;
import java.awt.*;

import models.meja.Meja;
import services.MejaService;

/**
 * Frame untuk membersihkan meja restoran.
 * Menampilkan grid tombol meja dengan warna berdasarkan status kebersihan dan ketersediaan.
 * Pelayan dapat memilih meja untuk dibersihkan dengan mengklik tombol yang sesuai.
 * 
 * @author Kelompok_7
 * @version 1.0
 * @see MejaService
 * @see Meja
 */
public class BersihkanMejaFrame extends JFrame {
    private MejaService mejaService;

    /**
     * Constructor untuk BersihkanMejaFrame.
     * 
     * @param mejaService service untuk mengelola operasi meja
     */
    public BersihkanMejaFrame(MejaService mejaService) {
        this.mejaService = mejaService;
        initializeUI();
    }

    /**
     * Menginisialisasi komponen UI frame.
     * Membuat grid tombol untuk setiap meja dengan warna berdasarkan status.
     */
    private void initializeUI() {
        setTitle("Pilih Meja untuk Dibersihkan");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));

        var list = mejaService.getDaftarMeja();

        // buat tombol untuk setiap meja
        for (var meja : list) {
            JButton btn = new JButton("Meja " + meja.getNomorMeja());
            Color warna = getWarna(meja);
            btn.setBackground(warna);

            btn.addActionListener(e -> {
                mejaService.bersihkanMejaGUI(meja.getNomorMeja()); // bersihkan meja
                JOptionPane.showMessageDialog(this,
                        "Meja " + meja.getNomorMeja() + " berhasil dibersihkan!");
                this.dispose(); // tutup frame setelah dibersihkan
            });

            panel.add(btn); // tambahkan tombol ke panel
        }

        add(panel);
    }

    /**
     * Menentukan warna tombol berdasarkan status meja.
     * 
     * @param m objek Meja yang akan dicek statusnya
     * @return Color yang merepresentasikan status meja:
     *         - HIJAU: Tersedia dan bersih
     *         - ORANYE: Tersedia tapi kotor
     *         - KUNING: Dipesan tapi bersih  
     *         - MERAH: Dipesan dan kotor
     */
    private Color getWarna(Meja m) {
        boolean tersedia = (m.getKetersediaan().toString().equals("TERSEDIA"));
        boolean bersih = (m.getKebersihan().toString().equals("BERSIH"));

        if (tersedia && bersih) return Color.GREEN;
        if (tersedia && !bersih) return Color.ORANGE;
        if (!tersedia && bersih) return Color.YELLOW;
        return Color.RED;  // dipesan + kotor
    }
}