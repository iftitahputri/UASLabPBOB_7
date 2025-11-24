
// File: PilihMenuFrame.java
import javax.swing.*;
import java.awt.*;
import models.menu.MenuItem;
import models.pesanan.Pesanan;
import models.pesanan.DetailPesanan;
import services.MenuService;
import services.MejaService;
import services.PesananService;

public class PilihMenuFrame extends JFrame {
    private MenuService menuService;
    private MejaService mejaService;
    private PesananService pesananService;
    private int nomorMeja;
    private Pesanan pesananTemp;

    public PilihMenuFrame(MenuService menuService, MejaService mejaService,
            PesananService pesananService, int nomorMeja) {
        this.menuService = menuService;
        this.mejaService = mejaService;
        this.pesananService = pesananService;
        this.nomorMeja = nomorMeja;
        this.pesananTemp = new Pesanan(mejaService.getMejaByNomor(nomorMeja));

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pilih Menu");
        setSize(600, 500);
        setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel(new GridLayout(0, 1));

        for (MenuItem item : menuService.getDaftarMenu()) {
            JButton btn = new JButton(item.getNama() + " - " + item.getHarga());
            btn.addActionListener(e -> tambahItem(item));
            panelMenu.add(btn);
        }

        JButton selesaiBtn = new JButton("Simpan Pesanan");
        selesaiBtn.addActionListener(e -> simpanPesanan());

        add(new JScrollPane(panelMenu), BorderLayout.CENTER);
        add(selesaiBtn, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void tambahItem(MenuItem item) {
        String jumlahStr = JOptionPane.showInputDialog("Jumlah:");
        if (jumlahStr == null)
            return;

        try {
            int jumlah = Integer.parseInt(jumlahStr);
            DetailPesanan dp = new DetailPesanan(item, jumlah, "");
            pesananTemp.tambahDetailPesanan(dp);
            JOptionPane.showMessageDialog(this, "Ditambahkan: " + item.getNama());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
        }
    }

    private void simpanPesanan() {
        if (pesananTemp.getDetailPesanan().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pesanan kosong!");
            return;
        }

        pesananService.getDaftarPesanan().add(pesananTemp);
        mejaService.getMejaByNomor(nomorMeja).setKebersihan(models.meja.KebersihanMeja.KOTOR);

        JOptionPane.showMessageDialog(this, "Pesanan berhasil disimpan!");
        dispose();
    }
}